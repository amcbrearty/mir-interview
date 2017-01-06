import mir.interview.backend.handler.BalanceHandler;
import mir.interview.backend.handler.LoginHandler;
import mir.interview.backend.handler.SpendHandler;
import mir.interview.backend.handler.TransactionHandler;
import mir.interview.backend.service.DbService;
import ratpack.server.RatpackServer;

public class Main {

    public static void main(String[] args) throws Exception {
        DbService dbService = new DbService();

        RatpackServer.start(spec -> spec
            .handlers(chain -> chain
                .path("login", new LoginHandler(dbService))
                .path("balance", new BalanceHandler(dbService))
                .path("spend", new SpendHandler(dbService))
                .path("transactions", new TransactionHandler(dbService))
                )
            );
    }
}
