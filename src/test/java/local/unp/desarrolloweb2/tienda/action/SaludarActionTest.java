package local.unp.desarrolloweb2.tienda.action;

import org.apache.struts2.ActionProxy;
import org.apache.struts2.junit.StrutsTestCase;
import org.junit.Test;

public class SaludarActionTest extends StrutsTestCase {

    @Test
    public void testExecute() throws Exception {
        ActionProxy proxy = getActionProxy("/saludar");
        SaludarAction saludarAction = (SaludarAction) proxy.getAction();
        
        assertNotNull(saludarAction);
        
        String result = proxy.execute();
        
        assertEquals("success", result);
        assertEquals("Hola invitado, bienvenido a Apache Struts 2 versión 7", saludarAction.getSaludo());
    }
}
