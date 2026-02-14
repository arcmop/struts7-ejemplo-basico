package local.unp.desarrolloweb2.tienda.action;

import java.util.Map;
import org.apache.struts2.ActionSupport;
import org.apache.struts2.action.SessionAware;
import org.apache.struts2.dispatcher.SessionMap;

public class LogoutAction extends ActionSupport implements SessionAware {

    private Map<String, Object> session;

    @Override
    public String execute() {
        if (session instanceof SessionMap) {
            ((SessionMap) session).invalidate();
        } else if (session != null) {
            session.remove("adminAuthenticated");
        }
        return SUCCESS;
    }

    @Override
    public void withSession(Map<String, Object> session) {
        this.session = session;
    }
}
