package materialmail.client.editor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import materialmail.client.model.ClientModel;
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
        templateMail.getReceiver().add(email.getSender());
    }

    public void replyEveryone(Email email) {
        templateMail.getReceiver().add(email.getSender());
        for (int i = 0; i < email.getReceiver().size(); i++) {
            if (!email.getReceiver().get(i).equals(sender))
                templateMail.getReceiver().add(email.getReceiver().get(i));
        }
    }

    public void forward(Email email) {
        bodyfield.setText(email.getText());
    }

    public void setUpMail() {
        receiversmail.setText(templateMail.getReceiver().toString());
        receiversmail.setText(templateMail.getReceiver().toString());
    }

    public void handleSend(ActionEvent actionEvent) {
        try {
            if (!mailobject.getText().isEmpty())
                templateMail.setObject(mailobject.getText());
            if (!bodyfield.getText().isEmpty())
                templateMail.setText(bodyfield.getText());
            templateMail.setText(bodyfield.getText());
            if (receiversmail.getText().isEmpty()) {
                //TODO: alertutility
                System.out.println("Devi specificare almeno un destinatario!");
            } else if (receiversmail.getText().equals(sender))
                //TODO: alertutility
                System.out.println("Mittente e destinatario non possono coincidere!");
            else {
                String receiver = receiversmail.getText();
                Scanner scanner = new Scanner(receiver.substring(1, receiver.length() - 1)).useDelimiter(", ");
                Boolean flag = true;
                if (templateMail.getReceiver().isEmpty()) {
                    while (scanner.hasNext()) {
                        String destinatario = scanner.next();
                        if (serverRemote.checkReceiver(destinatario)) { //verifica l'esistenza del destinatario
                            if (!destinatario.equals(sender)) {
                                templateMail.getReceiver().add(destinatario);
                            } else {
                                //TODO: alertutility
                                System.out.println("Mittente e destinatario non possono coincidere!");
                                flag = false;
                            }
                        } else {
                            //TODO: alertutility
                            System.out.println("Il destinatario " + destinatario + " non esiste!");
                            flag = false;
                        }
                    }
                }
                if (flag) {
                    serverRemote.sendMail(templateMail);
                    clientModel.getSent().add(templateMail);
                    System.out.println(templateMail.toString());
                    //TODO: alertutility
                    System.out.println("Email inviata con successo!");
                    ((Node) actionEvent.getSource()).getScene().getWindow().hide();
                }
            }
        } catch (RemoteException e) {
            //TODO: alertutility
            System.out.println("Non sei connesso al server");
        }
    }
}
