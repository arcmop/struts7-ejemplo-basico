package local.unp.desarrolloweb2.tienda.action;

import org.apache.struts2.ActionProxy;
import org.apache.struts2.junit.StrutsTestCase;
import org.junit.Test;

public class IndexActionTest extends StrutsTestCase {

    @Test
    public void testExecute() throws Exception {
        ActionProxy actionProxy = getActionProxy("/index");
        IndexAction indexAction = (IndexAction) actionProxy.getAction();

        assertNotNull(indexAction);

        String result = actionProxy.execute();

        assertEquals("success", result);
    }
}


