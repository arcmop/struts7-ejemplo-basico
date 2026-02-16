package local.unp.desarrolloweb2.tienda.interceptor;

import java.util.HashMap;
import java.util.Map;
import org.apache.struts2.ActionInvocation;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AdminSessionInterceptorTest {

    @Test
    public void testInterceptInvokesActionWhenAdminIsAuthenticated() throws Exception {
        AdminSessionInterceptor interceptor = new AdminSessionInterceptor();
        ActionInvocation invocation = mock(ActionInvocation.class, RETURNS_DEEP_STUBS);
        Map<String, Object> session = new HashMap<>();
        session.put("adminAuthenticated", Boolean.TRUE);
        when(invocation.getInvocationContext().getSession()).thenReturn(session);
        when(invocation.invoke()).thenReturn("success");

        String result = interceptor.intercept(invocation);

        assertEquals("success", result);
        verify(invocation).invoke();
    }

    @Test
    public void testInterceptReturnsLoginRequiredWhenAdminIsNotAuthenticated() throws Exception {
        AdminSessionInterceptor interceptor = new AdminSessionInterceptor();
        ActionInvocation invocation = mock(ActionInvocation.class, RETURNS_DEEP_STUBS);
        when(invocation.getInvocationContext().getSession()).thenReturn(new HashMap<String, Object>());

        String result = interceptor.intercept(invocation);

        assertEquals("loginRequired", result);
        verify(invocation, never()).invoke();
    }
}
