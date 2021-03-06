package mir.interview.backend.handler;


import static ratpack.jackson.Jackson.fromJson;

import mir.interview.backend.domain.Spend;
import mir.interview.backend.service.AuthService;
import mir.interview.backend.service.DbService;
import ratpack.handling.Context;
import ratpack.handling.Handler;

public class SpendHandler implements Handler {

    private DbService dbService;

    public SpendHandler(DbService dbService) {
        this.dbService = dbService;
    }

    @Override
    public void handle(Context ctx) throws Exception {

        String authorisationHeader = ctx.getRequest().getHeaders().get("Authorization");
        String accountUuid = AuthService.getUuid(AuthService.getToken(authorisationHeader));

        ctx.parse(fromJson(Spend.class)).then((Spend spend) -> {

            dbService.updateBalance(accountUuid, spend.getAmount());
            dbService.addSpend(accountUuid, spend);

            ctx.byMethod(method -> method.post(() -> ctx.render("")));
        });
    }
}
