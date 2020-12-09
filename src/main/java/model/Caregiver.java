package model;

import utils.DateConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Caregiver extends Person {
    private long cid;
    private String phoneNumber;
    private List<Treatment> allTreatments = new ArrayList<Treatment>();


    public Caregiver(String firstname, String firstName, String surname) {
        super(firstName, surname);
        this.phoneNumber = phoneNumber;
    }

    public Caregiver(long cid, String firstName, String surname, String phoneNumber) {
        super(firstName, surname);
        this.cid = cid;
        this.phoneNumber = phoneNumber;
    }


    public long getCid() {
        return cid;
    }

    /**
     *
     * @return phoneNumber
     */

    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     *
     * @param phoneNumber new phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * adds a treatment to the treatment-list, if it does not already contain it.
     * @param m Treatment
     * @return true if the treatment was not already part of the list. otherwise false
     */

    public boolean add(Treatment m) {
        if (!this.allTreatments.contains(m)) {
            this.allTreatments.add(m);
            return true;
        }
        return false;
    }

    /**
     *
     * @return string-representation of the caregiver
     */
    public String toString() {
        return "Patient" + "\nMNID: " + this.cid +
                "\nFirstname: " + this.getFirstName() +
                "\nSurname: " + this.getSurname() +
                "\nBirthday: " + this.phoneNumber +
                "\n";
    }

}
