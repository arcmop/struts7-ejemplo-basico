package local.unp.desarrolloweb2.tienda.action;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PasswordCambiarActionTest {

    @Test
    public void testExecuteReturnsSuccess() {
        PasswordCambiarAction action = new PasswordCambiarAction();

        String result = action.execute();

        assertEquals("success", result);
    }
}
