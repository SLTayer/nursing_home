package controller;

import datastorage.UserDAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.User;
import utils.DateConverter;
import datastorage.DAOFactory;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;



public class AllUserController {
    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, Integer> colUserID;
    @FXML
    private TableColumn<User, String> colUsername;
    @FXML
    private TableColumn<User, String> colPassword;
    @FXML
    private TableColumn<User, Integer> colPermissionCaregiver;
    @FXML
    private TableColumn<User, Integer > colPermissionTreatment;
    @FXML
    private TableColumn<User, Integer> colPermissionPatient;


    @FXML
    Button btnDelete;
    @FXML
    Button btnAdd;

    @FXML
    TextField txtUsername;
    @FXML
    TextField txtPassword;
    @FXML
    TextField txtPermissionCaregiver;
    @FXML
    TextField txtPermissionTreatment;
    @FXML
    TextField txtPermissionPatient;


    private ObservableList<User> tableviewContent = FXCollections.observableArrayList();
    private UserDAO dao;



    /**
     * Initializes the corresponding fields. Is called as soon as the corresponding FXML file is to be displayed.
     */
    public void initialize() {
        readAllAndShowInTableView();

        this.colUserID.setCellValueFactory(new PropertyValueFactory<User, Integer>("UserID"));

        //CellValuefactory zum Anzeigen der Daten in der TableView
        this.colUsername.setCellValueFactory(new PropertyValueFactory<User, String>("Username"));
        //CellFactory zum Schreiben innerhalb der Tabelle
        this.colUsername.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colPassword.setCellValueFactory(new PropertyValueFactory<User, String>("Password"));
        this.colPassword.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colPermissionCaregiver.setCellValueFactory(new PropertyValueFactory<User, Integer>("PermissionCaregiver"));
        //this.colPermissionCaregiver.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colPermissionTreatment.setCellValueFactory(new PropertyValueFactory<User, Integer>("PermissionTreatment"));
        //this.colPermissionTreatment.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colPermissionPatient.setCellValueFactory(new PropertyValueFactory<User, Integer>("PermissionPatient"));
        //this.colPermissionPatient.setCellFactory(TextFieldTableCell.forTableColumn());



        //Anzeigen der Daten
        this.tableView.setItems(this.tableviewContent);
    }

    /**
     * handles new firstname value
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditUsername(TableColumn.CellEditEvent<User, String> event){
        event.getRowValue().setUsername(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new surname value
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditPassword(TableColumn.CellEditEvent<User, String> event){
        event.getRowValue().setPassword(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new birthdate value
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditPermissionCaregiver(TableColumn.CellEditEvent<User, String> event){
        event.getRowValue().setPermissionCaregiver(Integer.parseInt(event.getNewValue()));
        doUpdate(event);
    }


    @FXML
    public void handleOnEditPermissionTreatment(TableColumn.CellEditEvent<User, String> event){
        event.getRowValue().setPermissionTreatment(Integer.parseInt(event.getNewValue()));
        doUpdate(event);
    }


    @FXML
    public void handleOnEditPermissionPatient(TableColumn.CellEditEvent<User, String> event){
        event.getRowValue().setPermissionPatient(Integer.parseInt(event.getNewValue()));
        doUpdate(event);
    }




    private void doUpdate(TableColumn.CellEditEvent<User, String> t) {
        try {
            dao.update(t.getRowValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void readAllAndShowInTableView() {
        this.tableviewContent.clear();
        this.dao = DAOFactory.getDAOFactory().createUserDAO();
        List<User> allUsers;
        try {
            allUsers = dao.readAll();
            for (User u : allUsers) {
                this.tableviewContent.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void handleDeleteRow() {

        User selectedItem = this.tableView.getSelectionModel().getSelectedItem();
        this.tableView.getItems().remove(selectedItem);
        try {
            dao.deleteById((selectedItem.getUserID()) );

        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.handleAdd();
    }


    @FXML
    public void handleAdd() {

        String username = this.txtUsername.getText();
        String password = this.txtPassword.getText();
        Integer permissionCaregiver = Integer.parseInt(this.txtPermissionCaregiver.getText());
        Integer permissionTreatment = Integer.parseInt(this.txtPermissionTreatment.getText());
        Integer permissionPatient = Integer.parseInt(this.txtPermissionPatient.getText());

        try {
            User u = new User(username, password, permissionCaregiver,  permissionTreatment,permissionPatient );
            dao.create(u);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        readAllAndShowInTableView();
        clearTextfields();
    }

    /**
     * removes content from all textfields
     */
    private void clearTextfields() {

        this.txtUsername.clear();
        this.txtPassword.clear();
        this.txtPermissionCaregiver.clear();
        this.txtPermissionTreatment.clear();
        this.txtPermissionPatient.clear();
    }
}