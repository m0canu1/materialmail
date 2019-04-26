package materialmail.core;

import javafx.collections.ObservableList;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerRemote extends Remote {
    void addLog(String event) throws RemoteException;

    boolean loadMailbox(String address) throws RemoteException;

    void deleteSent(String address, Mail mail) throws RemoteException;

    void deleteReceived(String address, Mail mail) throws RemoteException;

    void sendMail(Mail mail) throws RemoteException;

    boolean checkReceiver(String address) throws RemoteException;

    ArrayList<Mail> getSent(String address) throws RemoteException;

    ArrayList<Mail> getInbox(String address) throws RemoteException;

    ObservableList<Log> getLogs() throws RemoteException;

    void resetNewMail(String address) throws RemoteException;

    void removeMailbox(String address) throws RemoteException;

    Mail notRead(String address) throws RemoteException;

}
