package mir.interview.backend.handler;

import static ratpack.jackson.Jackson.json;

import java.util.UUID;

import mir.interview.backend.domain.Token;
import mir.interview.backend.service.AuthService;
import mir.interview.backend.service.DbService;
import ratpack.handling.Context;
import ratpack.handling.Handler;

public class LoginHandler implements Handler {

    private DbService dbService;

    public LoginHandler(DbService dbService) {
        this.dbService = dbService;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        String uuid =  UUID.randomUUID().toString();
        String token = AuthService.createToken(uuid);

        dbService.createAccount(uuid);

        ctx.byMethod(method -> method.post(() -> ctx.render(json(new Token(token)))));
    }
}
