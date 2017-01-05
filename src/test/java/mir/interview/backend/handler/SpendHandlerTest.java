package mir.interview.backend.handler;

import static io.restassured.path.json.JsonPath.with;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import ratpack.http.HttpMethod;
import ratpack.http.client.ReceivedResponse;
import ratpack.test.embed.EmbeddedApp;

public class SpendHandlerTest extends BaseHandlerTest {

    private static final String spendJson =
        "{" +
            "\"date\": \"2017-01-05T10:25:43.511Z\"," +
            "\"description\": \"spend transaction\"," +
            "\"amount\": \"100.00\"," +
            "\"currency\": \"GBP\"" +
            "}";

    @Test
    public void spendAndReduceBalance() throws Exception {
        EmbeddedApp.fromHandlers(chain -> chain
            .path("login", new LoginHandler(aerospikeClient))
            .path("balance", new BalanceHandler(aerospikeClient))
            .path("spend", new SpendHandler(aerospikeClient))
        ).test(httpClient -> {

            // Retrieve a valid login token
            String token = with(httpClient.post("login").getBody().getText()).getString("token");

            ReceivedResponse spendResponse = httpClient
                .request("spend", requestSpec -> {
                    requestSpec.method(HttpMethod.POST);
                    requestSpec.getBody().type("application/json");
                    requestSpec.getBody().text(spendJson);
                    requestSpec.getHeaders().add("Authorization", "Bearer " + token);
                });
            assertEquals(200, spendResponse.getStatusCode());

            ReceivedResponse balanceResponse = httpClient
                .request("balance", requestSpec -> requestSpec.getHeaders().add("Authorization", "Bearer " + token));

            assertEquals(200,balanceResponse.getStatusCode());
            assertEquals("1900.0", with(balanceResponse.getBody().getText()).getString("balance"));
            assertEquals("GBP", with(balanceResponse.getBody().getText()).getString("currency"));
        });
    }
}
