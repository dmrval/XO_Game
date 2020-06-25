import org.apache.log4j.BasicConfigurator;
import server.XOServer;

public class StartServer {
    public static void main(String[] args) {
        XOServer server = new XOServer();
        BasicConfigurator.configure();
        server.run();
    }


}
