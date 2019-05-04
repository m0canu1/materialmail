package materialmail.client.ui;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import materialmail.client.model.ClientModel;
import materialmail.core.Email;
import materialmail.core.ServerRemote;

import java.rmi.RemoteException;

public class UIController {

    private Stage stage;
    private ClientModel clientModel;
    private ServerRemote serverRemote;

//    private final String currentUser = ClientModel.getModel().getCurrentUser().getUsername();
    private final String currentUser = "alex@matmail.com";

    @FXML
    private Label fromLabel, toLabel, objLabel, usernameLabel;

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

    public void initialize(ServerRemote serverRemote, ClientModel clientModel) {


//        usernameLabel.setText(currentUser); //imposto il label con il nome dell'utente loggato

//        ClientModel.getModel().currentMailProperty().addListener();

        clearAllSelections();
        initializeLists(serverRemote, clientModel);
    }

    private void clearAllSelections() {
        listinbox.getSelectionModel().clearSelection();
        listsent.getSelectionModel().clearSelection();
        listdraft.getSelectionModel().clearSelection();
    }


    private void initializeLists(ServerRemote serverRemote, ClientModel clientModel) {
        this.serverRemote = serverRemote;
        this.clientModel = clientModel;

        listsent.setItems(clientModel.getInbox());
        listsent.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        listinbox.setItems(clientModel.getInbox());
        listinbox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //TODO: creare le bozze

        showEmailDetails(null);
        listsent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showEmailDetails(newValue));
        listinbox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showEmailDetails(newValue));

        Thread syncMail = new Thread(new CheckMail());
        syncMail.setDaemon(true);
        syncMail.start();
    }

    private void showEmailDetails(Email email) {
        if (email != null) {
            fromLabel.setText(email.getSender());
            toLabel.setText(email.getReceiver().toString());
            objLabel.setText(email.getObject());
            //TODO: da verificare questi due
            maildate.setText(email.getDate());
            mailcontent.setText(email.getText());
        }
//        else {
//            fromLabel.setText();
//            toLabel.setText();
//            objLabel.setText();
//            //TODO: da verificare questi due
//            maildate.setText();
//            mailcontent.setText();
//        }
    }

    class CheckMail extends Task {
        Email nuova = null;
        public Void call() {
            while (true) {
                try {
                    Thread.sleep(5000);
                    nuova = serverRemote.notRead(clientModel.getAddress());
                    if (nuova != null) {
                        Platform.runLater(() -> {
                            System.out.println("È arrivata una nuova email.");
//                            AlertUtility.alertInfo("È arrivata una nuova email.");
                        });
                        synchronized (clientModel){
                            clientModel.getInbox().add(nuova);
                        }
                        synchronized (serverRemote) {
                            serverRemote.resetNewMail(clientModel.getAddress());
                        }
                        nuova = null;
                    }
                } catch (RemoteException e) {
                    Platform.runLater(() -> {
                        System.out.println("Impossibile connettersi al server.");
//                        AlertUtility.alertError("Impossibile connettersi al server.");
                    });
                } catch (InterruptedException e) {
                    Platform.runLater(() -> {
                        System.out.println("Malfunzionamento del thread.");
//                        AlertUtility.alertError("Malfunzionamento del thread.");
                        System.exit(-1);
                    });
                }
            }
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
