package materialmail.client.login;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import materialmail.client.model.ClientModel;
import materialmail.core.ServerRemote;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ControllerWithoutLogin {

    private ServerRemote serverRemote;
    private ClientModel clientModel;

    @FXML
    private ObservableList<String> addresses =
            FXCollections.observableArrayList(
                  "alex@matmail.com",
                  "daniele@matmail.com",
                    "gianni@matmail.com"
            );
    @FXML
    private ComboBox choose_email;

    @FXML
    public void initialize() {
        this.clientModel = new ClientModel();
        /*Initializza gli elementi con gli indirizzi che sono
        * un oggetto observable, quindi ad ogni loro cambiamento questa
        * lista verrà aggiornata*/
        choose_email.setItems(addresses);
    }

    @FXML
    public void handleLogin(ActionEvent event) {
        try {
            //Casting perché restituisce un java.rmi.Remote
            serverRemote = (ServerRemote) Naming.lookup(
                    "rmi://127.0.0.1:2000/mocanu");
            if (choose_email.getSelectionModel().isEmpty()) { //metodi dell'oggetto ComboBox
                //todo alertutility
                System.out.println("Selezione il tuo indirizzo email");
            } else {
                String address = (String) choose_email.getSelectionModel().getSelectedItem();
                clientModel.setServerRemote(serverRemote); //imposta il server remoto per il model del client
                clientModel.setAddress(address);
                if (clientModel.setMailBox()) { //verifica se è riuscito a settare la mailbox
                    serverRemote.addLog(address + "si è connesso");
                    showMailboxView(event);
                } else {
                    //todo alertUtility
                    System.out.println("impossibile collegarsi a questo account");
                }
            }
        } catch (RemoteException | NotBoundException | MalformedURLException e) {
            //todo creare classe AlertUtility
//            AlertUtility.alertError("Non sono riuscito a connettermi al server");
            System.out.println("Non sono riuscito a connettermi al server");
        }
    }

    private void showMailboxView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../UI.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Mailbox");
            stage.setScene(new Scene(root));
            ((Node) (event.getSource())).getScene().getWindow().hide(); //nasconde la finestra di login

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
