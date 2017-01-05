package mir.interview.backend.handler;

import static ratpack.jackson.Jackson.json;

import mir.interview.backend.service.AuthService;
import mir.interview.backend.service.DbService;
import ratpack.handling.Context;
import ratpack.handling.Handler;

public class BalanceHandler implements Handler {

    private DbService dbService;

    public BalanceHandler(DbService dbService) {
        this.dbService = dbService;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        String authorisationHeader = ctx.getRequest().getHeaders().get("Authorization");
        String accountUuid = AuthService.getUuid(AuthService.getToken(authorisationHeader));

        ctx.getResponse().contentType("application/json");
        ctx.byMethod(method -> method.get(() -> ctx.render(json(dbService.findBalance(accountUuid)))));
    }
}
