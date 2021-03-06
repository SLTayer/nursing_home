package controller;

import datastorage.CaregiverDAO;
import datastorage.DAOFactory;
import datastorage.PatientDAO;
import datastorage.TreatmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.Caregiver;

import java.sql.SQLException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * The <code>AllCaregiverController</code> contains the entire logic of the caregiver view. It determines which data is displayed and how to react to events.
 */

public class AllCaregiverController {

    @FXML
    private TableView<Caregiver> tableView;
    @FXML
    private TableColumn<Caregiver, Integer> colID;
    @FXML
    private TableColumn<Caregiver, String> colFirstName;
    @FXML
    private TableColumn<Caregiver, String> colSurname;
    @FXML
    private TableColumn<Caregiver, String> colPhoneNumber;

    @FXML
    Button btnDelete;
    @FXML
    Button btnAdd;
    @FXML
    TextField txtSurname;
    @FXML
    TextField txtFirstname;
    @FXML
    TextField txtPhoneNumber;

    private ObservableList<Caregiver> tableviewContent = FXCollections.observableArrayList();
    private CaregiverDAO dao;


    /**
     * Initializes the corresponding fields. Is called as soon as the corresponding FXML file is to be displayed.

     */
    public void initialize() {
        readAllAndShowInTableView();

        this.colID.setCellValueFactory(new PropertyValueFactory<Caregiver, Integer>("cid"));

        //CellValuefactory zum Anzeigen der Daten in der TableView
        this.colFirstName.setCellValueFactory(new PropertyValueFactory<Caregiver, String>("firstName"));
        //CellFactory zum Schreiben innerhalb der Tabelle
        this.colFirstName.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colSurname.setCellValueFactory(new PropertyValueFactory<Caregiver, String>("surname"));
        this.colSurname.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colPhoneNumber.setCellValueFactory(new PropertyValueFactory<Caregiver, String>("phoneNumber"));
        this.colPhoneNumber.setCellFactory(TextFieldTableCell.forTableColumn());


        //Anzeigen der Daten
        this.tableView.setItems(this.tableviewContent);
    }

    /**
     * handles new surname value
     * @param event event including the value that a user entered into the cell
     */


    @FXML
    public void handleOnEditSurname(TableColumn.CellEditEvent<Caregiver, String> event){
        Boolean hasNumeric = false;
        String sample = event.getNewValue();
        char[] chars = sample.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char c : chars){
            if(Character.isDigit(c)){
                hasNumeric = true;
            }
        }
        if (hasNumeric) {
            event.getRowValue().setSurname(event.getOldValue());
            doUpdate(event);
            JFrame frame = new JFrame("Error");
            JOptionPane.showMessageDialog(frame, "Im Namen dürfen keine Zahlen vorkommen");
        } else {
            event.getRowValue().setSurname(event.getNewValue());

        }
        doUpdate(event);
    }

    /**
     * handles new firstname value
     * @param event event including the value that a user entered into the cell
     */

    @FXML
    public void handleOnEditFirstname(TableColumn.CellEditEvent<Caregiver, String> event){
        Boolean hasNumeric = false;
        String sample = event.getNewValue();
        char[] chars = sample.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char c : chars){
            if(Character.isDigit(c)){
                hasNumeric = true;
            }
        }
        if (hasNumeric) {
            event.getRowValue().setFirstName(event.getOldValue());
            doUpdate(event);
            JFrame frame = new JFrame("Error");
            JOptionPane.showMessageDialog(frame, "Im Namen dürfen keine Zahlen vorkommen");
        } else {
            event.getRowValue().setFirstName(event.getNewValue());

        }
        doUpdate(event);

    }

    /**
     * handles new phoneNumber value
     * @param event event including the value that a user entered into the cell
     */

    @FXML
    public void handleOnEditPhoneNumber(TableColumn.CellEditEvent<Caregiver, String> event){
        event.getRowValue().setPhoneNumber(event.getNewValue());
        doUpdate(event);
    }

    /**
     * updates a caregiver by calling the update-Method in the {@link CaregiverDAO}
     * @param t row to be updated by the user (includes the patient)
     */

    private void doUpdate(TableColumn.CellEditEvent<Caregiver, String> t) {
        try {
            dao.update(t.getRowValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * calls readAll in {@link CaregiverDAO} and shows caregiver in the table
     */

    private void readAllAndShowInTableView() {
        this.tableviewContent.clear();
        this.dao = DAOFactory.getDAOFactory().createCaregiverDAO();
        List<Caregiver> allCaregivers;
        try {
            allCaregivers = dao.readAll();
            for (Caregiver c : allCaregivers) {
                this.tableviewContent.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * handles a delete-click-event. Calls the delete methods in the {@link CaregiverDAO} and {@link TreatmentDAO}
     */

    @FXML
    public void handleDeleteRow() {
        TreatmentDAO tDao = DAOFactory.getDAOFactory().createTreatmentDAO();
        Caregiver selectedItem = this.tableView.getSelectionModel().getSelectedItem();
        this.tableView.getItems().remove(selectedItem);
        try {
            dao.deleteById((int) selectedItem.getCid());
            tDao.deleteByPid((int) selectedItem.getCid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.handleAdd();
    }

    /**
     * handles a add-click-event. Creates a caregiver and calls the create method in the {@link CaregiverDAO}
     */

    @FXML
    public void handleAdd() {

        Boolean hasNumeric = false;
        String sample = this.txtFirstname.getText();
        char[] chars = sample.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(char c : chars){
            if(Character.isDigit(c)){
                hasNumeric = true;
            }
        }
        Boolean hasNumeric2 = false;
        String sample2 = this.txtSurname.getText();
        char[] chars2 = sample.toCharArray();
        StringBuilder sb2 = new StringBuilder();
        for(char c : chars){
            if(Character.isDigit(c)){
                hasNumeric = true;
            }
        }

        if ((hasNumeric) | (hasNumeric2))
        {
            JFrame frame = new JFrame("Error");
            JOptionPane.showMessageDialog(frame, "Im Namen dürfen keine Zahlen vorkommen");
        }
        else
            {
            String firstname = this.txtFirstname.getText();
            String surname = this.txtSurname.getText();
            String phoneNumber = this.txtPhoneNumber.getText();

            try {
                Caregiver c = new Caregiver(firstname, surname, phoneNumber);
                dao.create(c);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            readAllAndShowInTableView();
        }
        clearTextfields();

    }


    /**
     * removes content from all textfields
     */

    private void clearTextfields() {
        this.txtFirstname.clear();
        this.txtSurname.clear();
        this.txtPhoneNumber.clear();
    }

}
