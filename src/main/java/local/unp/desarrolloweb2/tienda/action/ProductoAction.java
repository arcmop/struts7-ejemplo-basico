/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package local.unp.desarrolloweb2.tienda.action;

import org.apache.struts2.inject.Inject;
import java.time.Year;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import local.unp.desarrolloweb2.tienda.model.Producto;
import local.unp.desarrolloweb2.tienda.service.TiendaService;
import org.apache.struts2.ActionSupport;
import org.apache.struts2.action.SessionAware;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

/**
 *
 * @author mastermind
 */
public class ProductoAction extends ActionSupport implements SessionAware {

    private static final int PRODUCTOS_POR_PAGINA = 10;

    private TiendaService tiendaService;
    private List<Producto> listaProducto;
    private Integer anio;
    private String anioTexto;
    private boolean autenticado;
    private boolean admin;
    private String usernameAutenticado;
    private String loginErrorCode;
    private String loginError;
    private Integer page;
    private int paginaActual;
    private int totalPaginas;
    private Map<String, Object> session;

    @StrutsParameter(depth = 1)
    public void setListaProducto(List<Producto> listaProducto) {
        this.listaProducto = listaProducto;
    }

    public List<Producto> getListaProducto() {
        return listaProducto;
    }

    public Integer getAnio() {
        return anio;
    }

    public String getAnioTexto() {
        return anioTexto;
    }

    public boolean isAdmin() {
        return admin;
    }

    public boolean isAutenticado() {
        return autenticado;
    }

    public String getLoginError() {
        return loginError;
    }

    public String getUsernameAutenticado() {
        return usernameAutenticado;
    }

    public String getLoginErrorCode() {
        return loginErrorCode;
    }

    public int getPaginaActual() {
        return paginaActual;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public boolean isTienePaginaAnterior() {
        return paginaActual > 1;
    }

    public boolean isTienePaginaSiguiente() {
        return paginaActual < totalPaginas;
    }

    public int getPaginaAnterior() {
        return paginaActual - 1;
    }

    public int getPaginaSiguiente() {
        return paginaActual + 1;
    }

    @StrutsParameter
    public void setLoginErrorCode(String loginErrorCode) {
        this.loginErrorCode = loginErrorCode;
    }

    @StrutsParameter
    public void setPage(Integer page) {
        this.page = page;
    }

    @Override
    public String execute() throws Exception {
        int totalProductos = tiendaService.getTotalProductos();
        this.totalPaginas = totalProductos == 0 ? 1 : (int) Math.ceil((double) totalProductos / PRODUCTOS_POR_PAGINA);
        this.paginaActual = normalizePage(page, totalPaginas);

        if (totalProductos == 0) {
            this.listaProducto = Collections.emptyList();
        } else {
            this.listaProducto = tiendaService.getListaProductosPaginados(paginaActual, PRODUCTOS_POR_PAGINA);
        }
        this.anio = Year.now().getValue();
        this.anioTexto = this.anio.toString();

        this.autenticado = session != null
                && (Boolean.TRUE.equals(session.get("userAuthenticated"))
                || Boolean.TRUE.equals(session.get("adminAuthenticated")));
        this.admin = session != null && Boolean.TRUE.equals(session.get("adminAuthenticated"));
        this.usernameAutenticado = resolveAuthenticatedUsername();
        if (loginErrorCode != null && !loginErrorCode.trim().isEmpty()) {
            this.loginError = getText(loginErrorCode.trim());
        }

        return SUCCESS;
    }

    @Inject("tiendaService")
    public void setTiendaService(TiendaService tiendaService) {
        this.tiendaService = tiendaService;
    }

    @Override
    public void withSession(Map<String, Object> session) {
        this.session = session;
    }

    private int normalizePage(Integer requestedPage, int maxPages) {
        if (requestedPage == null || requestedPage < 1) {
            return 1;
        }
        if (requestedPage > maxPages) {
            return maxPages;
        }
        return requestedPage;
    }

    private String resolveAuthenticatedUsername() {
        if (session == null) {
            return null;
        }
        Object username = session.get("authenticatedUsername");
        if (username instanceof String) {
            String value = ((String) username).trim();
            if (!value.isEmpty()) {
                return value;
            }
        }

        Object adminUsername = session.get("adminUsername");
        if (adminUsername instanceof String) {
            String value = ((String) adminUsername).trim();
            if (!value.isEmpty()) {
                return value;
            }
        }
        return null;
    }
}
