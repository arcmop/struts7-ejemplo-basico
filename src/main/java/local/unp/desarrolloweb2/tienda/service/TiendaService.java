package local.unp.desarrolloweb2.tienda.service;

import java.util.List;
import local.unp.desarrolloweb2.tienda.model.Producto;

public interface TiendaService {

    List<Producto> getListaProductos();

    List<Producto> getListaProductosPaginados(int pagina, int tamanioPagina);

    int getTotalProductos();

    boolean autenticarUsuario(String username, String password);

    boolean autenticarAdministrador(String username, String password);

    boolean registrarUsuario(String username, String nombreCompleto, String password, boolean esAdmin);

    boolean cambiarClaveAdministrador(String username, String currentPassword, String newPassword);

    Producto getProductoPorId(Integer id);

    void actualizarProducto(Producto producto);

    void crearProducto(Producto producto);

    void eliminarProducto(Integer id);
}
