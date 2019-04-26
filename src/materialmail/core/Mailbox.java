package materialmail.core;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Mailbox implements Serializable {

    private String address;
    private int counter;
    private ArrayList<Mail> sent;
    private ArrayList<Mail> inbox;
    private Mail newMail;
    private Scanner scanner1;
    private Scanner scanner2;
    private Scanner scanner3;

    public void readMail(String filepath) {
        try {
            scanner1 = new Scanner(new File(filepath));
            while (scanner1.hasNextLine()) {
                ArrayList<String> receivers = new ArrayList<>();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO completare la lettura delle mail
    public Mailbox(String address) {
        this.address = address;
        this.counter = 1;
        this.sent = new ArrayList<>();
        this.inbox = new ArrayList<>();
        //TODO: inserire metodi readMail delle cartelle sent e inbox
        readMail("../../emails" + address + "/sent.txt");
        readMail("../../emails" + address + "/inbox.txt");
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCounter() {
        return counter;
    }

    public void incrCounter() {
        this.counter += 1;
    }

    public ArrayList<Mail> getSent() {
        return sent;
    }

    public void setSent(ArrayList<Mail> sent) {
        this.sent = sent;
    }

    public ArrayList<Mail> getInbox() {
        return inbox;
    }

    public void setInbox(ArrayList<Mail> inbox) {
        this.inbox = inbox;
    }

    public Mail getNewMail() {
        return newMail;
    }

    public void setNewMail(Mail newMail) {
        this.newMail = newMail;
    }
}
