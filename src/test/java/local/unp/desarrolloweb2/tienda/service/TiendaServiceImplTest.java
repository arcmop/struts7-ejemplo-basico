package local.unp.desarrolloweb2.tienda.service;

import local.unp.desarrolloweb2.tienda.dao.TiendaDao;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

public class TiendaServiceImplTest {

    @Test
    public void testAutenticarAdministradorHashesPasswordBeforeDaoLookup() {
        TiendaDao tiendaDao = mock(TiendaDao.class);
        String passwordHash = "b4d97856c0e4228bfcc3403dc610857d162a4d2fb18c1b01fd5a5037f2a18496";
        when(tiendaDao.autenticarAdministrador("admin", passwordHash)).thenReturn(true);

        TiendaServiceImpl service = new TiendaServiceImpl(tiendaDao);

        boolean authenticated = service.autenticarAdministrador("admin", "desaweb2");

        assertTrue(authenticated);
        verify(tiendaDao).autenticarAdministrador("admin", passwordHash);
    }

    @Test
    public void testAutenticarAdministradorReturnsFalseForInvalidInput() {
        TiendaDao tiendaDao = mock(TiendaDao.class);
        TiendaServiceImpl service = new TiendaServiceImpl(tiendaDao);

        assertFalse(service.autenticarAdministrador(null, "desaweb2"));
        assertFalse(service.autenticarAdministrador("admin", null));
        assertFalse(service.autenticarAdministrador("   ", "desaweb2"));
        assertFalse(service.autenticarAdministrador("admin", ""));
        verifyNoInteractions(tiendaDao);
    }

    @Test
    public void testRegistrarUsuarioHashesPasswordBeforeCreate() {
        TiendaDao tiendaDao = mock(TiendaDao.class);
        when(tiendaDao.existeUsuarioPorUsername("nuevo_admin")).thenReturn(false);
        TiendaServiceImpl service = new TiendaServiceImpl(tiendaDao);

        boolean created = service.registrarUsuario("nuevo_admin", "Nuevo Admin", "desaweb2", true);

        assertTrue(created);
        verify(tiendaDao).existeUsuarioPorUsername("nuevo_admin");
        verify(tiendaDao).crearUsuario(
                eq("nuevo_admin"),
                eq("Nuevo Admin"),
                eq("b4d97856c0e4228bfcc3403dc610857d162a4d2fb18c1b01fd5a5037f2a18496"),
                eq(true),
                anyBoolean()
        );
    }

    @Test
    public void testRegistrarUsuarioReturnsFalseWhenAlreadyExists() {
        TiendaDao tiendaDao = mock(TiendaDao.class);
        when(tiendaDao.existeUsuarioPorUsername("admin")).thenReturn(true);
        TiendaServiceImpl service = new TiendaServiceImpl(tiendaDao);

        boolean created = service.registrarUsuario("admin", "Administrador", "desaweb2", true);

        assertFalse(created);
        verify(tiendaDao).existeUsuarioPorUsername("admin");
    }

    @Test
    public void testCambiarClaveAdministradorHashesBothPasswords() {
        TiendaDao tiendaDao = mock(TiendaDao.class);
        when(tiendaDao.actualizarClaveAdministrador(
                "admin",
                "b4d97856c0e4228bfcc3403dc610857d162a4d2fb18c1b01fd5a5037f2a18496",
                "ee9a8dd4b6ef13326a77657bf08af2114aa2e951cb8a1a16341681f469b7b330"
        )).thenReturn(true);
        TiendaServiceImpl service = new TiendaServiceImpl(tiendaDao);

        boolean changed = service.cambiarClaveAdministrador("admin", "desaweb2", "nuevaClave123");

        assertTrue(changed);
        verify(tiendaDao).actualizarClaveAdministrador(
                "admin",
                "b4d97856c0e4228bfcc3403dc610857d162a4d2fb18c1b01fd5a5037f2a18496",
                "ee9a8dd4b6ef13326a77657bf08af2114aa2e951cb8a1a16341681f469b7b330"
        );
    }
}
