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
    public List<Producto> getListaProductosPaginadosPorTitulo(String titulo, int pagina, int tamanioPagina) {
        if (pagina < 1 || tamanioPagina < 1) {
            throw new IllegalArgumentException("Parámetros de paginación inválidos.");
        }
        int offset = (pagina - 1) * tamanioPagina;
        String termino = normalizeTexto(titulo);
        return tiendaDao.listarProductosPaginadosPorTitulo(termino, tamanioPagina, offset);
    }

    @Override
    public int getTotalProductos() {
        return tiendaDao.contarProductos();
    }

    @Override
    public int getTotalProductosPorTitulo(String titulo) {
        return tiendaDao.contarProductosPorTitulo(normalizeTexto(titulo));
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
    public boolean registrarUsuario(String username, String nombreCompleto, String password, boolean esAdmin, String actorUsername) {
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
        String auditActor = resolveActorUsername(actorUsername, normalizedUsername);
        tiendaDao.crearUsuario(normalizedUsername, normalizedNombreCompleto, passwordHash, esAdmin, true, auditActor);
        return true;
    }

    @Override
    public boolean cambiarClaveAdministrador(String username, String currentPassword, String newPassword, String actorUsername) {
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

        String normalizedUsername = username.trim();
        String auditActor = resolveActorUsername(actorUsername, normalizedUsername);
        return tiendaDao.actualizarClaveAdministrador(normalizedUsername, currentHash, newHash, auditActor);
    }

    @Override
    public Producto getProductoPorId(Integer id) {
        if (id == null) {
            return null;
        }
        return tiendaDao.buscarProductoPorId(id);
    }

    @Override
    public void actualizarProducto(Producto producto, String actorUsername) {
        if (producto == null || producto.getId() == null || producto.getPrecio() == null) {
            throw new IllegalArgumentException("Producto inválido para actualizar.");
        }
        tiendaDao.actualizarProducto(producto, resolveActorUsername(actorUsername, null));
    }

    @Override
    public void crearProducto(Producto producto, String actorUsername) {
        if (producto == null || producto.getPrecio() == null) {
            throw new IllegalArgumentException("Producto inválido para registrar.");
        }
        tiendaDao.crearProducto(producto, resolveActorUsername(actorUsername, null));
    }

    @Override
    public void eliminarProducto(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id de producto inválido para eliminar.");
        }
        tiendaDao.eliminarProducto(id);
    }

    private String resolveActorUsername(String actorUsername, String fallback) {
        if (actorUsername != null) {
            String value = actorUsername.trim();
            if (!value.isEmpty()) {
                return value;
            }
        }
        if (fallback != null) {
            String value = fallback.trim();
            if (!value.isEmpty()) {
                return value;
            }
        }
        return "sistema";
    }

    private String normalizeTexto(String texto) {
        if (texto == null) {
            return null;
        }
        String value = texto.trim();
        return value.isEmpty() ? null : value;
    }
}
