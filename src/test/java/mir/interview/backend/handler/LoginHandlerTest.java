package mir.interview.backend.handler;

import static io.restassured.path.json.JsonPath.with;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.junit.Test;
import ratpack.test.embed.EmbeddedApp;

public class LoginHandlerTest {

    @Test
    public void testLoginPost() throws Exception {

        EmbeddedApp.fromHandler(new LoginHandler())
            .test(httpClient -> {
                String token = with(httpClient.post().getBody().getText()).getString("token");

                assertNotNull("Token not found in login response", token);
                JWT.require(Algorithm.HMAC256("secret")).withIssuer("amcbrearty").build().verify(token);
            });
    }

    @Test
    public void testLoginGet() throws Exception {
        EmbeddedApp.fromHandler(new LoginHandler())
            .test(httpClient -> assertEquals(405, httpClient.get().getStatusCode()));
    }
}
