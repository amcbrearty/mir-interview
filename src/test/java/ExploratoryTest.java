import static org.junit.Assert.assertEquals;

import mir.interview.backend.handler.LoginHandler;
import org.junit.BeforeClass;
import org.junit.Test;
import ratpack.handling.Context;
import ratpack.server.RatpackServer;
import ratpack.test.embed.EmbeddedApp;

/**
 * Cutting my teeth on the rat pack library and getting a feel
 * for how a rat pack API is configured, implemented & tested.
 */
public class ExploratoryTest {

    private static RatpackServer server;
    private static EmbeddedApp embeddedApp;

    @BeforeClass
    public static void configureServer() throws Exception {
        server = RatpackServer.of(spec -> spec
            .handlers(chain -> chain
                .path("login", new LoginHandler())
                .all(Context::next)
                .all(ctx -> ctx
                    .byMethod(method -> method
                        .get(() -> ctx.render("GET"))
                        .post(() -> ctx.render("POST"))
                        .put(() -> ctx.render("PUT"))
                        .delete(() -> ctx.render("DELETE"))
                    )
                )
            )
        );

        embeddedApp = EmbeddedApp.fromServer(server);
    }

    @Test
    public void testGet() throws Exception {
        embeddedApp.test(testHttpClient -> assertEquals("GET", testHttpClient.getText()));
    }

    @Test
    public void testPost() throws Exception {
        embeddedApp.test(testHttpClient -> assertEquals("POST", testHttpClient.postText()));
    }

    @Test
    public void testPut() throws Exception {
        embeddedApp.test(testHttpClient -> assertEquals("PUT", testHttpClient.putText()));
    }

    @Test
    public void testDelete() throws Exception {
        embeddedApp.test(testHttpClient -> assertEquals("DELETE", testHttpClient.deleteText()));
    }

    @Test
    public void testLoginPost() throws Exception {
        embeddedApp.test(httpClient -> assertEquals("login", httpClient.post("login").getBody().getText()));
    }

    @Test
    public void testLoginGet() throws Exception {
        embeddedApp.test(httpClient -> assertEquals("", httpClient.get("login").getBody().getText()));
    }
}
