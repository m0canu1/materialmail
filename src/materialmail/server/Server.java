package materialmail.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Server extends Application {

    public Server() {
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Materialmail - Server");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Server.fxml"));
        stage.setScene(new Scene(loader.load()));
        stage.getScene().setFill(Color.TRANSPARENT); //rende gli angoli trasparenti

        stage.getIcons().add(new Image("materialmail/resources/mail.png"));

//        ServerController uiController = loader.getController();
//        ServerModel model = new ServerModel();
//        uiController.initModel(model);

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
