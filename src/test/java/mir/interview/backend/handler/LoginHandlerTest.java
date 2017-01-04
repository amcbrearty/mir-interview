package mir.interview.backend.handler;

import static io.restassured.path.json.JsonPath.with;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ratpack.test.embed.EmbeddedApp;

public class LoginHandlerTest {

    private AerospikeClient aerospikeClient;

    @Before
    public void connectToAerospikeClient() {
        aerospikeClient = new AerospikeClient("172.28.128.3", 3000);
    }

    @After
    public void closeAerospikeClient() {
        aerospikeClient.close();
    }

    @Test
    public void testLoginPost() throws Exception {
        EmbeddedApp.fromHandler(new LoginHandler())
            .test(httpClient -> {
                String token = with(httpClient.post().getBody().getText()).getString("token");

                assertNotNull("Token not found in login response", token);
                String uuid = JWT.require(Algorithm.HMAC256("secret")).withIssuer("amcbrearty").build().verify(token).getClaim("uuid").asString();

                Key key = new Key("test", "account", uuid);
                Record record = aerospikeClient.get(null, key);

                assertTrue("Balance not created", record.bins.containsKey("balance"));
                assertTrue("Currency not created", record.bins.containsKey("currency"));
                assertEquals("Initial balance incorrect", "2000", record.bins.get("balance").toString());
                assertEquals("Initial currency incorrect", "GBP", record.bins.get("currency"));
            });
    }

    @Test
    public void testLoginGet() throws Exception {
        EmbeddedApp.fromHandler(new LoginHandler())
            .test(httpClient -> assertEquals(405, httpClient.get().getStatusCode()));
    }
}
