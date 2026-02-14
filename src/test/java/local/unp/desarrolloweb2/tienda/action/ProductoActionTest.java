package local.unp.desarrolloweb2.tienda.action;

import java.time.Year;
import java.util.Arrays;
import java.util.List;
import local.unp.desarrolloweb2.tienda.model.Producto;
import local.unp.desarrolloweb2.tienda.service.TiendaService;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductoActionTest {

    @Test
    public void testExecuteLoadsProductsAndYear() throws Exception {
        TiendaService tiendaService = mock(TiendaService.class);
        List<Producto> productos = Arrays.asList(
                new Producto(1, "Teclado", "Mecanico", 120.0, 10)
        );
        when(tiendaService.getListaProductos()).thenReturn(productos);

        ProductoAction action = new ProductoAction();
        action.setTiendaService(tiendaService);
        String result = action.execute();

        assertEquals("success", result);
        assertEquals(productos, action.getListaProducto());
        verify(tiendaService).getListaProductos();

        String expectedYear = String.valueOf(Year.now().getValue());
        assertEquals(expectedYear, action.getAnioTexto());
    }
}
