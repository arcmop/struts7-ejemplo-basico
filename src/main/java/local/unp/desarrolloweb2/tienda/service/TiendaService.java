package local.unp.desarrolloweb2.tienda.service;

import java.util.List;
import local.unp.desarrolloweb2.tienda.model.Producto;

public interface TiendaService {

    List<Producto> getListaProductos();

    List<Producto> getListaProductosPaginados(int pagina, int tamanioPagina);

    List<Producto> getListaProductosPaginadosPorTitulo(String titulo, int pagina, int tamanioPagina);

    int getTotalProductos();

    int getTotalProductosPorTitulo(String titulo);

    boolean autenticarUsuario(String username, String password);

    boolean autenticarAdministrador(String username, String password);

    boolean registrarUsuario(String username, String nombreCompleto, String password, boolean esAdmin, String actorUsername);

    boolean cambiarClaveAdministrador(String username, String currentPassword, String newPassword, String actorUsername);

    Producto getProductoPorId(Integer id);

    void actualizarProducto(Producto producto, String actorUsername);

    void crearProducto(Producto producto, String actorUsername);

    void eliminarProducto(Integer id);
}
