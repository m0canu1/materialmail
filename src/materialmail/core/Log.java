package materialmail.core;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    private String message;
    private String timestamp;

    public Log(String message) {
        this.message = message;
        this.timestamp = new SimpleDateFormat("yyyy/MM/dd - HH:mm").format(new Date());
    }

    public StringProperty getMessageProperty() {
        StringProperty spMessage = new SimpleStringProperty(this.message);
        return spMessage;
    }

    public StringProperty getTimestampProperty() {
        StringProperty spTimestamp = new SimpleStringProperty(this.timestamp);
        return spTimestamp;
    }

    public void setMessagge(String message) {
        this.message = message;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
