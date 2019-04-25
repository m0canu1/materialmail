package materialmail.client.login;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import materialmail.client.ui.UIController;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginController implements Initializable {

    private Stage stage;

    /**
     * Il controller inizializza automaticamente le variabili globali
     * dichiarate  esse possono essere usate nel metodo di handling degli
     * eventi
     */
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Label loginfailure;

    public void handleLogin(ActionEvent actionEvent) {
        Toolkit.getDefaultToolkit().beep();
    }



    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void handleSignup(ActionEvent actionEvent) {
    }

    /**
     * Metodo che viene invocato quando
     * l’utente schiaccia il bottone (onAction)
     * @param actionEvent
     */
    public void handleClose(ActionEvent actionEvent) {
        Platform.exit();
    }

    private void spawnHome() {
        try {
            FXMLLoader uiLoader = new FXMLLoader();
            uiLoader.setLocation(getClass().getResource("Server.fxml"));
            Parent root;
            root = uiLoader.load();
            UIController uiController = uiLoader.getController();
            stage.close();
            Stage newStage = new Stage();
            newStage.setTitle("Materialmail - Home");
            newStage.getIcons().add(new Image("materialmail/resources/hammermail.png"));
            newStage.setScene(new Scene(root));
            uiController.setStage(newStage);
            newStage.show();

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            Platform.exit();
        }
    }

    private void showError(String message) {
        loginfailure.setText(message);
        loginfailure.setVisible(true);
    }

    /**
     * Metodo che viene invocato quando si inizializza il controller
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginfailure.setVisible(false);
        /*TODO: capire il funzionamento dell'addListener()*/
        /*Overrides method on InvalidationListener*/
        username.textProperty().addListener(e -> loginfailure.setVisible(false));
        password.textProperty().addListener(e -> loginfailure.setVisible(false));
    }
}
