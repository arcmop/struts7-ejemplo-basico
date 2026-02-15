package local.unp.desarrolloweb2.tienda.action;

import java.time.Year;
import java.util.Collections;
import local.unp.desarrolloweb2.tienda.service.TiendaService;
import org.apache.struts2.ActionProxy;
import org.apache.struts2.junit.StrutsTestCase;
import org.junit.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LandingPageActionTest extends StrutsTestCase {

    @Test
    public void testLandingPageProductosActionExecutesSuccessfully() throws Exception {
        TiendaService tiendaService = mock(TiendaService.class);
        when(tiendaService.getTotalProductos()).thenReturn(0);
        when(tiendaService.getListaProductosPaginados(1, 10)).thenReturn(Collections.emptyList());

        ActionProxy actionProxy = getActionProxy("/productos.action");

        assertNotNull(actionProxy);
        assertTrue(actionProxy.getAction() instanceof ProductoAction);

        ProductoAction action = (ProductoAction) actionProxy.getAction();
        action.setTiendaService(tiendaService);

        String result = actionProxy.execute();

        assertEquals("success", result);
        assertEquals(String.valueOf(Year.now().getValue()), action.getAnioTexto());
        verify(tiendaService).getTotalProductos();
    }
}
