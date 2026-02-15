package local.unp.desarrolloweb2.tienda.action;

import java.util.Map;
import local.unp.desarrolloweb2.tienda.service.TiendaService;
import org.apache.struts2.ActionSupport;
import org.apache.struts2.inject.Inject;
import org.apache.struts2.action.SessionAware;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

public class LoginAction extends ActionSupport implements SessionAware {

    private TiendaService tiendaService;
    private Map<String, Object> session;
    private String username;
    private String password;

    @Override
    public String execute() {
        if (tiendaService == null) {
            return INPUT;
        }

        if (tiendaService.autenticarUsuario(username, password)) {
            boolean admin = tiendaService.autenticarAdministrador(username, password);
            if (session != null) {
                session.put("userAuthenticated", Boolean.TRUE);
                session.put("authenticatedUsername", username);
                if (admin) {
                    session.put("adminAuthenticated", Boolean.TRUE);
                    session.put("adminUsername", username);
                } else {
                    session.remove("adminAuthenticated");
                    session.remove("adminUsername");
                }
            }
            return SUCCESS;
        }

        if (session != null) {
            session.remove("userAuthenticated");
            session.remove("authenticatedUsername");
            session.remove("adminAuthenticated");
            session.remove("adminUsername");
        }
        return INPUT;
    }

    @StrutsParameter
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    @StrutsParameter
    public void setPassword(String password) {
        this.password = password;
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
