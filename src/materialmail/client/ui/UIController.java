package materialmail.client.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import materialmail.client.model.ClientModel;
import materialmail.core.Mail;

import java.net.URL;
import java.util.ResourceBundle;

public class UIController implements Initializable {

    private Stage stage;

//    private final String currentUser = ClientModel.getClientModel().getCurrentUser().getUsername();

    @FXML
    private Label fromLabel, toLabel, subjectLabel, usernameLabel;

    @FXML
    private Button newMailButton, sendButton, modifyButton, forwardButton, replyButton, replyAllButton, deleteButton;

    @FXML
    private ListView<Mail> listinbox, listsent, listdraft;

    @FXML
    private TextArea maildate, mailcontent;

    @FXML
    private TabPane tabPane;

    @FXML
    private VBox noMailBox, mailBox;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clearAllSelections();
        initializeLists();
    }

    private void clearAllSelections() {
        listinbox.getSelectionModel().clearSelection();
        listsent.getSelectionModel().clearSelection();
        listdraft.getSelectionModel().clearSelection();
    }


    private void initializeLists() {
        listsent.setItems(ClientModel.getClientModel().getListSent());
        listdraft.setItems(ClientModel.getClientModel().getListDraft());
        listinbox.setItems(ClientModel.getClientModel().getListInbox());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
