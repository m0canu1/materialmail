package materialmail.core;

import javafx.scene.control.Alert;

public class AlertUtility {
    public static void error(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(msg);
        alert.showAndWait();
    }

    public static void info(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Operazione effettuata con successo");
        alert.setHeaderText(msg);
        alert.showAndWait();
    }
}
