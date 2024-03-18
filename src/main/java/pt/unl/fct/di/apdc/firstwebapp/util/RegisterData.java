package pt.unl.fct.di.apdc.firstwebapp.util;

public class RegisterData {

    public String userName;
    public String password;
    public String name;
    public String email;
    public String confirmation;

    public RegisterData(){}

    public RegisterData(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

}
