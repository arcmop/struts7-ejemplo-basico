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
    public void actualizarProducto(Producto producto) {
        DataSource dataSource = lookupDataSource();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE public.producto SET nombre = ?, descripcion = ?, precio = ?, stock = ? WHERE id = ?"
             )) {
            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getDescripcion());
            statement.setBigDecimal(3, BigDecimal.valueOf(producto.getPrecio()));
            statement.setInt(4, producto.getStock() == null ? 0 : producto.getStock());
            statement.setInt(5, producto.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException("Error actualizando el producto en la base de datos.", ex);
        }
    }

    @Override
    public void crearProducto(Producto producto) {
        DataSource dataSource = lookupDataSource();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO public.producto (nombre, descripcion, precio, stock) VALUES (?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS
             )) {
            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getDescripcion());
            statement.setBigDecimal(3, BigDecimal.valueOf(producto.getPrecio()));
            statement.setInt(4, producto.getStock() == null ? 0 : producto.getStock());
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
}
