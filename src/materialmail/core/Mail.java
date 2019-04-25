package materialmail.core;

import java.io.Serializable;
import java.security.Timestamp;
import java.util.Objects;

public class Mail implements Serializable {
    private Integer id;
    private String sender, receiver, title, text;
    private Timestamp date;

    public Mail(Integer id, String sender, String receiver, String title, String text, Timestamp date) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.title = title;
        this.text = text;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "id=" + id +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.sender);
        hash = 79 * hash + Objects.hashCode(this.receiver);
        hash = 79 * hash + Objects.hashCode(this.title);
        hash = 79 * hash + Objects.hashCode(this.text);
        hash = 79 * hash + Objects.hashCode(this.date);
        return hash;
    }

    @Override
    public boolean equals (Object o) {
        if (o == null) return false;
        if (!(o instanceof Mail)) return false;
        return this.hashCode() == o.hashCode();
    }

    public Integer getId() {
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

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
