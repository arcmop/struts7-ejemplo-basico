package local.unp.desarrolloweb2.tienda.action;

import java.util.Map;
import local.unp.desarrolloweb2.tienda.service.TiendaService;
import org.apache.struts2.ActionSupport;
import org.apache.struts2.action.SessionAware;
import org.apache.struts2.inject.Inject;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

public class UsuarioRegistrarAction extends ActionSupport implements SessionAware {

    private static final int USERNAME_MIN = 3;
    private static final int USERNAME_MAX = 60;
    private static final int NOMBRE_COMPLETO_MAX = 120;
    private static final int PASSWORD_MIN = 8;
    private static final int PASSWORD_MAX = 72;

    private TiendaService tiendaService;
    private String username;
    private String nombreCompleto;
    private String password;
    private String confirmPassword;
    private Boolean esAdmin;
    private Map<String, Object> session;

    @Override
    public String execute() {
        boolean creado = tiendaService.registrarUsuario(
                username,
                nombreCompleto,
                password,
                Boolean.TRUE.equals(esAdmin),
                resolveActorUsername()
        );
        if (!creado) {
            addFieldError("username", getText("error.usuario.username.exists"));
            return INPUT;
        }

        addActionMessage(getText("user.create.success"));
        username = null;
        nombreCompleto = null;
        password = null;
        confirmPassword = null;
        esAdmin = Boolean.FALSE;
        return SUCCESS;
    }

    @Override
    public void validate() {
        if (username != null) {
            username = username.trim();
        }
        if (nombreCompleto != null) {
            nombreCompleto = nombreCompleto.trim();
        }

        if (username == null || username.isEmpty()) {
            addFieldError("username", getText("error.usuario.username.required"));
        } else {
            if (username.length() < USERNAME_MIN || username.length() > USERNAME_MAX) {
                addFieldError("username", getText("error.usuario.username.length"));
            }
            if (!username.matches("^[a-zA-Z0-9._-]+$")) {
                addFieldError("username", getText("error.usuario.username.format"));
            }
        }

        if (nombreCompleto != null && !nombreCompleto.isEmpty() && nombreCompleto.length() > NOMBRE_COMPLETO_MAX) {
            addFieldError("nombreCompleto", getText("error.usuario.nombre.length"));
        }

        if (password == null || password.isEmpty()) {
            addFieldError("password", getText("error.usuario.password.required"));
        } else if (password.length() < PASSWORD_MIN || password.length() > PASSWORD_MAX) {
            addFieldError("password", getText("error.usuario.password.length"));
        }

        if (confirmPassword == null || confirmPassword.isEmpty()) {
            addFieldError("confirmPassword", getText("error.usuario.password.confirm.required"));
        } else if (password != null && !password.equals(confirmPassword)) {
            addFieldError("confirmPassword", getText("error.usuario.password.confirm.mismatch"));
        }
    }

    public String getUsername() {
        return username;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public Boolean getEsAdmin() {
        return esAdmin;
    }

    @StrutsParameter
    public void setUsername(String username) {
        this.username = username;
    }

    @StrutsParameter
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    @StrutsParameter
    public void setPassword(String password) {
        this.password = password;
    }

    @StrutsParameter
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @StrutsParameter
    public void setEsAdmin(Boolean esAdmin) {
        this.esAdmin = esAdmin;
    }

    @Inject("tiendaService")
    public void setTiendaService(TiendaService tiendaService) {
        this.tiendaService = tiendaService;
    }

    @Override
    public void withSession(Map<String, Object> session) {
        this.session = session;
    }

    private String resolveActorUsername() {
        if (session == null) {
            return null;
        }
        Object value = session.get("authenticatedUsername");
        if (value instanceof String) {
            String usernameValue = ((String) value).trim();
            if (!usernameValue.isEmpty()) {
                return usernameValue;
            }
        }
        value = session.get("adminUsername");
        if (value instanceof String) {
            String usernameValue = ((String) value).trim();
            if (!usernameValue.isEmpty()) {
                return usernameValue;
            }
        }
        return null;
    }
}
