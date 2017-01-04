package mir.interview.backend.handler;

import org.junit.Test;
import ratpack.test.embed.EmbeddedApp;

public class LoginHandlerTest {

    @Test
    public void testLoginPost() throws Exception {
        EmbeddedApp.fromHandler(new LoginHandler());
    }
}
