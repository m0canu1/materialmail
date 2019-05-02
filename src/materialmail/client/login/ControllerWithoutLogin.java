package materialmail.client.login;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import materialmail.client.model.Model;
import materialmail.core.ServerRemote;

public class ControllerWithoutLogin {

    private ServerRemote serverRemote;
    private Model clientModel;

    @FXML
    private ObservableList<String> addresses =
            FXCollections.observableArrayList(
                  "alex@matmail.com",
                  "daniele@matmail.com",
                    "gianni@matmail.com"
            );

    private ComboBox choose_email;

    public void initialize() {
        this.clientModel = new Model();
        /*Initializza gli elementi con gli indirizzi che sono
        * un oggetto observable, quindi ad ogni loro cambiamento questa
        * lista verrà aggiornata*/
        choose_email.setItems(addresses);
    }

}
