package local.unp.desarrolloweb2.tienda.action;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LogoutActionTest {

    @Test
    public void testExecuteClearsAuthenticationFlagsFromRegularSessionMap() {
        LogoutAction action = new LogoutAction();
        Map<String, Object> session = new HashMap<>();
        session.put("userAuthenticated", Boolean.TRUE);
        session.put("authenticatedUsername", "admin");
        session.put("adminAuthenticated", Boolean.TRUE);
        session.put("adminUsername", "admin");
        session.put("otraLlave", "valor");
        action.withSession(session);

        String result = action.execute();

        assertEquals("success", result);
        assertFalse(session.containsKey("userAuthenticated"));
        assertFalse(session.containsKey("authenticatedUsername"));
        assertFalse(session.containsKey("adminAuthenticated"));
        assertFalse(session.containsKey("adminUsername"));
        assertTrue(session.containsKey("otraLlave"));
    }

    @Test
    public void testExecuteReturnsSuccessWhenSessionIsNull() {
        LogoutAction action = new LogoutAction();

        String result = action.execute();

        assertEquals("success", result);
    }
}
