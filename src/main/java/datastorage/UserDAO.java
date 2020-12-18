package datastorage;


import controller.Main;
import javafx.fxml.FXMLLoader;
import model.User;


import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;
import java.util.ArrayList;

public class UserDAO extends DAOimp<User>{

    public static  User user;

    public UserDAO(Connection conn) {
        super(conn);
    }


    @Override
    protected String getCreateStatementString(User user) {
        return String.format("INSERT INTO user ( UserName,Password,PermissionCaregiver, PermissionPatient,PermissionTreatment) VALUES ('%s', '%s', '%s', '%s', '%s')", user.getUsername(), user.getPassword(), user.getPermissionCaregiver(),user.getPermissionPatient(),user.getPermissionTreatment());


    }


    @Override
    protected String getReadByIDStatementString(int key) {
        return String.format("SELECT * FROM User WHERE UID = %d", key);
    }

    @Override
    protected User getInstanceFromResultSet(ResultSet result) throws SQLException {
        User u;

        u = new User(result.getInt(0),
                result.getString(1),
                result.getString(2),
                result.getInt(3),
                result.getInt(4),
                result.getInt(5));

        return u;
    }


    @Override
    protected String getReadAllStatementString() {
        return "SELECT * FROM User";
    }


    @Override
    protected ArrayList<User> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<User> list = new ArrayList<User>();
        User u  = null;
        while (result.next()) {

            u = new User(result.getInt(1),
                    result.getString(2)
                    , result.getString(3),
                    result.getInt(4),
                    result.getInt(5),
                    result.getInt(6));
            list.add(u);
        }
        return list;
    }


    @Override
    protected String getUpdateStatementString(User user) {
        return String.format("UPDATE User SET Username = '%s', Password = '%s', PermissionCaregiver = '%s', PermissionTreatment = '%s', PermissionPatient = '%s' + WHERE UID = %d", user.getUsername(), user.getPassword(), user.getPermissionCaregiver(),user.getPermissionPatient(),user.getPermissionTreatment());
    }


    @Override
    protected String getDeleteStatementString(int key) {
        return String.format("Delete FROM User WHERE UID=%s", key);
    }



    public User login(String username,String password){
        User user;
        String passwordInput = String.format("SELECT * FROM USER WHERE USERNAME = '%s' AND PASSWORD = '%s' ",username,password);
        try {
            Statement st = conn.createStatement();
            ResultSet result = st.executeQuery(passwordInput);
            if (!result.next() ) {
                return null;
            } else {
                user = new User(result.getString(2),result.getString(3),result.getInt(4),result.getInt(5),result.getInt(6));
               return user ;

            }

        } catch (SQLException b) {
            b.printStackTrace();
        }
        return null;
    }
}
