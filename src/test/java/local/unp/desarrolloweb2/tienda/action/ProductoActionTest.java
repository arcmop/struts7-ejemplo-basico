package local.unp.desarrolloweb2.tienda.action;

import java.time.Year;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import local.unp.desarrolloweb2.tienda.model.Producto;
import local.unp.desarrolloweb2.tienda.service.TiendaService;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductoActionTest {

    @Test
    public void testExecuteLoadsFirstPageProductsAndYear() throws Exception {
        TiendaService tiendaService = mock(TiendaService.class);
        List<Producto> productos = Arrays.asList(
                new Producto(1, "Teclado", "Mecanico", 120.0, 10)
        );
        when(tiendaService.getTotalProductos()).thenReturn(1);
        when(tiendaService.getListaProductosPaginados(1, 10)).thenReturn(productos);

        ProductoAction action = new ProductoAction();
        action.setTiendaService(tiendaService);
        String result = action.execute();

        assertEquals("success", result);
        assertEquals(productos, action.getListaProducto());
        assertEquals(1, action.getPaginaActual());
        assertEquals(1, action.getTotalPaginas());
        verify(tiendaService).getTotalProductos();
        verify(tiendaService).getListaProductosPaginados(1, 10);

        String expectedYear = String.valueOf(Year.now().getValue());
        assertEquals(expectedYear, action.getAnioTexto());
    }

    @Test
    public void testExecuteNormalizesPageWhenOutOfRange() throws Exception {
        TiendaService tiendaService = mock(TiendaService.class);
        List<Producto> productos = Arrays.asList(
                new Producto(31, "Mouse", "Inalambrico", 80.0, 25)
        );
        when(tiendaService.getTotalProductos()).thenReturn(35);
        when(tiendaService.getListaProductosPaginados(4, 10)).thenReturn(productos);

        ProductoAction action = new ProductoAction();
        action.setTiendaService(tiendaService);
        action.setPage(99);
        String result = action.execute();

        assertEquals("success", result);
        assertEquals(4, action.getPaginaActual());
        assertEquals(4, action.getTotalPaginas());
        assertEquals(productos, action.getListaProducto());
        verify(tiendaService).getTotalProductos();
        verify(tiendaService).getListaProductosPaginados(4, 10);
    }

    @Test
    public void testExecuteMarksAuthenticatedNonAdminFromSession() throws Exception {
        TiendaService tiendaService = mock(TiendaService.class);
        when(tiendaService.getTotalProductos()).thenReturn(0);

        ProductoAction action = new ProductoAction();
        Map<String, Object> session = new HashMap<>();
        session.put("userAuthenticated", Boolean.TRUE);
        session.put("authenticatedUsername", "cliente1");
        action.withSession(session);
        action.setTiendaService(tiendaService);

        String result = action.execute();

        assertEquals("success", result);
        assertTrue(action.isAutenticado());
        assertFalse(action.isAdmin());
        assertEquals("cliente1", action.getUsernameAutenticado());
        verify(tiendaService).getTotalProductos();
    }
}
