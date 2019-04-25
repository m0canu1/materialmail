package materialmail.client.editor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EmailEditorController {

    private Stage stage;

    @FXML
    private TextField receiversmail, mailsubject;

    @FXML
    private TextArea bodyfield;

    public void handleSend(ActionEvent actionEvent) {
    }

    public void handleSave(ActionEvent actionEvent) {
    }
}
