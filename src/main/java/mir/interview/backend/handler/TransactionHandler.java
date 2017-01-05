package mir.interview.backend.handler;

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

        dbService.findTransactions(accountUuid);

    }
}
