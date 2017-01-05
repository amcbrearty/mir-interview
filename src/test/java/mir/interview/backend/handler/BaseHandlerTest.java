package mir.interview.backend.handler;

import com.aerospike.client.AerospikeClient;

import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
  * Initialise common fixtures for the handler tests.
 */
public class BaseHandlerTest {

    private static final String serverHost = "172.28.128.3";
    private static final int serverPort = 3000;

    protected static AerospikeClient aerospikeClient;

    @BeforeClass
    public static void connectToAerospikeClient() {
        aerospikeClient = new AerospikeClient(serverHost, serverPort);
    }

    @AfterClass
    public static void closeAerospikeClient() {
        aerospikeClient.close();
    }

}
