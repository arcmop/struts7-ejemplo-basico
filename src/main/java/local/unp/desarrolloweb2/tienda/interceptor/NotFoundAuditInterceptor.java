package local.unp.desarrolloweb2.tienda.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ActionInvocation;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.AbstractInterceptor;

public class NotFoundAuditInterceptor extends AbstractInterceptor {

    private static final Logger LOGGER = LogManager.getLogger(NotFoundAuditInterceptor.class);

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        HttpServletRequest request = resolveRequest(invocation);
        if (request == null) {
            LOGGER.warn("NotFoundAuditInterceptor invocado sin HttpServletRequest disponible.");
        } else {
            LOGGER.warn(
                    "Solicitud no encontrada detectada: method={}, uri={}, query={}, remoteAddr={}, xForwardedFor={}, userAgent={}",
                    sanitizeForLog(request.getMethod()),
                    sanitizeForLog(request.getRequestURI()),
                    sanitizeForLog(request.getQueryString()),
                    sanitizeForLog(request.getRemoteAddr()),
                    sanitizeForLog(request.getHeader("X-Forwarded-For")),
                    sanitizeForLog(request.getHeader("User-Agent"))
            );
        }

        return invocation.invoke();
    }

    private HttpServletRequest resolveRequest(ActionInvocation invocation) {
        Object request = invocation.getInvocationContext().get(ServletActionContext.HTTP_REQUEST);
        return request instanceof HttpServletRequest ? (HttpServletRequest) request : null;
    }

    private String sanitizeForLog(String value) {
        if (value == null) {
            return "-";
        }
        String sanitized = value.replace('\r', ' ').replace('\n', ' ').trim();
        if (sanitized.isEmpty()) {
            return "-";
        }
        if (sanitized.length() > 300) {
            return sanitized.substring(0, 300) + "...";
        }
        return sanitized;
    }
}
