package local.unp.desarrolloweb2.tienda.action;

import java.time.Year;
import java.util.Arrays;
import java.util.Collections;
import local.unp.desarrolloweb2.tienda.model.Producto;
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

    @Test
    public void testLandingPageHidesAuthenticatedActionsWhenUserIsGuest() throws Exception {
        TiendaService tiendaService = mock(TiendaService.class);
        when(tiendaService.getTotalProductos()).thenReturn(1);
        when(tiendaService.getListaProductosPaginados(1, 10)).thenReturn(Arrays.asList(
                new Producto(1, "Teclado", "Mecanico", 120.0, 10)
        ));

        ActionProxy actionProxy = getActionProxy("/productos.action");

        assertNotNull(actionProxy);
        assertTrue(actionProxy.getAction() instanceof ProductoAction);

        ProductoAction action = (ProductoAction) actionProxy.getAction();
        action.setTiendaService(tiendaService);

        String result = actionProxy.execute();

        assertEquals("success", result);
        String html = response.getContentAsString();

        
        assertFalse(html.contains(">Cerrar sesión<"));
        assertFalse(html.contains(">Editar<"));
        assertFalse(html.contains(">Eliminar<"));
        assertFalse(html.contains(">Cambiar clave<"));
        assertFalse(html.contains(">Nuevo producto<"));
        assertFalse(html.contains(">Nuevo usuario<"));

        
        assertFalse(html.contains("logout.action"));
        assertFalse(html.contains("producto-editar.action"));
        assertFalse(html.contains("producto-eliminar.action"));
        assertFalse(html.contains("producto-nuevo.action"));
        assertFalse(html.contains("usuario-nuevo.action"));
        assertFalse(html.contains("password-cambiar.action"));

        verify(tiendaService).getTotalProductos();
        verify(tiendaService).getListaProductosPaginados(1, 10);
    }
}
