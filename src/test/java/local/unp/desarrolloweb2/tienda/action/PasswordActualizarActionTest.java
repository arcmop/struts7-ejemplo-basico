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
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

public class PasswordActualizarActionTest {

    @Test
    public void testExecuteReturnsInputWhenAdminSessionIsMissing() {
        TiendaService tiendaService = mock(TiendaService.class);

        PasswordActualizarAction action = new TestablePasswordActualizarAction();
        action.setTiendaService(tiendaService);
        action.withSession(new HashMap<String, Object>());
        action.setCurrentPassword("actual123");
        action.setNewPassword("nueva1234");
        action.setConfirmNewPassword("nueva1234");

        String result = action.execute();

        assertEquals("input", result);
        assertTrue(action.hasActionErrors());
        verifyNoInteractions(tiendaService);
    }

    @Test
    public void testExecuteReturnsInputWhenCurrentPasswordIsInvalid() {
        TiendaService tiendaService = mock(TiendaService.class);
        when(tiendaService.cambiarClaveAdministrador("admin", "actual123", "nueva1234", "admin")).thenReturn(false);

        PasswordActualizarAction action = new TestablePasswordActualizarAction();
        Map<String, Object> session = new HashMap<>();
        session.put("adminUsername", "admin");
        action.withSession(session);
        action.setTiendaService(tiendaService);
        action.setCurrentPassword("actual123");
        action.setNewPassword("nueva1234");
        action.setConfirmNewPassword("nueva1234");

        String result = action.execute();

        assertEquals("input", result);
        assertTrue(action.getFieldErrors().containsKey("currentPassword"));
        verify(tiendaService).cambiarClaveAdministrador("admin", "actual123", "nueva1234", "admin");
    }

    @Test
    public void testExecuteReturnsSuccessWhenPasswordIsUpdated() {
        TiendaService tiendaService = mock(TiendaService.class);
        when(tiendaService.cambiarClaveAdministrador("admin", "actual123", "nueva1234", "admin")).thenReturn(true);

        PasswordActualizarAction action = new TestablePasswordActualizarAction();
        Map<String, Object> session = new HashMap<>();
        session.put("adminUsername", "admin");
        action.withSession(session);
        action.setTiendaService(tiendaService);
        action.setCurrentPassword("actual123");
        action.setNewPassword("nueva1234");
        action.setConfirmNewPassword("nueva1234");

        String result = action.execute();

        assertEquals("success", result);
        assertTrue(action.hasActionMessages());
        verify(tiendaService).cambiarClaveAdministrador("admin", "actual123", "nueva1234", "admin");
    }

    @Test
    public void testValidateAddsRequiredErrorsWhenFieldsAreEmpty() {
        PasswordActualizarAction action = new TestablePasswordActualizarAction();
        action.setCurrentPassword("");
        action.setNewPassword("");
        action.setConfirmNewPassword("");

        action.validate();

        assertTrue(action.getFieldErrors().containsKey("currentPassword"));
        assertTrue(action.getFieldErrors().containsKey("newPassword"));
        assertTrue(action.getFieldErrors().containsKey("confirmNewPassword"));
    }

    @Test
    public void testValidateAddsErrorsWhenNewPasswordIsInvalid() {
        PasswordActualizarAction action = new TestablePasswordActualizarAction();
        action.setCurrentPassword("clave");
        action.setNewPassword("clave");
        action.setConfirmNewPassword("otra");

        action.validate();

        assertTrue(action.getFieldErrors().containsKey("newPassword"));
        assertTrue(action.getFieldErrors().containsKey("confirmNewPassword"));
    }

    @Test
    public void testValidateAcceptsValidPasswordChange() {
        PasswordActualizarAction action = new TestablePasswordActualizarAction();
        action.setCurrentPassword("actual123");
        action.setNewPassword("nueva1234");
        action.setConfirmNewPassword("nueva1234");

        action.validate();

        assertFalse(action.hasFieldErrors());
    }
}

class TestablePasswordActualizarAction extends PasswordActualizarAction {

    @Override
    public String getText(String key) {
        return key;
    }
}
