package datastorage;


import model.User;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;

public class UserDAO extends DAOimp<User>{



    public UserDAO(Connection conn) {
        super(conn);
    }


    @Override
    protected String getCreateStatementString(User user) {
        return String.format("INSERT INTO user (UserID, UserName,Password,PermissionCaregiver, PermissionPatient,PermissionTreatment) VALUES ('%s', '%s', '%s', '%s', '%s')", user.getUsername(), user.getPassword(), user.getPermissionCaregiver(),user.getPermissionPatient(),user.getPermissionTreatment());


    }


    @Override
    protected String getReadByIDStatementString(int key) {
        return String.format("SELECT * FROM User WHERE UserID = %d", key);
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
        User u ;
        while (result.next()) {

            u = new User(result.getInt(0),
                    result.getString(1)
                    , result.getString(2),
                    result.getInt(3),
                    result.getInt(4),
                    result.getInt(5));
            list.add(u);
        }
        return list;
    }


    @Override
    protected String getUpdateStatementString(User user) {
        return String.format("UPDATE User SET Username = '%s', Password = '%s', PermissionCaregiver = '%s', PermissionTreatment = '%s', PermissionPatient = '%s' + WHERE cid = %d", user.getUsername(), user.getPassword(), user.getPermissionCaregiver(),user.getPermissionPatient(),user.getPermissionTreatment());
    }


    @Override
    protected String getDeleteStatementString(int key) {
        return String.format("Delete FROM User WHERE UserID=%d", key);
    }
}
