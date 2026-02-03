package local.unp.desarrolloweb2.tienda.action;

import java.time.Year;
import org.apache.struts2.ActionProxy;
import org.apache.struts2.junit.StrutsTestCase;
import org.junit.Test;

public class ProductoActionTest extends StrutsTestCase {

    @Test
    public void testExecuteLoadsProductsAndYear() throws Exception {
        ActionProxy actionProxy = getActionProxy("/index");
        ProductoAction action = (ProductoAction) actionProxy.getAction();

        assertNotNull(action);

        String result = actionProxy.execute();

        assertEquals("success", result);
        assertNotNull(action.getListaProducto());
        assertTrue(action.getListaProducto().size() > 0);

        String expectedYear = String.valueOf(Year.now().getValue());
        assertEquals(expectedYear, action.getAnioTexto());
    }
}
