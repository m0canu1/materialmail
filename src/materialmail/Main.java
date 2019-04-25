package materialmail;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.initStyle(StageStyle.TRANSPARENT); //elimina la barra del titolo
        Parent root = FXMLLoader.load(getClass().getResource("/materialmail/UILogin.fxml"));

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT); //rende gli angoli trasparenti
        primaryStage.setTitle("MaterialMail - Login"); //titolo della finestra
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("materialmail/resources/hammermail.png"));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
