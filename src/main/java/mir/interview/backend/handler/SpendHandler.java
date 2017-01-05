package mir.interview.backend.handler;


import static ratpack.jackson.Jackson.fromJson;

import java.util.UUID;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import mir.interview.backend.domain.Spend;
import ratpack.handling.Context;
import ratpack.handling.Handler;

public class SpendHandler implements Handler {

    private AerospikeClient aerospikeClient;

    public SpendHandler(AerospikeClient aerospikeClient) {
        this.aerospikeClient = aerospikeClient;
    }

    @Override
    public void handle(Context ctx) throws Exception {

        String authorisationHeader = ctx.getRequest().getHeaders().get("Authorization");
        String[] splitHeader = authorisationHeader.split("Bearer ");
        String token = splitHeader[1];

        String accountUuid = JWT.require(Algorithm.HMAC256("secret")).withIssuer("amcbrearty").build().verify(token).getClaim("uuid").asString();

        ctx.parse(fromJson(Spend.class)).then((Spend spend) -> {
            String spendUuid = UUID.randomUUID().toString();

            Key accountKey = new Key("test", "account", accountUuid);
            Record balanceRecord = aerospikeClient.get(null, accountKey);

            Double currentBalance = Double.parseDouble(balanceRecord.bins.get("balance").toString());
            Double updatedBalance = currentBalance - spend.getAmount();

            Bin balanceBin = new Bin("balance", updatedBalance);

            aerospikeClient.put(null, accountKey, balanceBin);

            // Namespace change from test in the aerospace configuration is preferred
            Key spendKey = new Key("test", "spend", spendUuid);

            Bin accountBin = new Bin("uuid", accountUuid);
            Bin dateBin = new Bin("date", spend.getDate());
            Bin descriptionBin = new Bin("description", spend.getDescription());
            Bin amountBin = new Bin("amount", spend.getAmount());
            Bin currencyBin = new Bin("currency", spend.getCurrency());

            aerospikeClient.put(null, spendKey, accountBin, dateBin, descriptionBin, amountBin, currencyBin);

            ctx.byMethod(method -> method.post(() -> ctx.render("")));
        });
    }
}
