import client.XOClient;
import org.apache.log4j.BasicConfigurator;

public class StartClient {
    public static void main(String[] args) {
        XOClient client = new XOClient();
        BasicConfigurator.configure();
        client.run();
    }
}
