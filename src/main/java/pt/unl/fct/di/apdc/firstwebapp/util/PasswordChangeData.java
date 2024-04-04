package pt.unl.fct.di.apdc.firstwebapp.util;

public class PasswordChangeData {

    public String currentPassword;
    public String newPassword;
    public String confirmation;

    public PasswordChangeData(){}

    public PasswordChangeData(String currentPassword, String newPassword, String confirmation){
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.confirmation = confirmation;
    }

}
