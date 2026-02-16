package local.unp.desarrolloweb2.tienda.action;

import java.util.HashMap;
import java.util.Map;
import local.unp.desarrolloweb2.tienda.model.Producto;
import local.unp.desarrolloweb2.tienda.service.TiendaService;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ProductoRegistrarActionTest {

    @Test
    public void testExecuteCreatesProductoWhenDataIsValid() {
        TiendaService tiendaService = mock(TiendaService.class);
        Producto producto = new Producto(null, "Monitor", "27 pulgadas", 799.99, 12);

        ProductoRegistrarAction action = new ProductoRegistrarAction();
        Map<String, Object> session = new HashMap<>();
        session.put("authenticatedUsername", "admin");
        action.withSession(session);
        action.setTiendaService(tiendaService);
        action.setProducto(producto);

        String result = action.execute();

        assertEquals("success", result);
        verify(tiendaService).crearProducto(producto, "admin");
    }
}
