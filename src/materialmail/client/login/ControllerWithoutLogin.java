package materialmail.client.login;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import materialmail.client.model.ClientModel;
import materialmail.client.ui.UIController;
import materialmail.core.ServerRemote;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class ControllerWithoutLogin implements Initializable {

    @FXML
    private final ObservableList<String> addresses =
            FXCollections.observableArrayList(
                  "alex@matmail.com",
                  "daniele@matmail.com",
                    "gianni@matmail.com"
            );
    private ServerRemote serverRemote;
    private ClientModel clientModel;
    @FXML
    private Label loginfailure;
    @FXML
    private ComboBox<String> choose_email;

    @FXML
    public void handleLogin(ActionEvent event) {
        try {
            //Casting perché restituisce un java.rmi.Remote
            this.serverRemote = (ServerRemote) Naming.lookup(
                    "rmi://127.0.0.1:2000/server");
            if (choose_email.getSelectionModel().isEmpty()) { //metodi dell'oggetto ComboBox
                showError("No account selected");
            } else {
                String address = choose_email.getSelectionModel().getSelectedItem();
                clientModel.setServerRemote(serverRemote); //imposta il server remoto per il model del client
                clientModel.setAddress(address);
                if (clientModel.setMailbox()) { //verifica se è riuscito a settare la mailbox
                    serverRemote.addLog(address + " si è connesso");
                    showMailboxView(event);
                } else {
                    showError("Cannot connect to this account");
                }
            }
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            showError("Server connection error");
        }
    }

    public void handleClose() {
        Platform.exit();
    }

    private void showError(String message) {
        loginfailure.setText(message);
        loginfailure.setVisible(true);
    }

    private void showMailboxView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../ui/UI.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Mailbox");
            stage.setScene(new Scene(root));
            ((Node) (event.getSource())).getScene().getWindow().hide(); //nasconde la finestra di login
            /* imposto il parametro uiController con il controller preso da UI.fxml*/
            UIController uiController = loader.getController();
            uiController.initialize(serverRemote, clientModel);
            stage.setOnCloseRequest(e -> uiController.shutdown());
            stage.setTitle(clientModel.getAddress());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginfailure.setVisible(false);
        this.clientModel = new ClientModel();
        /* Inizializza gli elementi con gli indirizzi che sono
         * un oggetto observable, quindi ad ogni loro cambiamento questa
         * lista verrà aggiornata*/
        choose_email.setItems(addresses);
    }
}
