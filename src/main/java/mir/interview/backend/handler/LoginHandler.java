package mir.interview.backend.handler;

import static ratpack.jackson.Jackson.json;

import java.util.UUID;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import mir.interview.backend.domain.Token;
import ratpack.handling.Context;
import ratpack.handling.Handler;

public class LoginHandler implements Handler {

    private AerospikeClient aerospikeClient;

    public LoginHandler(AerospikeClient aerospikeClient) {
        this.aerospikeClient = aerospikeClient;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        String uuid = UUID.randomUUID().toString();

        String token = JWT.create()
            .withClaim("uuid", uuid)
            .withIssuer("amcbrearty")
            .sign(Algorithm.HMAC256("secret"));

        // Namespace change from test in the aerospace configuration is preferred
        Key accountKey = new Key("test", "account", uuid);
        Bin accountBalance = new Bin("balance", 2000.0);
        Bin accountCurrency = new Bin("currency", "GBP");

        aerospikeClient.put(null, accountKey, accountBalance, accountCurrency);

        ctx.byMethod(method -> method.post(() -> ctx.render(json(new Token(token)))));
    }
}
