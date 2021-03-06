package materialmail.core;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Email implements Serializable {

    private int id;
    private String sender, object, text;
    private ArrayList<String> receivers;
    private String date;

    public Email(int id, String sender, String object, String text, ArrayList<String> receivers, String date) {
        this.id = id;
        this.sender = sender;
        this.object = object;
        this.text = text;
        this.receivers = receivers;
        this.date = date;
    }

    public Email(String sender, String object, String text, ArrayList<String> receivers, String date) {
        this.sender = sender;
        this.object = object;
        this.text = text;
        this.receivers = receivers;
        this.date = date;
    }

    public Email(String sender) {
        this.sender = sender;
        this.receivers = new ArrayList<>();
        this.object = "";
        this.text = "";
        this.date = new SimpleDateFormat("yyyy/MM/dd - HH:mm").format(new Date());
    }

    @Override
    public String toString() {
        return object + " - " + sender;
    }

    public String toFile() {
        String result = getSender() + " % ";
        for (int i = 0; i < getReceivers().size(); i++) {
            if (i < getReceivers().size() - 1)
                result += getReceivers().get(i) + ", ";
            else
                result += getReceivers().get(i);
        }
        result += " % " + getObject();
        result += " % " + getText();
        result += " % " + getDate();
        return result;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.sender);
        hash = 79 * hash + Objects.hashCode(this.receivers);
        hash = 79 * hash + Objects.hashCode(this.object);
        hash = 79 * hash + Objects.hashCode(this.text);
        hash = 79 * hash + Objects.hashCode(this.date);
        return hash;
    }

    @Override
    public boolean equals (Object o) {
        if (o == null) return false;
        if (!(o instanceof Email)) return false;
        return this.hashCode() == o.hashCode();
    }

    Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public ArrayList<String> getReceivers() {
        return receivers;
    }

    public String getReceiverAsString() {
        StringBuilder builder = new StringBuilder();
        for (String value : receivers) {
            builder.append(value).append(", ");
        }
        String text = builder.toString();
        if( text.length() > 0 ) //rimuove l'ultima virgola e lo spazio.
            text = text.substring(0, text.length() - 2);
        return text;
    }

    public void setReceiver(ArrayList<String> receiver) {
        this.receivers = receiver;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
