package materialmail.client.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import materialmail.core.Mail;
import materialmail.core.User;

import java.sql.Timestamp;

public class ClientModel {
    private static final ClientModel CLIENT_MODEL = new ClientModel(); //this will execute the constructor the first time you call a method

    /**
     * ObservableList implementa una lista di oggetti osservabili. ObservableList
     * invia in automatico le notifiche agli osservatori quando cambia lo stato degli
     * oggetti.
     */
    private final ObservableList<Mail> listInbox = FXCollections.observableArrayList();

    private final ObservableList<Mail> listSent = FXCollections.observableArrayList();

    private final ObservableList<Mail> listDraft = FXCollections.observableArrayList();

    private final ObservableList<Mail> mailsToNotify = FXCollections.observableArrayList();

    private User CurrentUser;

    private Timestamp lastRequestTime;

    public ClientModel() {
        Timestamp ts = new Timestamp(System.currentTimeMillis());
    }

    public static ClientModel getClientModel() {
        return CLIENT_MODEL;
    }

    public Mail getDraftByIndex(int i) {
        return listDraft.get(i);
    }

    public Mail getSentMailByIndex(int i) {
        return listSent.get(i);
    }

    public Mail getReceivedMailByIndex(int i) {
        return listInbox.get(i);
    }

    public ObservableList<Mail> getListInbox() {
        return listInbox;
    }

    public ObservableList<Mail> getListSent() {
        return listSent;
    }

    public ObservableList<Mail> getListDraft() {
        return listDraft;
    }

    public ObservableList<Mail> getMailsToNotify() {
        return mailsToNotify;
    }

    public User getCurrentUser() {
        return CurrentUser;
    }

    public void setCurrentUser(User currentUser) {
        CurrentUser = currentUser;
    }

    public Timestamp getLastRequestTime() {
        return lastRequestTime;
    }

    public void setLastRequestTime(Timestamp lastRequestTime) {
        this.lastRequestTime = lastRequestTime;
    }
}
