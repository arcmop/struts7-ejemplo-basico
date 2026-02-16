package local.unp.desarrolloweb2.tienda.action;

import jakarta.servlet.http.HttpServletResponse;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class NotFoundActionTest {

    @Test
    public void testExecuteSetsNotFoundStatus() {
        HttpServletResponse response = mock(HttpServletResponse.class);
        NotFoundAction action = new NotFoundAction();
        action.withServletResponse(response);

        String result = action.execute();

        assertEquals("success", result);
        verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
}
