package materialmail.client.ui;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import materialmail.client.editor.EmailEditorController;
import materialmail.client.model.ClientModel;
import materialmail.core.AlertUtility;
import materialmail.core.Email;
import materialmail.core.ServerRemote;

import java.io.IOException;
import java.rmi.RemoteException;

public class UIController {

    private EmailEditorController emailEditorController;
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

        this.serverRemote = serverRemote;
        this.clientModel = clientModel;

//        clearAllSelections();
        usernameLabel.setText(this.clientModel.getAddress());
        initializeLists();
    }

    private void initializeLists() {
        listsent.setItems(this.clientModel.getSent());
        listsent.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        listinbox.setItems(this.clientModel.getInbox());
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

    /**
     * Quando viene chiusa la finestra del client
     * verrà invocato questo metodo che eseguirà la disconnessione
     * dell'utente.
     */
    public void shutdown() {
        try {
            serverRemote.removeMailbox(clientModel.getAddress());
            serverRemote.addLog(clientModel.getAddress() + " si è disconnesso.");
        } catch (RemoteException e) {
            AlertUtility.error("Impossibile disconnettersi dal server.");
        }
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

    /**
     * Metodo che viene invocato quando
     * l’utente schiaccia il bottone (onAction)
     *
     * @param actionEvent
     */
    public void handleClose(ActionEvent actionEvent) {
        shutdown();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void inviaNuovaMail(ActionEvent event) {
        Stage stage = setCreator();
        emailEditorController.setUpMail();
        stage.show();
    }

    /**
     * crea la view per la creazione di una nuova mail
     *
     * @return lo Stage da visualizzare
     */
    private Stage setCreator() {
        Stage stage = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().
                    getResource("../editor/EmailEditor.fxml"));
            Parent root = loader.load();
            stage = new Stage();
            emailEditorController = loader.getController();
            stage.setTitle("Nuova Email");
            stage.setScene(new Scene(root, 400, 450));
            emailEditorController.setUpCreator(serverRemote, clientModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stage;
    }
}
