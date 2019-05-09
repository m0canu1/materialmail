package materialmail.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Server extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Materialmail - Server");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Server.fxml"));
        stage.setScene(new Scene(loader.load()));
//        stage.setMaximized(true);
        stage.getIcons().add(new Image("materialmail/resources/hammermail.png"));

//        ServerController uiController = loader.getController();
//        ServerModel model = new ServerModel();
//        uiController.initModel(model);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
