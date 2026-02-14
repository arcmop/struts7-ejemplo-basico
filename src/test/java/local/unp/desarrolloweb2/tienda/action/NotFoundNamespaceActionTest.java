package local.unp.desarrolloweb2.tienda.action;

import org.apache.struts2.ActionProxy;
import org.apache.struts2.junit.StrutsTestCase;
import org.junit.Test;

public class NotFoundNamespaceActionTest extends StrutsTestCase {

    @Test
    public void testUnknownNamespaceFallsBackToNotFoundAction() throws Exception {
        ActionProxy actionProxy = getActionProxy("/jsp/");

        assertNotNull(actionProxy);
        assertTrue(actionProxy.getAction() instanceof NotFoundAction);

        String result = actionProxy.execute();
        assertEquals("success", result);
    }
}
