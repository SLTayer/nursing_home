package controller;

import datastorage.PatientDAO;
import datastorage.TreatmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Patient;
import utils.DateConverter;
import datastorage.DAOFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class BackupController {

    @FXML
    private ChoiceBox choicebox;

    private String selectedBackup;
    public void initialize(List<String> backupList) {
        for(String backup: backupList) {
            choicebox.getItems().add(backup);
        }
    }

    public String getSelectedBackup() {
        return selectedBackup;
    }

    @FXML
    public void onSelectedBackupChanged() {
        selectedBackup = choicebox.getValue().toString();
    }
}
