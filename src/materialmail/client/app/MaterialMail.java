package materialmail.client.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MaterialMail extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.initStyle(StageStyle.TRANSPARENT); //elimina la barra del titolo
//        Parent root = FXMLLoader.load(getClass().getResource("../login/login.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("../login/LoginView.fxml"));

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT); //rende gli angoli trasparenti
        primaryStage.setTitle("MaterialMail - Client"); //titolo della finestra
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("materialmail/resources/hammermail.png"));
        primaryStage.show();

//        ServerController uiController = loader.getController();
//        ServerModel model = new ServerModel();
//        uiController.initModel(model);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
