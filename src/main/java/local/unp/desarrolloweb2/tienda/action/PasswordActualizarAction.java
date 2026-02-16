package local.unp.desarrolloweb2.tienda.action;

import java.util.Map;
import local.unp.desarrolloweb2.tienda.service.TiendaService;
import org.apache.struts2.ActionSupport;
import org.apache.struts2.action.SessionAware;
import org.apache.struts2.inject.Inject;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

public class PasswordActualizarAction extends ActionSupport implements SessionAware {

    private static final int PASSWORD_MIN = 8;
    private static final int PASSWORD_MAX = 72;

    private TiendaService tiendaService;
    private Map<String, Object> session;
    private String currentPassword;
    private String newPassword;
    private String confirmNewPassword;

    @Override
    public String execute() {
        String adminUsername = getAdminUsername();
        if (adminUsername == null) {
            addActionError(getText("auth.error.required"));
            return INPUT;
        }

        boolean actualizado = tiendaService.cambiarClaveAdministrador(
                adminUsername,
                currentPassword,
                newPassword,
                adminUsername
        );
        if (!actualizado) {
            addFieldError("currentPassword", getText("error.password.current.invalid"));
            return INPUT;
        }

        addActionMessage(getText("password.change.success"));
        currentPassword = null;
        newPassword = null;
        confirmNewPassword = null;
        return SUCCESS;
    }

    @Override
    public void validate() {
        if (currentPassword == null || currentPassword.isEmpty()) {
            addFieldError("currentPassword", getText("error.password.current.required"));
        }

        if (newPassword == null || newPassword.isEmpty()) {
            addFieldError("newPassword", getText("error.password.new.required"));
        } else if (newPassword.length() < PASSWORD_MIN || newPassword.length() > PASSWORD_MAX) {
            addFieldError("newPassword", getText("error.password.new.length"));
        }

        if (confirmNewPassword == null || confirmNewPassword.isEmpty()) {
            addFieldError("confirmNewPassword", getText("error.password.confirm.required"));
        } else if (newPassword != null && !newPassword.equals(confirmNewPassword)) {
            addFieldError("confirmNewPassword", getText("error.password.confirm.mismatch"));
        }

        if (currentPassword != null && newPassword != null && !currentPassword.isEmpty()
                && !newPassword.isEmpty() && currentPassword.equals(newPassword)) {
            addFieldError("newPassword", getText("error.password.new.same"));
        }
    }

    @StrutsParameter
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    @StrutsParameter
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @StrutsParameter
    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    @Inject("tiendaService")
    public void setTiendaService(TiendaService tiendaService) {
        this.tiendaService = tiendaService;
    }

    @Override
    public void withSession(Map<String, Object> session) {
        this.session = session;
    }

    private String getAdminUsername() {
        if (session == null) {
            return null;
        }
        Object value = session.get("adminUsername");
        return value instanceof String ? (String) value : null;
    }
}
