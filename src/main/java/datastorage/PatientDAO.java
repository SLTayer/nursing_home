package datastorage;

import model.Patient;
import utils.DateConverter;

import java.sql.Statement;
import java.text.*;
import java.util.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Implements the Interface <code>DAOImp</code>. Overrides methods to generate specific patient-SQL-queries.
 */
public class PatientDAO extends DAOimp<Patient> {

    /**
     * constructs Onbject. Calls the Constructor from <code>DAOImp</code> to store the connection.
     * @param conn
     */
    public PatientDAO(Connection conn) {
        super(conn);
    }

    /**
     * generates a <code>INSERT INTO</code>-Statement for a given patient
     * @param patient for which a specific INSERT INTO is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getCreateStatementString(Patient patient) {
        return String.format("INSERT INTO patient (firstname, surname, dateOfBirth, carelevel, roomnumber, assets) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
                patient.getFirstName(), patient.getSurname(), patient.getDateOfBirth(), patient.getCareLevel(), patient.getRoomnumber(), patient.getAssets());
    }

    /**
     * generates a <code>select</code>-Statement for a given key
     * @param key for which a specific SELECTis to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getReadByIDStatementString(int key) {
        return String.format("SELECT * FROM patient WHERE pid = %d", key);
    }

    /**
     * maps a <code>ResultSet</code> to a <code>Patient</code>
     * @param result ResultSet with a single row. Columns will be mapped to a patient-object.
     * @return patient with the data from the resultSet.
     */
    @Override
    protected Patient getInstanceFromResultSet(ResultSet result) throws SQLException {
        Patient p = null;
        LocalDate date = DateConverter.convertStringToLocalDate(result.getString(4));
        p = new Patient(result.getInt(1), result.getString(2),
                result.getString(3), date, result.getString(5),
                result.getString(6), result.getString(7));
        return p;
    }

    /**
     * generates a <code>SELECT</code>-Statement for all patients.
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getReadAllStatementString() {
        return "SELECT * FROM patient";
    }

    /**
     * maps a <code>ResultSet</code> to a <code>Patient-List</code>
     * @param result ResultSet with a multiple rows. Data will be mapped to patient-object.
     * @return ArrayList with patients from the resultSet.
     */
    @Override
    protected ArrayList<Patient> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Patient> list = new ArrayList<Patient>();
        Patient p = null;
        while (result.next()) {
            LocalDate date = DateConverter.convertStringToLocalDate(result.getString(4));
            p = new Patient(result.getInt(1), result.getString(2),
                    result.getString(3), date,
                    result.getString(5), result.getString(6), result.getString(7));
            list.add(p);
        }
        return list;
    }

    /**
     * generates a <code>UPDATE</code>-Statement for a given patient
     * @param patient for which a specific update is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getUpdateStatementString(Patient patient) {
        return String.format("UPDATE patient SET firstname = '%s', surname = '%s', dateOfBirth = '%s', carelevel = '%s', " +
                "roomnumber = '%s', assets = '%s' WHERE pid = %d", patient.getFirstName(), patient.getSurname(), patient.getDateOfBirth(),
                patient.getCareLevel(), patient.getRoomnumber(), patient.getAssets(), patient.getPid());
    }

    /**
     * Creates a backup for the Patient Data
     */
    public boolean createBackup() throws SQLException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String today = dateFormat.format(new Date());

        ArrayList<Patient> list;
        Patient object = null;
        Statement st = conn.createStatement();
        ResultSet result = st.executeQuery(this.getReadAllStatementString());
        list = this.getListFromResultSet(result);

        for (Patient patient : list) {
            try {
                String query = String.format("INSERT INTO backups (pid, firstname, surname, dateOfBirth, carelevel, roomnumber, assets, backup) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
                        patient.getPid(), patient.getFirstName(), patient.getSurname(), patient.getDateOfBirth(), patient.getCareLevel(), patient.getRoomnumber(), patient.getAssets(), today);
                st.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * Returns the different Backup Versions
     */
    public List<String> getBackups() throws SQLException {

        String statement = "SELECT * FROM backups";

        Statement st = conn.createStatement();
        ResultSet result = st.executeQuery(statement);

        List<String> backups = new ArrayList<>();

        while (result.next()) {
            String backup = result.getString(9);
            if (!backups.contains(backup) && backup != null) {
                backups.add(backup);
            }
        }
        return backups;
    }

    /**
     * Loads a backup
     */
    public boolean loadBackup(String backup) throws SQLException {
        Statement st = conn.createStatement();

        String getBackupsQuery = "SELECT * FROM backups WHERE backup = '" + backup + "'";

        try {
            ResultSet result = st.executeQuery(getBackupsQuery);
            List<Patient> patients = new ArrayList<>();
            while (result.next()) {
                Integer pid = result.getInt(2);
                LocalDate date = DateConverter.convertStringToLocalDate(result.getString(5));
                Patient p = new Patient(pid, result.getString(3), result.getString(4), date,
                        result.getString(6), result.getString(7), result.getString(8));

                String selectPatientQuery = "SELECT * FROM patient WHERE pid = " + pid;
                String updatePatientQuery = String.format("UPDATE patient SET firstname = '%s', surname = '%s', dateOfBirth = '%s', carelevel = '%s', " +
                                "roomnumber = '%s', assets = '%s' WHERE pid = %d", p.getFirstName(), p.getSurname(), p.getDateOfBirth(),
                        p.getCareLevel(), p.getRoomnumber(), p.getAssets(), p.getPid());
                ResultSet patientResult = st.executeQuery(selectPatientQuery);
                if (patientResult.next()) {
                    st.executeUpdate(updatePatientQuery);
                } else {
                    st.executeUpdate(getCreateStatementString(p));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * generates a <code>delete</code>-Statement for a given key
     * @param key for which a specific DELETE is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getDeleteStatementString(int key) {
        return String.format("Delete FROM patient WHERE pid=%d", key);
    }
}
