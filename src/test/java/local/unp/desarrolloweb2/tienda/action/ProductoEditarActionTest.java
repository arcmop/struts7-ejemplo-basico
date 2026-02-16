package local.unp.desarrolloweb2.tienda.action;

import local.unp.desarrolloweb2.tienda.model.Producto;
import local.unp.desarrolloweb2.tienda.service.TiendaService;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductoEditarActionTest {

    @Test
    public void testExecuteReturnsSuccessWhenProductoExists() {
        TiendaService tiendaService = mock(TiendaService.class);
        Producto producto = new Producto(1, "Laptop", "Gaming", 2500.0, 4);
        when(tiendaService.getProductoPorId(1)).thenReturn(producto);

        ProductoEditarAction action = new ProductoEditarAction();
        action.setTiendaService(tiendaService);
        action.setId(1);

        String result = action.execute();

        assertEquals("success", result);
        assertNotNull(action.getProducto());
        assertEquals(producto, action.getProducto());
        verify(tiendaService).getProductoPorId(1);
    }

    @Test
    public void testExecuteReturnsErrorWhenServiceThrowsRuntimeException() {
        TiendaService tiendaService = mock(TiendaService.class);
        ProductoEditarAction action = new ProductoEditarAction();
        action.setTiendaService(tiendaService);
        when(tiendaService.getProductoPorId(5)).thenThrow(new RuntimeException("error controlado"));
        action.setId(5);

        String result = action.execute();

        assertEquals("error", result);
        assertTrue(action.hasActionErrors());
        assertFalse(action.hasFieldErrors());
        verify(tiendaService).getProductoPorId(5);
    }
}
