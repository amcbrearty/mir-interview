package mir.interview.backend.handler;

import static io.restassured.path.json.JsonPath.with;
import static org.junit.Assert.assertEquals;

import java.util.List;

import mir.interview.backend.domain.Spend;
import org.junit.Test;
import ratpack.http.HttpMethod;
import ratpack.http.client.ReceivedResponse;
import ratpack.test.embed.EmbeddedApp;
import ratpack.test.http.TestHttpClient;

public class TransactionHandlerTest extends BaseHandlerTest {

    private static final String spendJson =
        "{" +
            "\"date\": \"2017-01-05T10:25:43.511Z\"," +
            "\"description\": \"spend transaction\"," +
            "\"amount\": \"100.00\"," +
            "\"currency\": \"GBP\"" +
            "}";

    @Test
    public void shouldListTransactionsForGivenAccount() throws Exception {
        EmbeddedApp.fromHandlers(chain -> chain
            .path("login", new LoginHandler(dbService))
            .path("balance", new BalanceHandler(dbService))
            .path("spend", new SpendHandler(dbService))
            .path("transactions", new TransactionHandler(dbService))
        ).test(httpClient -> {

            // Retrieve a valid login token
            String token = with(httpClient.post("login").getBody().getText()).getString("token");

            ReceivedResponse initialBalanceResponse = httpClient
                .request("balance", requestSpec -> requestSpec.getHeaders().add("Authorization", "Bearer " + token));

            assertEquals(200, initialBalanceResponse.getStatusCode());
            assertEquals("2000.0", with(initialBalanceResponse.getBody().getText()).getString("balance"));
            assertEquals("GBP", with(initialBalanceResponse.getBody().getText()).getString("currency"));

            spend(httpClient, token);
            spend(httpClient, token);

            ReceivedResponse transactionsResponse = httpClient
                .request("transactions", requestSpec -> requestSpec.getHeaders().add("Authorization", "Bearer " + token));

            List<Spend> transactions = with(transactionsResponse.getBody().getText()).getList("$", Spend.class);

            assertEquals("unexpected transaction present", 2, transactions.size());
            assertEquals(2, transactions.size());
        });
    }

    private void spend(TestHttpClient httpClient, String token) {
        httpClient
            .request("spend", requestSpec -> {
                requestSpec.method(HttpMethod.POST);
                requestSpec.getBody().type("application/json");
                requestSpec.getBody().text(spendJson);
                requestSpec.getHeaders().add("Authorization", "Bearer " + token);
            });

    }
}
