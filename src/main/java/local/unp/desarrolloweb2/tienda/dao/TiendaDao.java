package local.unp.desarrolloweb2.tienda.dao;

import java.util.List;
import local.unp.desarrolloweb2.tienda.model.Producto;

public interface TiendaDao {

    List<Producto> listarProductos();

    Producto buscarProductoPorId(Integer id);

    void actualizarProducto(Producto producto);

    void crearProducto(Producto producto);

    void eliminarProducto(Integer id);
}
