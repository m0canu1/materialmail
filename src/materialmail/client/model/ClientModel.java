package materialmail.client.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import materialmail.core.Email;
import materialmail.core.ServerRemote;

import java.rmi.RemoteException;

public class ClientModel {
    private String address;
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
