package materialmail.client.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import materialmail.client.model.Model;
import materialmail.core.Email;

import java.net.URL;
import java.util.ResourceBundle;

public class UIController implements Initializable {

    private Stage stage;

//    private final String currentUser = Model.getModel().getCurrentUser().getUsername();
    private final String currentUser = "alex@matmail.com";

    @FXML
    private Label fromLabel, toLabel, subjectLabel, usernameLabel;

    @FXML
    private Button newMailButton, sendButton, modifyButton, forwardButton, replyButton, replyAllButton, deleteButton;

    /*A ListView displays a horizontal or vertical list of items
     from which the user may select, or with which the user may interact.*/
    @FXML
    private ListView<Email> listinbox, listsent, listdraft;

    @FXML
    private TextArea maildate, mailcontent;

    @FXML
    private TabPane tabPane;

    @FXML
    private VBox noMailBox, mailBox;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(currentUser); //imposto il label con il nome dell'utente loggato

//        Model.getModel().currentMailProperty().addListener();

        clearAllSelections();
        initializeLists();
    }

    private void clearAllSelections() {
        listinbox.getSelectionModel().clearSelection();
        listsent.getSelectionModel().clearSelection();
        listdraft.getSelectionModel().clearSelection();
    }


    private void initializeLists() {
//        listsent.setItems(Model.getModel().getListSent());
        listsent.setItems(Model.getModel().getListSentByName(currentUser));
//        listsent.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//        listsent.getSelectionModel().selectedIndexProperty().addListener((obsValue, oldValue, newValue) -> {
//            int newindex = (int) newValue;
//
//            if (!listsent.getSelectionModel().isEmpty() && newindex >= 0) {
//                System.out.println("New mail selected from sent list");
////                sentTabShow();
//                Model.getModel().setCurrentMail(Model.getModel().getSentMailByIndex(newindex));
//            }
//        });

        listdraft.setItems(Model.getModel().getListDraft());
        listinbox.setItems(Model.getModel().getListInbox());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
