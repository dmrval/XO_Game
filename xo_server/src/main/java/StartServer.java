import org.apache.log4j.BasicConfigurator;
import server.XOServer;

public class StartServer {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        XOServer server = new XOServer();
        server.run();
    }
}
