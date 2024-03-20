package pt.unl.fct.di.apdc.firstwebapp.util;

public class RegisterData {

    public String username;
    public String password;
    public String name;
    public String email;
    public String confirmation;

    public RegisterData(){}

    public RegisterData(String username, String password){
        this.username = username;
        this.password = password;
    }

}
