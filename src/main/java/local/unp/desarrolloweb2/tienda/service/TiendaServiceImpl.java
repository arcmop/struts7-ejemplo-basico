package local.unp.desarrolloweb2.tienda.service;

import org.apache.struts2.inject.Inject;
import java.util.List;
import local.unp.desarrolloweb2.tienda.dao.TiendaDao;
import local.unp.desarrolloweb2.tienda.model.Producto;

public class TiendaServiceImpl implements TiendaService {

    private final TiendaDao tiendaDao;

    @Inject("tiendaDao")
    public TiendaServiceImpl(TiendaDao tiendaDao) {
        this.tiendaDao = tiendaDao;
    }

    @Override
    public List<Producto> getListaProductos() {
        return tiendaDao.listarProductos();
    }

    @Override
    public Producto getProductoPorId(Integer id) {
        if (id == null) {
            return null;
        }
        return tiendaDao.buscarProductoPorId(id);
    }

    @Override
    public void actualizarProducto(Producto producto) {
        if (producto == null || producto.getId() == null || producto.getPrecio() == null) {
            throw new IllegalArgumentException("Producto inválido para actualizar.");
        }
        tiendaDao.actualizarProducto(producto);
    }

    @Override
    public void crearProducto(Producto producto) {
        if (producto == null || producto.getPrecio() == null) {
            throw new IllegalArgumentException("Producto inválido para registrar.");
        }
        tiendaDao.crearProducto(producto);
    }

    @Override
    public void eliminarProducto(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id de producto inválido para eliminar.");
        }
        tiendaDao.eliminarProducto(id);
    }
}
