package local.unp.desarrolloweb2.tienda.action;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UsuarioNuevoActionTest {

    @Test
    public void testExecuteReturnsSuccess() {
        UsuarioNuevoAction action = new UsuarioNuevoAction();

        String result = action.execute();

        assertEquals("success", result);
    }
}
