package mir.interview.backend.handler;

import com.aerospike.client.AerospikeClient;

import mir.interview.backend.service.DbService;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
  * Initialise common fixtures for the handler tests.
 */
public class BaseHandlerTest {

    private static final String serverHost = "172.28.128.3";
    private static final int serverPort = 3000;

    protected static AerospikeClient aerospikeClient;
    protected static DbService dbService;

    @BeforeClass
    public static void connectToDb() {
        aerospikeClient = new AerospikeClient(serverHost, serverPort);
        dbService = new DbService();
    }

    @AfterClass
    public static void closeDb() {
        aerospikeClient.close();
        dbService.closeDb();
    }
}
