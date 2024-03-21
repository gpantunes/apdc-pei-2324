package pt.unl.fct.di.apdc.firstwebapp.util;

public class LoginData {

    public String username;
    public String password;
    public String email;

    public LoginData(){}

    public LoginData(String userName, String password, String email){
        this.username = userName;
        this.password = password;
        this.email = email;
    }

}
