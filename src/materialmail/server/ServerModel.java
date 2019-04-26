package materialmail.server;

import javafx.collections.ObservableList;
import materialmail.core.Log;
import materialmail.core.Mail;
import materialmail.core.ServerRemote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerModel extends UnicastRemoteObject implements ServerRemote {

    private ArrayList<String> addresses;
    private ArrayList<>
    /**
     * Creates and exports a new UnicastRemoteObject object using an
     * anonymous port.
     *
     * <p>The object is exported with a server socket
     * created using the {@link RMISocketFactory} class.
     *
     * @throws RemoteException if failed to export object
     * @since 1.1
     */
    public ServerModel() throws RemoteException {
    }

    @Override
    public void addLog(String event) throws RemoteException {

    }

    @Override
    public boolean loadMailbox(String address) throws RemoteException {
        return false;
    }

    @Override
    public void deleteSent(String address, Mail mail) throws RemoteException {

    }

    @Override
    public void deleteReceived(String address, Mail mail) throws RemoteException {

    }

    @Override
    public void sendMail(Mail mail) throws RemoteException {

    }

    @Override
    public boolean checkReceiver(String address) throws RemoteException {
        return false;
    }

    @Override
    public ArrayList<Mail> getSent(String address) throws RemoteException {
        return null;
    }

    @Override
    public ArrayList<Mail> getInbox(String address) throws RemoteException {
        return null;
    }

    @Override
    public ObservableList<Log> getLogs() throws RemoteException {
        return null;
    }

    @Override
    public void resetNewMail(String address) throws RemoteException {

    }

    @Override
    public void removeMailbox(String address) throws RemoteException {

    }

    @Override
    public Mail notRead(String address) throws RemoteException {
        return null;
    }
}
