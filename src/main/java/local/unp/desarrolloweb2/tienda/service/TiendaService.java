/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package local.unp.desarrolloweb2.tienda.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import local.unp.desarrolloweb2.tienda.model.Producto;

/**
 *
 * @author mastermind
 */
public class TiendaService {

    private static final String JNDI_DATASOURCE = "java:comp/env/jdbc/tienda";

    public static List<Producto> getListaProductos() {
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
        } catch (SQLException ex) {
            throw new IllegalStateException("Error consultando productos en la base de datos.", ex);
        }

        return productos;
    }

    public static Producto getProductoPorId(Integer id) {
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
            }
        } catch (SQLException ex) {
            throw new IllegalStateException("Error consultando el producto en la base de datos.", ex);
        }

        return null;
    }

    public static void actualizarProducto(Producto producto) {
        if (producto == null || producto.getId() == null || producto.getPrecio() == null) {
            throw new IllegalArgumentException("Producto inválido para actualizar.");
        }

        DataSource dataSource = lookupDataSource();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE public.producto SET nombre = ?, descripcion = ?, precio = ?, stock = ? WHERE id = ?"
             )) {
            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getDescripcion());
            statement.setBigDecimal(3, java.math.BigDecimal.valueOf(producto.getPrecio()));
            statement.setInt(4, producto.getStock() == null ? 0 : producto.getStock());
            statement.setInt(5, producto.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new IllegalStateException("Error actualizando el producto en la base de datos.", ex);
        }
    }

    public static void crearProducto(Producto producto) {
        if (producto == null || producto.getPrecio() == null) {
            throw new IllegalArgumentException("Producto inválido para registrar.");
        }

        DataSource dataSource = lookupDataSource();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO public.producto (nombre, descripcion, precio, stock) VALUES (?, ?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS
             )) {
            statement.setString(1, producto.getNombre());
            statement.setString(2, producto.getDescripcion());
            statement.setBigDecimal(3, java.math.BigDecimal.valueOf(producto.getPrecio()));
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

    public static void eliminarProducto(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id de producto inválido para eliminar.");
        }

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

    private static DataSource lookupDataSource() {
        try {
            InitialContext context = new InitialContext();
            return (DataSource) context.lookup(JNDI_DATASOURCE);
        } catch (NamingException ex) {
            throw new IllegalStateException("No se pudo resolver el DataSource JNDI: " + JNDI_DATASOURCE, ex);
        }
    }

    private static Producto mapProducto(ResultSet resultSet) throws SQLException {
        return new Producto(
                resultSet.getInt("id"),
                resultSet.getString("nombre"),
                resultSet.getString("descripcion"),
                resultSet.getBigDecimal("precio").doubleValue(),
                resultSet.getInt("stock")
        );
    }
}
