package materialmail.client.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import materialmail.core.Mail;
import materialmail.core.User;

import java.sql.Timestamp;

public class Model {
    private static final Model CLIENT_MODEL = new Model(); //this will execute the constructor the first time you call a method


    /**
     * If you are displaying a ListView<Person> the data you need, as a minimum,
     * is an ObservableList<Person>. You might also want a property such as currentPerson,
     * that might represent the selected item in the list.
     */
    /**
     * ObservableList implementa una lista di oggetti osservabili. ObservableList
     * invia in automatico le notifiche agli osservatori quando cambia lo stato degli
     * oggetti.
     */
    private final ObservableList<Mail> listInbox = FXCollections.observableArrayList();

    private final ObservableList<Mail> listSent = FXCollections.observableArrayList();

    private final ObservableList<Mail> listDraft = FXCollections.observableArrayList();

    private final ObservableList<Mail> mailsToNotify = FXCollections.observableArrayList();

    /*definisco la property di currentMail*/
    private SimpleObjectProperty<Mail> currentMail;

    /*il nome dell'utente corrente*/
    private User CurrentUser;

    private Timestamp lastRequestTime;

    public Model() {
        currentMail = new SimpleObjectProperty<>();
        Timestamp ts = new Timestamp(System.currentTimeMillis());
    }

    public static Model getModel() {
        return CLIENT_MODEL;
    }


    public SimpleObjectProperty<Mail> currentMailProperty() {
        return currentMail;
    }

    public final Mail getCurrentMail() {
        return currentMail.get();
    }


    public final void setCurrentMail(Mail mail) {
        currentMail.set(mail);
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

    // Observable getters
    public ObservableList<Mail> getListInbox() {
        return listInbox;
    }

    /**
     *
     * @return the list of all sent emails by the current user
     */
    public ObservableList<Mail> getListSent() {
        return listSent;
    }

    /**
     *
     * @param user the user which we want to get sent emails from
     * @return the list of all sent emails
     */
    public ObservableList<Mail> getListSentByName(String user) {

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
