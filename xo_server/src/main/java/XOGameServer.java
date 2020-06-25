import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.apache.log4j.BasicConfigurator;

public class XOGameServer extends Application {
    private Stage primaryStage;
    private AnchorPane rootLayout;

    @SneakyThrows
    public static void main(String[] args) {
        BasicConfigurator.configure();
        launch(args);
    }


    @Override
    @SneakyThrows
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("XO Game - Server");
        initRootLayout();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(0);
    }

    @SneakyThrows
    public void initRootLayout() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(XOGameServer.class.getResource("MainView.fxml"));
        System.out.println(loader.getLocation());
        rootLayout = (AnchorPane) loader.load();

        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
