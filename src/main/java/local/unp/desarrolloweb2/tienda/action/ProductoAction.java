/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package local.unp.desarrolloweb2.tienda.action;

import org.apache.struts2.inject.Inject;
import java.time.Year;
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

    private TiendaService tiendaService;
    private List<Producto> listaProducto;
    private Integer anio;
    private String anioTexto;
    private boolean admin;
    private String loginErrorCode;
    private String loginError;
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

    public String getLoginError() {
        return loginError;
    }

    public String getLoginErrorCode() {
        return loginErrorCode;
    }

    @StrutsParameter
    public void setLoginErrorCode(String loginErrorCode) {
        this.loginErrorCode = loginErrorCode;
    }

    @Override
    public String execute() throws Exception {
        this.listaProducto = tiendaService.getListaProductos();
        this.anio = Year.now().getValue();
        this.anioTexto = this.anio.toString();

        this.admin = session != null && Boolean.TRUE.equals(session.get("adminAuthenticated"));
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
}
