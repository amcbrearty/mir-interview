package mir.interview.backend.handler;

import static ratpack.jackson.Jackson.json;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import mir.interview.backend.domain.Balance;
import ratpack.handling.Context;
import ratpack.handling.Handler;

public class BalanceHandler implements Handler {

    @Override
    public void handle(Context ctx) throws Exception {
        String authorisationHeader = ctx.getRequest().getHeaders().get("Authorization");
        String[] splitHeader = authorisationHeader.split("Bearer ");
        String token = splitHeader[1];

        AerospikeClient aerospikeClient = new AerospikeClient("172.28.128.3", 3000);

        String uuid = JWT.require(Algorithm.HMAC256("secret")).withIssuer("amcbrearty").build().verify(token).getClaim("uuid").asString();

        Key key = new Key("test", "account", uuid);
        Record record = aerospikeClient.get(null, key);

        ctx.byMethod(method -> method.get(() -> ctx.render(json(new Balance(record.bins.get("balance").toString(), record.bins.get("currency").toString())))));
    }
}
