package materialmail.server;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import materialmail.core.Email;
import materialmail.core.Log;
import materialmail.core.Mailbox;
import materialmail.core.ServerRemote;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class ServerModel extends UnicastRemoteObject implements ServerRemote {

    private ArrayList<String> addresses;
    private ArrayList<Mailbox> mailboxes;
    private ObservableList<Log> logs;

    public ServerModel() throws RemoteException {
        this.addresses = new ArrayList<>();
        addresses.add("alex@matmail.com");
        addresses.add("daniele@matmail.com");
        addresses.add("gianni@matmail.com");
        this.mailboxes = new ArrayList<>();
        this.logs = FXCollections.observableArrayList();
    }

    public boolean loadMailBox(String address) {
        if (getMailBoxForAddress(address) == null) {
            this.mailboxes.add(new Mailbox(address));
            addLog("La casella postate di " + address + " è stata caricata.");
            return true;
        } else {
            addLog("Un altro utente ha cercato di connettersi all'account di " + address);
            return false;
        }
    }

    public void addLog(String event) {
        logs.add(new Log(event));
    }

    @Override
    public boolean loadMailbox(String address) throws RemoteException {
        return false;
    }

    public void sendMail(Email email) {
        writeMail("../../emails" + email.getSender() + "/sent.txt", email);
        getMailBoxForAddress(email.getSender()).incrCounter();
        getMailBoxForAddress(email.getSender()).getSent().add(email);
        for (int i = 0; i < email.getReceiver().size(); i++) {
            String receiver = email.getReceiver().get(i);
            deliverMail(email, receiver);
        }
    }

    private void deliverMail(Email email, String receiver) {
        if (getMailBoxForAddress(receiver) != null) {
            getMailBoxForAddress(receiver).setNewEmail(email);
            getMailBoxForAddress(receiver).incrCounter();
            getMailBoxForAddress(receiver).getInbox().add(email);
            addLog(receiver + " ha ricevuto una mail.");

        }
        writeMail("../../emails" + receiver + "/inbox.txt", email);
    }

    @Override
    public ArrayList<Email> getSent(String address) throws RemoteException {
        return getMailBoxForAddress(address).getSent();
    }

    @Override
    public ArrayList<Email> getInbox(String address) throws RemoteException {
        return getMailBoxForAddress(address).getInbox();
    }

    @Override
    public ObservableList<Log> getLogs() throws RemoteException {
        return this.logs;
    }

    /**
     * Scrive la mail nella directory indicata
     * @param path il file in cui scrivere la mail
     * @param email l'oggetto email che verrà scritto nel file indicato
     */
    private void writeMail(String path, Email email) {
        try {
            FileWriter fw = new FileWriter(path, true);
            fw.write(email.toString() + "\n");
            fw.close();
        } catch (FileNotFoundException e) {
            System.out.println("Non ho trovato il file");
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("Errore I/O");
            System.exit(-1);
        }
    }

    public void resetNewMail(String address) {
        if (getMailBoxForAddress(address) != null)
            getMailBoxForAddress(address).setNewEmail(null);
    }

    @Override
    public void removeMailbox(String address) throws RemoteException {
        for (int i = 0; i < mailboxes.size(); i++) {
            if (mailboxes.get(i).getAddress().equals(address))
                mailboxes.remove(i);
        }
    }

    @Override
    public Email notRead(String address) throws RemoteException {
        Mailbox tmp = getMailBoxForAddress(address);
        if (tmp != null) {
            return tmp.getNewEmail();
        } else {
            addLog("Errore imprevisto.");
            System.exit(-1);
        }
        return null;
    }

    public void deleteSent(String address, Email email) {
        Mailbox tmp = getMailBoxForAddress(address);
        if (tmp != null) {
            tmp.deleteSent(email);
            addLog(address + " ha cancellato una mail inviata.");
        } else {
            addLog("Errore imprevisto");
            System.exit(-1);
        }
    }

    @Override
    public void deleteReceived(String address, Email email) throws RemoteException {
        Mailbox tmp = getMailBoxForAddress(address);
        if (tmp != null) {
            tmp.deleteReceived(email);
            addLog(address + " ha cancellato una mail ricevuta");
        } else {
            addLog("Errore imprevisto.");
            System.exit(-1);
        }
    }

    private Mailbox getMailBoxForAddress(String address) {
        for (Mailbox mailbox : mailboxes) { //per ogni elemento Mailbox in mailboxes esegui
            if (mailbox.getAddress().equals(address)) {
                return mailbox;
            }
        }
        return null;
    }

    /**
     * verifica se l'indirizzo passato è registrato
     * @param address l'indirizzo da verificare
     * @return
     */
    public boolean checkReceiver(String address) {
        for (String s : addresses) {
            if (s.equals(address))
                return true;
        }
        return false;
    }
}
