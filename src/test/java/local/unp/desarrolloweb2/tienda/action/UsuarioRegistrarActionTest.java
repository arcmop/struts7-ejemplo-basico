package local.unp.desarrolloweb2.tienda.action;

import java.util.HashMap;
import java.util.Map;
import local.unp.desarrolloweb2.tienda.service.TiendaService;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UsuarioRegistrarActionTest {

    @Test
    public void testExecuteReturnsInputWhenUsernameAlreadyExists() {
        TiendaService tiendaService = mock(TiendaService.class);
        when(tiendaService.registrarUsuario("usuario1", "Usuario Uno", "clave12345", false, "operador")).thenReturn(false);

        UsuarioRegistrarAction action = new TestableUsuarioRegistrarAction();
        Map<String, Object> session = new HashMap<>();
        session.put("authenticatedUsername", "operador");
        action.withSession(session);
        action.setTiendaService(tiendaService);
        action.setUsername("usuario1");
        action.setNombreCompleto("Usuario Uno");
        action.setPassword("clave12345");
        action.setConfirmPassword("clave12345");
        action.setEsAdmin(Boolean.FALSE);

        String result = action.execute();

        assertEquals("input", result);
        assertTrue(action.getFieldErrors().containsKey("username"));
        verify(tiendaService).registrarUsuario("usuario1", "Usuario Uno", "clave12345", false, "operador");
    }

    @Test
    public void testExecuteReturnsSuccessAndResetsState() {
        TiendaService tiendaService = mock(TiendaService.class);
        when(tiendaService.registrarUsuario("usuario2", "Usuario Dos", "clave12345", true, "adminRoot")).thenReturn(true);

        UsuarioRegistrarAction action = new TestableUsuarioRegistrarAction();
        Map<String, Object> session = new HashMap<>();
        session.put("adminUsername", "  adminRoot  ");
        action.withSession(session);
        action.setTiendaService(tiendaService);
        action.setUsername("usuario2");
        action.setNombreCompleto("Usuario Dos");
        action.setPassword("clave12345");
        action.setConfirmPassword("clave12345");
        action.setEsAdmin(Boolean.TRUE);

        String result = action.execute();

        assertEquals("success", result);
        assertTrue(action.hasActionMessages());
        assertNull(action.getUsername());
        assertNull(action.getNombreCompleto());
        assertEquals(Boolean.FALSE, action.getEsAdmin());
        verify(tiendaService).registrarUsuario("usuario2", "Usuario Dos", "clave12345", true, "adminRoot");
    }

    @Test
    public void testValidateAddsErrorsForInvalidData() {
        UsuarioRegistrarAction action = new TestableUsuarioRegistrarAction();
        action.setUsername(" a* ");
        action.setNombreCompleto(repeat('n', 121));
        action.setPassword("123");
        action.setConfirmPassword("456");

        action.validate();

        assertEquals("a*", action.getUsername());
        assertTrue(action.getFieldErrors().containsKey("username"));
        assertTrue(action.getFieldErrors().containsKey("nombreCompleto"));
        assertTrue(action.getFieldErrors().containsKey("password"));
        assertTrue(action.getFieldErrors().containsKey("confirmPassword"));
    }

    @Test
    public void testValidateAddsRequiredErrors() {
        UsuarioRegistrarAction action = new TestableUsuarioRegistrarAction();
        action.setUsername("   ");
        action.setPassword("");
        action.setConfirmPassword("");

        action.validate();

        assertTrue(action.getFieldErrors().containsKey("username"));
        assertTrue(action.getFieldErrors().containsKey("password"));
        assertTrue(action.getFieldErrors().containsKey("confirmPassword"));
    }

    @Test
    public void testValidateAcceptsValidDataAndTrimsText() {
        UsuarioRegistrarAction action = new TestableUsuarioRegistrarAction();
        action.setUsername("usuario_ok-1");
        action.setNombreCompleto("  Nombre Usuario  ");
        action.setPassword("clave12345");
        action.setConfirmPassword("clave12345");

        action.validate();

        assertFalse(action.hasFieldErrors());
        assertEquals("usuario_ok-1", action.getUsername());
        assertEquals("Nombre Usuario", action.getNombreCompleto());
    }

    private String repeat(char value, int length) {
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(value);
        }
        return builder.toString();
    }
}

class TestableUsuarioRegistrarAction extends UsuarioRegistrarAction {

    @Override
    public String getText(String key) {
        return key;
    }
}
