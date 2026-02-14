package local.unp.desarrolloweb2.tienda.action;

import java.util.Map;
import org.apache.struts2.ActionSupport;
import org.apache.struts2.action.SessionAware;
import org.apache.struts2.interceptor.parameter.StrutsParameter;

public class LoginAction extends ActionSupport implements SessionAware {

    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASSWORD = "desaweb2";

    private Map<String, Object> session;
    private String username;
    private String password;

    @Override
    public String execute() {
        if (ADMIN_USER.equals(username) && ADMIN_PASSWORD.equals(password)) {
            if (session != null) {
                session.put("adminAuthenticated", Boolean.TRUE);
            }
            return SUCCESS;
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

    @Override
    public void withSession(Map<String, Object> session) {
        this.session = session;
    }
}
