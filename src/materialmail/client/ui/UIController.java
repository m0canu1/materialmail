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
    private ClientModel clientModel;
    private ServerRemote serverRemote;

    @FXML
    private Label fromLabel, toLabel, dateLabel, objLabel, usernameLabel;

    @FXML
    private Button replyButton;

    /*A ListView displays a horizontal or vertical list of items
     from which the user may select, or with which the user may interact.*/
    @FXML
    private ListView<Email> listInbox, listSent;

    @FXML
    private TextArea maildate, mailContent;

    @FXML
    private TabPane tabPane;
    @FXML
    private VBox noMailBox, mailBox;


    public void initialize(ServerRemote serverRemote, ClientModel clientModel) {
        this.serverRemote = serverRemote;
        this.clientModel = clientModel;
        usernameLabel.setText(this.clientModel.getAddress());
        initializeLists();
    }

    /**
     *
     */
    private void initializeLists() {
        listSent.setItems(this.clientModel.getSent());
        listSent.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        listInbox.setItems(this.clientModel.getInbox());
        listInbox.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        showEmailDetails(null);

        listSent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showEmailDetails(newValue));
        listInbox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showEmailDetails(newValue));

        //Setting up tab change
        tabPane.getSelectionModel().selectedIndexProperty().addListener((obsValue, oldValue, newValue) -> { //If tab changes clear all selections and text
            clearAllSelections();
//            System.out.println("Tab number " + newValue + " selected, list selections cleared");
        });

        Thread syncMail = new Thread(new CheckMail());
        syncMail.setDaemon(true);
        syncMail.start();
    }

    private void clearAllSelections() {
        listInbox.getSelectionModel().clearSelection();
        listSent.getSelectionModel().clearSelection();
    }

    private void showEmailDetails(Email email) {
        if (email != null) {
            fromLabel.setText(email.getSender());
            toLabel.setText(email.getReceiverAsString());
            objLabel.setText(email.getObject());
            //TODO: da verificare questi due
            dateLabel.setText(email.getDate());
            mailContent.setText(email.getText());
            setVisible();
        }
        else setInvisible();
    }

    private void setInvisible() {
        fromLabel.setVisible(false);
        toLabel.setVisible(false);
        objLabel.setVisible(false);
        dateLabel.setVisible(false);
        mailContent.setVisible(false);
    }

    private void setVisible() {
        fromLabel.setVisible(true);
        toLabel.setVisible(true);
        objLabel.setVisible(true);
        dateLabel.setVisible(true);
        mailContent.setVisible(true);
    }


    /**
     * Metodo che viene invocato quando
     * l’utente schiaccia il bottone (onAction)
     */
    @FXML
    public void handleClose() {
        shutdown();
        Platform.exit();
    }
    @FXML
    private void sendNewMail() {
        Stage stage = setCreator();
        stage.show();
    }

    @FXML
    private void handleReply(ActionEvent event) {
        Email email = null;
        if (!listInbox.getSelectionModel().isEmpty()) {
            email = listInbox.getSelectionModel().getSelectedItem();
            Stage stage = setCreator();
            if (event.getSource() == replyButton) emailEditorController.reply(email);
            else emailEditorController.replyEveryone(email);
            if (stage != null) stage.show();
        }
        if (email == null)
            AlertUtility.error("Seleziona prima una mail a cui rispondere");
    }

    @FXML
    private void handleForward() {
        Email email = null;
        if (!listSent.getSelectionModel().isEmpty()) {
            email = listSent.getSelectionModel().getSelectedItem();
            forward(email);
        } else if (!listInbox.getSelectionModel().isEmpty()) {
            email = listInbox.getSelectionModel().getSelectedItem();
            forward(email);
        }
        if (email == null)
            AlertUtility.error("Seleziona prima una mail da inoltrare");
    }

    /**
     * support method for handleForward to avoid redundant code
     *
     * @param email
     */
    private void forward(Email email) {
        Stage stage = setCreator();
        emailEditorController.forward(email);
        if (stage != null) stage.show();
    }


    @FXML
    private void handleDelete() {
        try {
            if (!listSent.getSelectionModel().isEmpty()) {
                Email selected = listSent.getSelectionModel().getSelectedItem();
                serverRemote.deleteSent(clientModel.getAddress(), selected);
                listSent.getItems().remove(selected);
            } else if (!listInbox.getSelectionModel().isEmpty()) {
                Email selected = listInbox.getSelectionModel().getSelectedItem();
                listInbox.getItems().remove(selected);
                serverRemote.deleteReceived(clientModel.getAddress(), selected);
            } else
                AlertUtility.error("Non hai selezionato alcuna mail.");

        } catch (RemoteException e) {
                AlertUtility.error("Non sei connesso al server.");
        }
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

    private Stage setCreator() {
        Stage stage = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../editor/EmailEditor.fxml"));
            Parent root = loader.load();
            stage = new Stage();
            emailEditorController = loader.getController();
            stage.setTitle("New Mail");
            stage.setScene(new Scene(root));
            emailEditorController.setUpEmailEditor(serverRemote, clientModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stage;
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
                        synchronized (clientModel) {
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

}
