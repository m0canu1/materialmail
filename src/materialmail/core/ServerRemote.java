package materialmail.core;

import javafx.collections.ObservableList;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerRemote extends Remote {
    void addLog(String event) throws RemoteException;

    boolean loadMailbox(String address) throws RemoteException;

    void deleteSent(String address, Email email) throws RemoteException;

    void deleteReceived(String address, Email email) throws RemoteException;

    void sendMail(Email email) throws RemoteException;

    boolean checkReceiver(String address) throws RemoteException;

    ArrayList<Email> getSent(String address) throws RemoteException;

    ArrayList<Email> getInbox(String address) throws RemoteException;

    ObservableList<Log> getLogs() throws RemoteException;

    void resetNewMail(String address) throws RemoteException;

    void removeMailbox(String address) throws RemoteException;

    Email notRead(String address) throws RemoteException;

}
