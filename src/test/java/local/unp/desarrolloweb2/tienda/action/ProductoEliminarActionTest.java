package local.unp.desarrolloweb2.tienda.action;

import local.unp.desarrolloweb2.tienda.service.TiendaService;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ProductoEliminarActionTest {

    @Test
    public void testExecuteDeletesProductoWhenIdExists() {
        TiendaService tiendaService = mock(TiendaService.class);
        ProductoEliminarAction action = new ProductoEliminarAction();
        action.setTiendaService(tiendaService);
        action.setId(7);

        String result = action.execute();

        assertEquals("success", result);
        verify(tiendaService).eliminarProducto(7);
    }
}
