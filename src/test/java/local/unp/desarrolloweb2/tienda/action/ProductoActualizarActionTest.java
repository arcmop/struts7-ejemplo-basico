package local.unp.desarrolloweb2.tienda.action;

import java.util.HashMap;
import java.util.Map;
import local.unp.desarrolloweb2.tienda.model.Producto;
import local.unp.desarrolloweb2.tienda.service.TiendaService;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ProductoActualizarActionTest {

    @Test
    public void testExecuteUpdatesProductoWhenDataIsValid() {
        TiendaService tiendaService = mock(TiendaService.class);
        Producto producto = new Producto(3, "Teclado", "Mecanico", 199.90, 9);

        ProductoActualizarAction action = new ProductoActualizarAction();
        Map<String, Object> session = new HashMap<>();
        session.put("authenticatedUsername", "cliente1");
        action.withSession(session);
        action.setTiendaService(tiendaService);
        action.setProducto(producto);

        String result = action.execute();

        assertEquals("success", result);
        verify(tiendaService).actualizarProducto(producto, "cliente1");
    }
}
