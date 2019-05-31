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


    public Mailbox(String address) {
        this.address = address;
        this.counter = 1;
        this.sent = new ArrayList<>();
        this.inbox = new ArrayList<>();

        readMail("./emails/" + address + "/sent.txt", this.sent);
        readMail("./emails/" + address + "/inbox.txt", this.inbox);
    }

    private void readMail(String filepath, ArrayList<Email> email) {
        try {
            System.out.println(filepath);
            Scanner scanner1 = new Scanner(new File(filepath));
            while (scanner1.hasNextLine()) {
                ArrayList<String> receivers = new ArrayList<>();
                Scanner scanner2 = new Scanner(scanner1.nextLine()).useDelimiter("\\s*%\\s*");
                if (scanner2.hasNext()) {
                    String sender = scanner2.next();
                    if (scanner2.hasNext()) {
                        Scanner scanner3 = new Scanner(scanner2.next()).useDelimiter("\\s*,\\s*");
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
        StringBuilder result = new StringBuilder("Address: " + getAddress() + "\n");
        result.append("Messaggi inviati: \n");
        for (int i = 0; i < getSent().size(); i++) {
            result.append(getSent().get(i).toString()).append("\n");
        }
        result.append("Messaggi ricevuti: \n");
        for (int i = 0; i < getInbox().size(); i++) {
            result.append(getInbox().get(i).toString()).append("\n");
        }
        return result.toString();
    }

    public String getAddress() {
        return address;
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

    public ArrayList<Email> getInbox() {
        return inbox;
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
    @SuppressWarnings("SuspiciousListRemoveInLoop")
    public void deleteSent(Email email) {
        for (int i = 0; i < sent.size(); i++) {
            if (sent.get(i).getId().equals(email.getId()))
                sent.remove(i);
        }
        writeMail("./emails/" + address + "/sent.txt", sent);
    }

    @SuppressWarnings("SuspiciousListRemoveInLoop")
    public void deleteReceived(Email email) {
        for (int i = 0; i < inbox.size(); i++) {
            if (inbox.get(i).getId().equals(email.getId()))
                inbox.remove(i);
        }
        writeMail("./emails/" + address + "/inbox.txt", inbox);
    }

    private void writeMail(String path, ArrayList<Email> emailList) {
        try {
            FileWriter fw = new FileWriter(path, false);
            for (Email e : emailList) {
                fw.write(e.toString() + "\n");
            }
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
