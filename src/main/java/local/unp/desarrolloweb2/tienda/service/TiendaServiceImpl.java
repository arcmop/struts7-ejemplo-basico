package local.unp.desarrolloweb2.tienda.service;

import org.apache.struts2.inject.Inject;
import java.util.List;
import local.unp.desarrolloweb2.tienda.dao.TiendaDao;
import local.unp.desarrolloweb2.tienda.model.Producto;
import local.unp.desarrolloweb2.tienda.util.HashUtils;

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
    public List<Producto> getListaProductosPaginados(int pagina, int tamanioPagina) {
        if (pagina < 1 || tamanioPagina < 1) {
            throw new IllegalArgumentException("Parámetros de paginación inválidos.");
        }
        int offset = (pagina - 1) * tamanioPagina;
        return tiendaDao.listarProductosPaginados(tamanioPagina, offset);
    }

    @Override
    public int getTotalProductos() {
        return tiendaDao.contarProductos();
    }

    @Override
    public boolean autenticarUsuario(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            return false;
        }
        String passwordHash = HashUtils.sha256Hex(password);
        return tiendaDao.autenticarUsuario(username.trim(), passwordHash);
    }

    @Override
    public boolean autenticarAdministrador(String username, String password) {
        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            return false;
        }
        String passwordHash = HashUtils.sha256Hex(password);
        return tiendaDao.autenticarAdministrador(username.trim(), passwordHash);
    }

    @Override
    public boolean registrarUsuario(String username, String nombreCompleto, String password, boolean esAdmin) {
        if (username == null || username.trim().isEmpty() || password == null || password.isEmpty()) {
            return false;
        }

        String normalizedUsername = username.trim();
        if (tiendaDao.existeUsuarioPorUsername(normalizedUsername)) {
            return false;
        }

        String normalizedNombreCompleto = nombreCompleto;
        if (normalizedNombreCompleto != null) {
            normalizedNombreCompleto = normalizedNombreCompleto.trim();
            if (normalizedNombreCompleto.isEmpty()) {
                normalizedNombreCompleto = null;
            }
        }

        String passwordHash = HashUtils.sha256Hex(password);
        tiendaDao.crearUsuario(normalizedUsername, normalizedNombreCompleto, passwordHash, esAdmin, true);
        return true;
    }

    @Override
    public boolean cambiarClaveAdministrador(String username, String currentPassword, String newPassword) {
        if (username == null || username.trim().isEmpty()
                || currentPassword == null || currentPassword.isEmpty()
                || newPassword == null || newPassword.isEmpty()) {
            return false;
        }

        String currentHash = HashUtils.sha256Hex(currentPassword);
        String newHash = HashUtils.sha256Hex(newPassword);
        if (currentHash.equals(newHash)) {
            return false;
        }

        return tiendaDao.actualizarClaveAdministrador(username.trim(), currentHash, newHash);
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
