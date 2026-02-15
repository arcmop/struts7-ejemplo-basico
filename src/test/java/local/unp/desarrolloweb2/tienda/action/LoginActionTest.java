package local.unp.desarrolloweb2.tienda.action;

import java.util.HashMap;
import java.util.Map;
import local.unp.desarrolloweb2.tienda.service.TiendaService;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginActionTest {

    @Test
    public void testExecuteAuthenticatesAdminAndStoresSessionFlag() {
        TiendaService tiendaService = mock(TiendaService.class);
        when(tiendaService.autenticarUsuario("admin", "desaweb2")).thenReturn(true);
        when(tiendaService.autenticarAdministrador("admin", "desaweb2")).thenReturn(true);

        LoginAction action = new LoginAction();
        Map<String, Object> session = new HashMap<>();
        action.withSession(session);
        action.setTiendaService(tiendaService);
        action.setUsername("admin");
        action.setPassword("desaweb2");

        String result = action.execute();

        assertEquals("success", result);
        assertTrue(Boolean.TRUE.equals(session.get("userAuthenticated")));
        assertEquals("admin", session.get("authenticatedUsername"));
        assertTrue(Boolean.TRUE.equals(session.get("adminAuthenticated")));
        assertEquals("admin", session.get("adminUsername"));
        verify(tiendaService).autenticarUsuario("admin", "desaweb2");
        verify(tiendaService).autenticarAdministrador("admin", "desaweb2");
    }

    @Test
    public void testExecuteWithInvalidCredentialsRemovesSessionFlag() {
        TiendaService tiendaService = mock(TiendaService.class);
        when(tiendaService.autenticarUsuario("admin", "incorrecta")).thenReturn(false);

        LoginAction action = new LoginAction();
        Map<String, Object> session = new HashMap<>();
        session.put("userAuthenticated", Boolean.TRUE);
        session.put("authenticatedUsername", "admin");
        session.put("adminAuthenticated", Boolean.TRUE);
        session.put("adminUsername", "admin");
        action.withSession(session);
        action.setTiendaService(tiendaService);
        action.setUsername("admin");
        action.setPassword("incorrecta");

        String result = action.execute();

        assertEquals("input", result);
        assertFalse(session.containsKey("userAuthenticated"));
        assertFalse(session.containsKey("authenticatedUsername"));
        assertFalse(session.containsKey("adminAuthenticated"));
        assertFalse(session.containsKey("adminUsername"));
        verify(tiendaService).autenticarUsuario("admin", "incorrecta");
    }

    @Test
    public void testExecuteAuthenticatesNonAdminUserWithoutAdminFlags() {
        TiendaService tiendaService = mock(TiendaService.class);
        when(tiendaService.autenticarUsuario("invitado", "clave12345")).thenReturn(true);
        when(tiendaService.autenticarAdministrador("invitado", "clave12345")).thenReturn(false);

        LoginAction action = new LoginAction();
        Map<String, Object> session = new HashMap<>();
        action.withSession(session);
        action.setTiendaService(tiendaService);
        action.setUsername("invitado");
        action.setPassword("clave12345");

        String result = action.execute();

        assertEquals("success", result);
        assertTrue(Boolean.TRUE.equals(session.get("userAuthenticated")));
        assertEquals("invitado", session.get("authenticatedUsername"));
        assertFalse(session.containsKey("adminAuthenticated"));
        assertFalse(session.containsKey("adminUsername"));
        verify(tiendaService).autenticarUsuario("invitado", "clave12345");
        verify(tiendaService).autenticarAdministrador("invitado", "clave12345");
    }
}
