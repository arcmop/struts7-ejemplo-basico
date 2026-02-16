package local.unp.desarrolloweb2.tienda.action;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProductoNuevoActionTest {

    @Test
    public void testExecuteInitializesProductoWithDefaultStock() {
        ProductoNuevoAction action = new ProductoNuevoAction();

        String result = action.execute();

        assertEquals("success", result);
        assertNotNull(action.getProducto());
        assertEquals(Integer.valueOf(0), action.getProducto().getStock());
    }
}
