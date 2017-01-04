package mir.interview.backend.handler;

import ratpack.handling.Context;
import ratpack.handling.Handler;

public class LoginHandler implements Handler {

    @Override
    public void handle(Context ctx) throws Exception {
        ctx.byMethod(method -> method.post(() -> ctx.render("login")));
    }
}
