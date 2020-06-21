import client.XOClient;
import org.apache.log4j.BasicConfigurator;

public class StartClient {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        XOClient client = new XOClient();
        client.run();
    }
}
