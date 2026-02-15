package local.unp.desarrolloweb2.tienda.dao;

import java.util.List;
import local.unp.desarrolloweb2.tienda.model.Producto;

public interface TiendaDao {

    List<Producto> listarProductos();

    List<Producto> listarProductosPaginados(int limit, int offset);

    int contarProductos();

    boolean autenticarUsuario(String username, String passwordHash);

    boolean autenticarAdministrador(String username, String passwordHash);

    boolean existeUsuarioPorUsername(String username);

    void crearUsuario(String username, String nombreCompleto, String passwordHash, boolean esAdmin, boolean activo);

    boolean actualizarClaveAdministrador(String username, String currentPasswordHash, String newPasswordHash);

    Producto buscarProductoPorId(Integer id);

    void actualizarProducto(Producto producto);

    void crearProducto(Producto producto);

    void eliminarProducto(Integer id);
}
