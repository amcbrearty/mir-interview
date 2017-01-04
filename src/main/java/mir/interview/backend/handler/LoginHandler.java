package mir.interview.backend.handler;

import static ratpack.jackson.Jackson.json;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import mir.interview.backend.domain.Token;
import ratpack.handling.Context;
import ratpack.handling.Handler;

public class LoginHandler implements Handler {

    @Override
    public void handle(Context ctx) throws Exception {

        String token = JWT.create()
            .withIssuer("amcbrearty")
            .sign(Algorithm.HMAC256("secret"));

        ctx.byMethod(method -> method.post(() -> ctx.render(json(new Token(token)))));
    }
}
