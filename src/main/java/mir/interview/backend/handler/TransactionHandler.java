package mir.interview.backend.handler;

import static ratpack.jackson.Jackson.json;

import java.util.List;

import mir.interview.backend.domain.Spend;
import mir.interview.backend.service.AuthService;
import mir.interview.backend.service.DbService;
import ratpack.handling.Context;
import ratpack.handling.Handler;

public class TransactionHandler implements Handler {

    private DbService dbService;

    public TransactionHandler(DbService dbService) {
        this.dbService = dbService;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        String authorisationHeader = ctx.getRequest().getHeaders().get("Authorization");
        String accountUuid = AuthService.getUuid(AuthService.getToken(authorisationHeader));

        List<Spend> transactions = dbService.findTransactions(accountUuid);

        transactions.forEach(System.out::println);

        ctx.getResponse().contentType("application/json");
        ctx.byMethod(method -> method.get(() -> ctx.render(json(transactions))));
    }
}
