package local.unp.desarrolloweb2.tienda.interceptor;

import java.util.Map;
import org.apache.struts2.ActionInvocation;
import org.apache.struts2.interceptor.AbstractInterceptor;

public class AdminSessionInterceptor extends AbstractInterceptor {

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        Map<String, Object> session = invocation.getInvocationContext().getSession();
        if (session != null && Boolean.TRUE.equals(session.get("adminAuthenticated"))) {
            return invocation.invoke();
        }
        return "loginRequired";
    }
}
