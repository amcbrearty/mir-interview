package mir.interview.backend.service;

import java.io.UnsupportedEncodingException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class AuthService {

    private static final String SECRET = "secret";
    private static final String ISSUER = "amcbrearty";
    private static final String UUID_CLAIM = "uuid";

    public static String createToken(String uuid) throws UnsupportedEncodingException {
        return JWT.create()
            .withClaim(UUID_CLAIM, uuid)
            .withIssuer(ISSUER)
            .sign(Algorithm.HMAC256(SECRET));
    }

    public static String getToken(String authorizationHeader) {
         return authorizationHeader.split("Bearer ")[1];
    }

    public static String getUuid(String token) throws UnsupportedEncodingException {
        return JWT.require(Algorithm.HMAC256(SECRET)).withIssuer(ISSUER).build().verify(token).getClaim(UUID_CLAIM).asString();
    }

}
