package controller;

import datastorage.DAOFactory;
import datastorage.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.sql.Connection;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.control.TextField;
import model.User;

public class MainWindowController {
    private UserDAO dao;
    private Connection conn;
    private User loggedInUser;
   private  FXMLLoader loader2 = new FXMLLoader();
    @FXML
    private BorderPane mainBorderPane;
    @FXML private TextField UsernameInput;
    @FXML private TextField PasswordInput;

    @FXML
    private void handleShowAllPatient(ActionEvent e) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/AllPatientView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        AllPatientController controller = loader.getController();
    }

    @FXML
    private void handleShowAllTreatments(ActionEvent e) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/AllTreatmentView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        AllTreatmentController controller = loader.getController();
    }

    @FXML
    private void handleShowAllCaregiver(ActionEvent e) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/AllCaregiverView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        AllCaregiverController controller = loader.getController();
    }
    @FXML
    private void handleLogin(ActionEvent e) {
        this.dao = DAOFactory.getDAOFactory().createUserDAO();

        String username = UsernameInput.getText();
        String password = PasswordInput.getText();
        if (dao.login(username,password)!=null){

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/MainWindowView.fxml"));
            try {
                mainBorderPane.setCenter(loader.load());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            JFrame frame = new JFrame("Error");
            JOptionPane.showMessageDialog(frame, "Mach doch richtig du hund");

        }

    }
    @FXML
    private void handleShowAllUsers(ActionEvent e) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/AllUsersView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        AllUserController controller = loader.getController();
    }
    @FXML
    public void handleLogout(ActionEvent actionEvent) {

            try {
                loader2.load(getClass().getResource("/MainWindowViewLogin.fxml"));
                mainBorderPane.setCenter(loader2.load(getClass().getResource("/MainWindowViewLogin.fxml")));
            } catch (IOException ex) {
                ex.printStackTrace();
            }


    }
}


