package materialmail.client.editor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import materialmail.client.model.ClientModel;
import materialmail.core.AlertUtility;
import materialmail.core.Email;
import materialmail.core.ServerRemote;

import java.rmi.RemoteException;
import java.util.Scanner;

public class EmailEditorController {

    private Stage stage;

    private ServerRemote serverRemote;
    private ClientModel clientModel;
    private Email templateMail;
    private String sender;



    @FXML
    private TextField receiversmail, mailobject;

    @FXML
    private TextArea bodyfield;

    /**
     * @param serverRemote
     * @param clientModel
     */
    public void setUpCreator(ServerRemote serverRemote, ClientModel clientModel) {
        this.serverRemote = serverRemote;
        this.clientModel = clientModel;
        this.sender = clientModel.getAddress();
        templateMail = new Email(sender);
    }

    /**
     * risponde alla mail passata in riferimento
     * prendendo come destinatari chi ha mandato la email
     *
     * @param email
     */
    public void reply(Email email) {
        templateMail.getReceivers().add(email.getSender());
        setUpMail();
    }

    public void replyEveryone(Email email) {
        templateMail.getReceivers().add(email.getSender());
        for (int i = 0; i < email.getReceivers().size(); i++) {
            if (!email.getReceivers().get(i).equals(sender))
                templateMail.getReceivers().add(email.getReceivers().get(i));
        }
        setUpMail();
    }

    public void forward(Email email) {
        bodyfield.setText(email.getText());
        setUpMail();
    }

    private void setUpMail() {
        //TODO: controllare
        receiversmail.setText(templateMail.getReceiverAsString());
        receiversmail.setText(templateMail.getReceiverAsString());
    }

    @FXML
    public void handleSend(ActionEvent actionEvent) {
        try {
            //TODO: forse inutile
//            if (!mailobject.getText().isEmpty()) {
//                templateMail.setObject(mailobject.getText());
//            }
            templateMail.setObject((mailobject.getText()));
//            if (!bodyfield.getText().isEmpty())
//                templateMail.setText(bodyfield.getText());
            templateMail.setText(bodyfield.getText());

            if (receiversmail.getText().isEmpty()) {
                AlertUtility.error("Devi specificare almeno un destinatario.");
            } else if (receiversmail.getText().equals(sender))
                AlertUtility.error("Mittente e destinatario non possono coincidere.");

            else {
                String receiver = receiversmail.getText();
                Scanner scanner = new Scanner(receiver).useDelimiter(", ");
                boolean flag = true;
                if (templateMail.getReceivers().isEmpty()) {
                    while (scanner.hasNext()) {
                        String destinatario = scanner.next();
                        if (serverRemote.checkReceiver(destinatario)) { //verifica l'esistenza del destinatario
                            if (!destinatario.equals(sender)) {
                                templateMail.getReceivers().add(destinatario);
                            } else {
                                AlertUtility.error("Mittente e destinatario non possono coincidere.");
                                flag = false;
                            }
                        } else {
                            AlertUtility.error("Il destinatario " + destinatario + " non esiste!");
                            flag = false;
                        }
                    }
                }
                if (flag) {
                    serverRemote.sendMail(templateMail);
                    clientModel.getSent().add(templateMail);
                    System.out.println(templateMail.toString());
                    AlertUtility.info("Email inviata con successo!");
                    ((Node) actionEvent.getSource()).getScene().getWindow().hide();
                }
            }
        } catch (RemoteException e) {
            AlertUtility.error("Non sei connesso al server.");
        }
    }
}
