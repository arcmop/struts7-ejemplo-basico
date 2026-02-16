package local.unp.desarrolloweb2.tienda.dao;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TiendaDaoImplTest {

    @Test
    public void testListarProductosPaginadosReturnsEmptyWhenLimitIsNotPositive() {
        TiendaDaoImpl dao = new TiendaDaoImpl();

        assertTrue(dao.listarProductosPaginados(0, 0).isEmpty());
    }

    @Test
    public void testListarProductosPaginadosReturnsEmptyWhenOffsetIsNegative() {
        TiendaDaoImpl dao = new TiendaDaoImpl();

        assertTrue(dao.listarProductosPaginados(10, -1).isEmpty());
    }

    @Test
    public void testListarProductosPaginadosPorTituloReturnsEmptyWhenTituloNullAndPaginationInvalid() {
        TiendaDaoImpl dao = new TiendaDaoImpl();

        assertTrue(dao.listarProductosPaginadosPorTitulo(null, 0, 0).isEmpty());
    }

    @Test
    public void testListarProductosPaginadosPorTituloReturnsEmptyWhenPaginationInvalid() {
        TiendaDaoImpl dao = new TiendaDaoImpl();

        assertTrue(dao.listarProductosPaginadosPorTitulo("laptop", 0, 0).isEmpty());
    }

    @Test
    public void testAutenticarUsuarioReturnsFalseWithNullData() {
        TiendaDaoImpl dao = new TiendaDaoImpl();

        assertFalse(dao.autenticarUsuario(null, "hash"));
        assertFalse(dao.autenticarUsuario("usuario", null));
    }

    @Test
    public void testAutenticarAdministradorReturnsFalseWithNullData() {
        TiendaDaoImpl dao = new TiendaDaoImpl();

        assertFalse(dao.autenticarAdministrador(null, "hash"));
        assertFalse(dao.autenticarAdministrador("admin", null));
    }

    @Test
    public void testExisteUsuarioPorUsernameReturnsFalseWhenUsernameIsNull() {
        TiendaDaoImpl dao = new TiendaDaoImpl();

        assertFalse(dao.existeUsuarioPorUsername(null));
    }

    @Test
    public void testActualizarClaveAdministradorReturnsFalseWithMissingData() {
        TiendaDaoImpl dao = new TiendaDaoImpl();

        assertFalse(dao.actualizarClaveAdministrador(null, "old", "new", "actor"));
        assertFalse(dao.actualizarClaveAdministrador("admin", null, "new", "actor"));
        assertFalse(dao.actualizarClaveAdministrador("admin", "old", null, "actor"));
        assertFalse(dao.actualizarClaveAdministrador("admin", "old", "new", null));
    }

    @Test
    public void testBuscarProductoPorIdReturnsNullWhenIdIsNull() {
        TiendaDaoImpl dao = new TiendaDaoImpl();

        assertNull(dao.buscarProductoPorId(null));
    }
}
