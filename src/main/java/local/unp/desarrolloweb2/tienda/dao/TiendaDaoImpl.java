package local.unp.desarrolloweb2.tienda.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import local.unp.desarrolloweb2.tienda.model.Producto;

public class TiendaDaoImpl implements TiendaDao {

    private static final String JNDI_DATASOURCE = "java:comp/env/jdbc/tienda";

    @Override
    public List<Producto> listarProductos() {
        List<Producto> productos = new ArrayList<>();
        DataSource dataSource = lookupDataSource();

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT id, nombre, descripcion, precio, stock FROM public.producto ORDER BY id"
             )) {
            while (resultSet.next()) {
                productos.add(mapProducto(resultSet));
            }
            return productos;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error consultando productos en la base de datos.", ex);
        }
    }

    @Override
    public List<Producto> listarProductosPaginados(int limit, int offset) {
        List<Producto> productos = new ArrayList<>();
        if (limit <= 0 || offset < 0) {
            return productos;
        }

        DataSource dataSource = lookupDataSource();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT id, nombre, descripcion, precio, stock "
                     + "FROM public.producto ORDER BY id LIMIT ? OFFSET ?"
             )) {
            statement.setInt(1, limit);
            statement.setInt(2, offset);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    productos.add(mapProducto(resultSet));
                }
            }
            return productos;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error consultando productos paginados en la base de datos.", ex);
        }
    }

    @Override
    public List<Producto> listarProductosPaginadosPorTitulo(String titulo, int limit, int offset) {
        String termino = normalizeTitulo(titulo);
        if (termino == null) {
            return listarProductosPaginados(limit, offset);
        }

        List<Producto> productos = new ArrayList<>();
        if (limit <= 0 || offset < 0) {
            return productos;
        }

        DataSource dataSource = lookupDataSource();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT id, nombre, descripcion, precio, stock "
                     + "FROM public.producto "
                     + "WHERE nombre ILIKE ? "
                     + "ORDER BY id LIMIT ? OFFSET ?"
             )) {
            statement.setString(1, "%" + termino + "%");
            statement.setInt(2, limit);
            statement.setInt(3, offset);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    productos.add(mapProducto(resultSet));
                }
            }
            return productos;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error consultando productos filtrados por título.", ex);
        }
    }

    @Override
    public int contarProductos() {
        DataSource dataSource = lookupDataSource();
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT COUNT(1) AS total FROM public.producto")) {
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
            return 0;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error contando productos en la base de datos.", ex);
        }
    }

    @Override
    public int contarProductosPorTitulo(String titulo) {
        String termino = normalizeTitulo(titulo);
        if (termino == null) {
            return contarProductos();
        }

        DataSource dataSource = lookupDataSource();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT COUNT(1) AS total FROM public.producto WHERE nombre ILIKE ?"
             )) {
            statement.setString(1, "%" + termino + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("total");
                }
                return 0;
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error contando productos filtrados por título.", ex);
        }
    }

    @Override
    public boolean autenticarUsuario(String username, String passwordHash) {
        if (username == null || passwordHash == null) {
            return false;
        }

        DataSource dataSource = lookupDataSource();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT 1 FROM public.usuario "
                     + "WHERE username = ? AND clave = ? AND activo = TRUE LIMIT 1"
            )) {
            statement.setString(1, username);
            statement.setString(2, passwordHash);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error validando credenciales de usuario en la base de datos.", ex);
        }
    }

    @Override
    public boolean autenticarAdministrador(String username, String passwordHash) {
        if (username == null || passwordHash == null) {
            return false;
        }

        DataSource dataSource = lookupDataSource();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT 1 FROM public.usuario "
                     + "WHERE username = ? AND clave = ? AND es_admin = TRUE AND activo = TRUE LIMIT 1"
            )) {
            statement.setString(1, username);
            statement.setString(2, passwordHash);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error validando credenciales de administrador en la base de datos.", ex);
        }
    }

    @Override
    public boolean existeUsuarioPorUsername(String username) {
        if (username == null) {
            return false;
        }

        DataSource dataSource = lookupDataSource();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT 1 FROM public.usuario WHERE username = ? LIMIT 1"
             )) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error consultando existencia del usuario en la base de datos.", ex);
        }
    }

    @Override
    public void crearUsuario(String username, String nombreCompleto, String passwordHash, boolean esAdmin, boolean activo, String actorUsername) {
        DataSource dataSource = lookupDataSource();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO public.usuario "
                     + "(username, clave, nombre_completo, es_admin, activo, creado_por, actualizado_por) "
                     + "VALUES (?, ?, ?, ?, ?, ?, ?)"
             )) {
            statement.setString(1, username);
            statement.setString(2, passwordHash);
            statement.setString(3, nombreCompleto);
            statement.setBoolean(4, esAdmin);
            statement.setBoolean(5, activo);
            statement.setString(6, actorUsername);
            statement.setString(7, actorUsername);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException("Error registrando usuario en la base de datos.", ex);
        }
    }

    @Override
    public boolean actualizarClaveAdministrador(
            String username,
            String currentPasswordHash,
            String newPasswordHash,
            String actorUsername
    ) {
        if (username == null || currentPasswordHash == null || newPasswordHash == null || actorUsername == null) {
            return false;
        }

        DataSource dataSource = lookupDataSource();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE public.usuario "
                     + "SET clave = ?, actualizado_por = ?, actualizado_en = CURRENT_TIMESTAMP "
                     + "WHERE username = ? AND clave = ? AND es_admin = TRUE AND activo = TRUE"
             )) {
            statement.setString(1, newPasswordHash);
            statement.setString(2, actorUsername);
            statement.setString(3, username);
            statement.setString(4, currentPasswordHash);
            return statement.executeUpdate() > 0;
        } catch (SQLException ex) {
            throw new IllegalStateException("Error actualizando clave de administrador en la base de datos.", ex);
        }
    }

    @Override
    public Producto buscarProductoPorId(Integer id) {
        if (id == null) {
            return null;
        }

        DataSource dataSource = lookupDataSource();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT id, nombre, descripcion, precio, stock FROM public.producto WHERE id = ?"
             )) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapProducto(resultSet);
                }
                return null;
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error consultando el producto en la base de datos.", ex);
        }
    }

    @Override
    public void actualizarProducto(Producto producto, String actorUsername) {
        DataSource dataSource = lookupDataSource();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE public.producto "
                     + "SET nombre = ?, descripcion = ?, precio = ?, stock = ?, "
                     + "actualizado_por = ?, actualizado_en = CURRENT_TIMESTAMP "
                     + "WHERE id = ?"
             )) {
            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getDescripcion());
            statement.setBigDecimal(3, BigDecimal.valueOf(producto.getPrecio()));
            statement.setInt(4, producto.getStock() == null ? 0 : producto.getStock());
            statement.setString(5, actorUsername);
            statement.setInt(6, producto.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException("Error actualizando el producto en la base de datos.", ex);
        }
    }

    @Override
    public void crearProducto(Producto producto, String actorUsername) {
        DataSource dataSource = lookupDataSource();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO public.producto "
                     + "(nombre, descripcion, precio, stock, creado_por, actualizado_por) "
                     + "VALUES (?, ?, ?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS
             )) {
            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getDescripcion());
            statement.setBigDecimal(3, BigDecimal.valueOf(producto.getPrecio()));
            statement.setInt(4, producto.getStock() == null ? 0 : producto.getStock());
            statement.setString(5, actorUsername);
            statement.setString(6, actorUsername);
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    producto.setId(keys.getInt(1));
                }
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error registrando el producto en la base de datos.", ex);
        }
    }

    @Override
    public void eliminarProducto(Integer id) {
        DataSource dataSource = lookupDataSource();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM public.producto WHERE id = ?"
             )) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException("Error eliminando el producto en la base de datos.", ex);
        }
    }

    private DataSource lookupDataSource() {
        try {
            InitialContext context = new InitialContext();
            return (DataSource) context.lookup(JNDI_DATASOURCE);
        } catch (NamingException ex) {
            throw new IllegalStateException("No se pudo resolver el DataSource JNDI: " + JNDI_DATASOURCE, ex);
        }
    }

    private Producto mapProducto(ResultSet resultSet) throws SQLException {
        return new Producto(
                resultSet.getInt("id"),
                resultSet.getString("nombre"),
                resultSet.getString("descripcion"),
                resultSet.getBigDecimal("precio").doubleValue(),
                resultSet.getInt("stock")
        );
    }

    private String normalizeTitulo(String titulo) {
        if (titulo == null) {
            return null;
        }
        String value = titulo.trim();
        return value.isEmpty() ? null : value;
    }
}
