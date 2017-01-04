package mir.interview.backend.handler;

import static io.restassured.path.json.JsonPath.with;
import static org.junit.Assert.assertEquals;

import com.aerospike.client.AerospikeClient;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ratpack.http.client.ReceivedResponse;
import ratpack.test.embed.EmbeddedApp;

public class BalanceHandlerTest {

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
    public void testGetInitialBalance() throws Exception {
        EmbeddedApp.fromHandlers(chain -> chain
            .path("login", new LoginHandler())
            .path("balance", new BalanceHandler())
        ).test(httpClient -> {

            // Retrieve a valid login token
            String token = with(httpClient.post("login").getBody().getText()).getString("token");

            ReceivedResponse response = httpClient
                .request("balance", requestSpec -> requestSpec.getHeaders().add("Authorization", "Bearer " + token));

            assertEquals(200,response.getStatusCode());
            assertEquals("2000", with(response.getBody().getText()).getString("balance"));
            assertEquals("GBP", with(response.getBody().getText()).getString("currency"));
        });
    }
}
