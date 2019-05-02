/*
 * Copyright (C) 2018 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package materialmail.server;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import materialmail.core.Log;
import materialmail.core.ServerRemote;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * FXML Controller class
 */
public class ServerController {

    private ServerRemote serverRemote;

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
            logTable.setItems(serverRemote.getLogs()); //aggiunge gli elementi (già esistenti)al log table
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
            launchRMIRegistry();
            Naming.rebind("rmi://127.0.0.1:2000/server", serverRemote);
        } catch (RemoteException e) {
            System.out.println("Errore di connessione");
            System.exit(-1);
        } catch (MalformedURLException e) {
            System.out.println("Errore nell'URL del server remoto");
            System.exit(-1);
        }
    }

    private void launchRMIRegistry() throws RemoteException {
        try {
            LocateRegistry.createRegistry(2000);
            serverRemote.addLog("E' stato creato il registro RMI");
        } catch (RemoteException e) {
            serverRemote.addLog("Il registro RMI esiste già");
        }
    }
}
