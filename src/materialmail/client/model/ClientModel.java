package materialmail.client.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import materialmail.core.Email;
import materialmail.core.ServerRemote;
import materialmail.core.User;

import java.rmi.RemoteException;

public class ClientModel {
    private String address;
    //TODO: sostituire address con la classe User
    private User currentUser;
    private ServerRemote serverRemote; //ServerRemote interface
    private ObservableList<Email> sent;
    private ObservableList<Email> inbox;

    public ClientModel() {
        sent = FXCollections.observableArrayList();
        inbox = FXCollections.observableArrayList();
    }

    public boolean setMailbox() throws RemoteException {
        if (serverRemote.loadMailbox(getAddress())) {
            sent.addAll(serverRemote.getSent(getAddress()));
            inbox.addAll(serverRemote.getInbox(getAddress()));
            return true;
        } else return true;
    }

    public void setServerRemote(ServerRemote serverRemote) {
        this.serverRemote = serverRemote;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public String getAddress() {
       return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ServerRemote getServerRemote() {
        return serverRemote;
    }

    public ObservableList<Email> getSent() {
        return sent;
    }

    public ObservableList<Email> getInbox() {
        return inbox;
    }
}

//public class ClientModel {
//    private static final ClientModel CLIENT_MODEL = new ClientModel(); //this will execute the constructor the first time you call a method
//
//
//    /**
//     * If you are displaying a ListView<Person> the data you need, as a minimum,
//     * is an ObservableList<Person>. You might also want a property such as currentPerson,
//     * that might represent the selected item in the list.
//     */
//    /**
//     * ObservableList implementa una lista di oggetti osservabili. ObservableList
//     * invia in automatico le notifiche agli osservatori quando cambia lo stato degli
//     * oggetti.
//     */
//    private final ObservableList<Email> listInbox = FXCollections.observableArrayList();
//
//    private final ObservableList<Email> listSent = FXCollections.observableArrayList();
//
//    private final ObservableList<Email> listDraft = FXCollections.observableArrayList();
//
//    private final ObservableList<Email> mailsToNotify = FXCollections.observableArrayList();
//
//    /*definisco la property di currentMail*/
//    private SimpleObjectProperty<Email> currentMail;
//
//    /*il nome dell'utente corrente*/
//    private User CurrentUser;
//
//    private Timestamp lastRequestTime;
//
//    public ClientModel() {
//        currentMail = new SimpleObjectProperty<>();
//        Timestamp ts = new Timestamp(System.currentTimeMillis());
//    }
//
//    public static ClientModel getModel() {
//        return CLIENT_MODEL;
//    }
//
//
//    public SimpleObjectProperty<Email> currentMailProperty() {
//        return currentMail;
//    }
//
//    public final Email getCurrentMail() {
//        return currentMail.get();
//    }
//
//
//    public final void setCurrentMail(Email email) {
//        currentMail.set(email);
//    }
//
//
//    public Email getDraftByIndex(int i) {
//        return listDraft.get(i);
//    }
//
//    public Email getSentMailByIndex(int i) {
//        return listSent.get(i);
//    }
//
//    public Email getReceivedMailByIndex(int i) {
//        return listInbox.get(i);
//    }
//
//    // Observable getters
//    public ObservableList<Email> getListInbox() {
//        return listInbox;
//    }
//
//    /**
//     *
//     * @return the list of all sent emails by the current user
//     */
//    public ObservableList<Email> getListSent() {
//        return listSent;
//    }
//
//    /**
//     *
//     * @param user the user which we want to get sent emails from
//     * @return the list of all sent emails
//     */
//    public ObservableList<Email> getListSentByName(String user) {
//
//        return listSent;
//    }
//
//    public ObservableList<Email> getListDraft() {
//        return listDraft;
//    }
//
//    public ObservableList<Email> getMailsToNotify() {
//        return mailsToNotify;
//    }
//
//    public User getCurrentUser() {
//        return CurrentUser;
//    }
//
//    public void setCurrentUser(User currentUser) {
//        CurrentUser = currentUser;
//    }
//
//    public Timestamp getLastRequestTime() {
//        return lastRequestTime;
//    }
//
//    public void setLastRequestTime(Timestamp lastRequestTime) {
//        this.lastRequestTime = lastRequestTime;
//    }
//}