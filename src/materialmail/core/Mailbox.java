package materialmail.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Mailbox implements Serializable {

    private String address;
    private int counter;
    private ArrayList<Email> sent;
    private ArrayList<Email> inbox;
    private Email newEmail;
    private Scanner scanner1;
    private Scanner scanner2;
    private Scanner scanner3;


    public Mailbox(String address) {
        this.address = address;
        this.counter = 1;
        this.sent = new ArrayList<>();
        this.inbox = new ArrayList<>();
        readMail("../../emails" + address + "/sent.txt", this.sent);
        readMail("../../emails" + address + "/inbox.txt", this.inbox);
    }

    private void readMail(String filepath, ArrayList<Email> email) {
        try {
            scanner1 = new Scanner(new File(filepath));
            while (scanner1.hasNextLine()) {
                ArrayList<String> receivers = new ArrayList<>();
                this.scanner2 = new Scanner(scanner1.nextLine()).useDelimiter("\\s*%\\s*");
                if (this.scanner2.hasNext()) {
                    String sender = scanner2.next();
                    if (scanner2.hasNext()) {
                        scanner3 = new Scanner(scanner2.next()).useDelimiter("\\s*,\\s*");
                        while (scanner3.hasNext()) {
                            receivers.add(scanner3.next()); //aggiungo il mittente appena letto ai mittenti già presenti
                        }
                        System.out.println(receivers);
                        if (scanner2.hasNext()) {
                            String object = scanner2.next();
                            if (scanner2.hasNext()) {
                                String text = scanner2.next();
                                if (scanner2.hasNext()) {
                                    String timestamp = scanner2.next();
                                    email.add(new Email(getCounter(), sender, object, text, receivers, timestamp));
                                    incrCounter();
                                }
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        String result = "Address: " + getAddress() + "\n";
        result += "Messaggi inviati: \n";
        for (int i = 0; i < getSent().size(); i++) {
            result += getSent().get(i).toString() + "\n";
        }
        result += "Messaggi ricevuti: \n";
        for (int i = 0; i < getInbox().size(); i++) {
            result += getInbox().get(i).toString() + "\n";
        }
        return result;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private int getCounter() {
        return counter;
    }

    public void incrCounter() {
        this.counter += 1;
    }

    public ArrayList<Email> getSent() {
        return sent;
    }

    public void setSent(ArrayList<Email> sent) {
        this.sent = sent;
    }

    public ArrayList<Email> getInbox() {
        return inbox;
    }

    public void setInbox(ArrayList<Email> inbox) {
        this.inbox = inbox;
    }

    public Email getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(Email newEmail) {
        this.newEmail = newEmail;
    }

    /**
     * cancella una mail dalla caselle inviata
     * @param email la mail che andrà cancellata
     */
    public void deleteSent(Email email) {
        for (int i = 0; i < sent.size(); i++) {
            if (sent.get(i).getId() == email.getId())
                sent.remove(i);
        }
        writeMail("../../emails" + address + "/sent.txt", sent, false);
    }

    public void deleteReceived(Email email) {
        for (int i = 0; i < inbox.size(); i++) {
            if (inbox.get(i).getId() == email.getId())
                inbox.remove(i);
        }
        writeMail("../../emails" + address + "/sent.txt", inbox, false);
    }

    private void writeMail(String path, ArrayList<Email> email, boolean append) {
        try {
            FileWriter fw = new FileWriter(path, append);
            for (int i = 0; i < email.size(); i++) {
                fw.write(email.get(i).toString() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
