package materialmail;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import materialmail.core.Log;
import materialmail.core.ServerRemote;
import materialmail.server.ServerModel;

import java.rmi.RemoteException;

public class Controller {

    ServerRemote serverRemote;

    @FXML
    private TableView<Log> logTable;
    @FXML
    private TableColumn<Log, String> eventColumn;
    @FXML
    private TableColumn<Log, String> timestampColumn;

    @FXML
    public void initialize() {
        setUpRemote();
        setUpView();
    }

    private void setUpView() {
        try {
            logTable.setItems(serverRemote.getLogs());
            eventColumn.setCellValueFactory(cellData -> cellData.getValue().getMessageProperty());
            timestampColumn.setCellValueFactory(cellData -> cellData.getValue().getTimestampProperty());
        } catch (RemoteException e) {
            System.out.println("Connection error.");
            System.exit(-1);
        }
    }

    private void setUpRemote() {
        try {
            serverRemote = new ServerModel();
            //TODO: completa

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
