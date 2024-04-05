package pt.unl.fct.di.apdc.firstwebapp.util;

public class RoleChangeData {

    public String usernameToChange;
    public String role;

    public RoleChangeData(){}

    public RoleChangeData(String usernameToChange, String role){
        this.usernameToChange = usernameToChange;
        this.role = role;
    }
}
