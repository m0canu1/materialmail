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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;


/**
 * La classe del server (ServerModel) che realizza l'oggetto remoto
 * deve avere le seguenti caratteristiche:
 * Deve implementare l'interfaccia (ServerRemote).
 * Deve estendere la classe java.rmi.server.UnicastRemoteObject.
 * Questo serve per creare le infrastrutture per comunicare attraverso la
 * rete.
 * Il costruttore deve lanciare l'eccezione java.rmi.RemoteException.
 */

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

    @Override
    public boolean loadMailbox(String address) {
        if (getMailBoxForAddress(address) == null) {
            this.mailboxes.add(new Mailbox(address));
            addLog("La casella postale di " + address + " è stata caricata");
            return true;
        } else {
            addLog("Un altro utente ha cercato di connettersi all'account di " + address);
            return false;
        }
    }

    public void addLog(String event) {
        logs.add(new Log(event));
    }

    public void sendMail(Email email) {
        writeMail("./emails/" + email.getSender() + "/sent.txt", email);
        getMailBoxForAddress(email.getSender()).incrCounter();
        getMailBoxForAddress(email.getSender()).getSent().add(email);
        for (int i = 0; i < email.getReceivers().size(); i++) {
            String receiver = email.getReceivers().get(i);
            deliverMail(email, receiver);
        }
        addLog(email.getSender() + " ha inviato una mail.");
    }

    private void deliverMail(Email email, String receiver) {
        if (getMailBoxForAddress(receiver) != null) {
            getMailBoxForAddress(receiver).setNewEmail(email);
            getMailBoxForAddress(receiver).incrCounter();
            getMailBoxForAddress(receiver).getInbox().add(email);
            addLog(receiver + " ha ricevuto una mail.");
        }
        writeMail("./emails/" + receiver + "/inbox.txt", email);
    }

    /**
     * Scrive la mail nella directory indicata
     * @param path il file in cui scrivere la mail
     * @param email l'oggetto email che verrà scritto nel file indicato
     */
    private void writeMail(String path, Email email) {
        try {
            if (!Files.exists(Paths.get(path))) {
                Files.createFile(Paths.get(path));
                System.out.println("Directory created.");
            } else {
                FileWriter fw = new FileWriter(path, true);
                fw.write(email.toFile() + "\n");
                fw.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Non ho trovato il file");
            System.out.println(path);
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
    public void removeMailbox(String address) {


        mailboxes.removeIf(m -> (m.getAddress().equals(address)));


        for (int i = 0; i < mailboxes.size(); i++) {
            if (mailboxes.get(i).getAddress().equals(address))
                mailboxes.remove(i);
        }
    }

    @Override
    public Email notRead(String address) {
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
    public void deleteReceived(String address, Email email) {
        Mailbox tmp = getMailBoxForAddress(address);
        if (tmp != null) {
            tmp.deleteReceived(email);
            addLog(address + " ha cancellato una mail ricevuta.");
        } else {
            addLog("Errore imprevisto.");
            System.exit(-1);
        }
    }

    private Mailbox getMailBoxForAddress(String address) {
        for (int i = 0; i < mailboxes.size(); i++) {
            if (mailboxes.get(i).getAddress().equals(address))
                return mailboxes.get(i);
        }
//        for (Mailbox mailbox : mailboxes) {
//            if (mailbox.getAddress().equals(address))
//                return mailbox;
//        }
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

    @Override
    public ArrayList<Email> getSent(String address) {
        return getMailBoxForAddress(address).getSent();
    }

    @Override
    public ArrayList<Email> getInbox(String address) {
        return getMailBoxForAddress(address).getInbox();
    }

    /**
     *
     * @return un oggetto Observable che segnala il proprio cambiamento ad ogni modifica
     * e, la classe che lo ha chiesto, aggiornerà i dati ad ogni modifica subita dall'oggetto
     */
    @Override
    public ObservableList<Log> getLogs() {
        return this.logs;
    }
}
