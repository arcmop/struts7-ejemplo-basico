package local.unp.desarrolloweb2.tienda.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.ActionInvocation;
import org.apache.struts2.interceptor.AbstractInterceptor;

public class TimerInterceptor extends AbstractInterceptor {

    private static final Logger LOGGER = LogManager.getLogger(TimerInterceptor.class);

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        long start = System.nanoTime();
        String result = invocation.invoke();
        long elapsedMs = (System.nanoTime() - start) / 1000000L;

        LOGGER.info(
                "Action [{}{}!{}] -> result [{}] en {} ms",
                invocation.getProxy().getNamespace(),
                invocation.getProxy().getActionName(),
                invocation.getProxy().getMethod(),
                result,
                elapsedMs
        );

        return result;
    }
}
