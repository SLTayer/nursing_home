package model;



public class User {
    private int UserID;
    private String Username;
    private String Password;
    private int PermissionCaregiver;
    private int PermissionPatient;
    private int PermissionTreatment;

    public User(

            String Username
            ,String Password
            ,int PermissionCaregiver
            ,int PermissionPatient
            ,int PermissionTreatment){

        this.Username = Username;
        this.Password = Password;
        this.PermissionCaregiver = PermissionCaregiver;
        this.PermissionPatient = PermissionPatient;
        this.PermissionTreatment = PermissionTreatment;}

    public User(
            int UserID
             ,String Username
             ,String Password
             ,int PermissionCaregiver
             ,int PermissionPatient
            ,int PermissionTreatment){
    this.UserID = UserID;
    this.Username = Username;
    this.Password = Password;
    this.PermissionCaregiver = PermissionCaregiver;
    this.PermissionPatient = PermissionPatient;
    this.PermissionTreatment = PermissionTreatment;}

    public Integer getUserID() {
        return UserID;

    }

    public String getUsername() {
        return Username;
    }
    public void setUsername(String username) {this.Username = username;}
    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {this.Password = password;}
    public int getPermissionCaregiver(){return PermissionCaregiver;}
    public void setPermissionCaregiver(Integer i) {this.PermissionCaregiver = i;}
    public int getPermissionPatient(){return PermissionPatient;}
    public void setPermissionPatient(Integer i) {this.PermissionPatient = i;}
    public int getPermissionTreatment(){return PermissionTreatment;}
    public void setPermissionTreatment(Integer i) {this.PermissionTreatment = i;}


    }


