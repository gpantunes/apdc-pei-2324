package pt.unl.fct.di.apdc.firstwebapp.util;

public class RegisterData {

    public String username;
    public String name;
    public String email;
    public String phoneNumber;
    public String password;
    public String confirmation;
    public Boolean publicProfile;
    public String occupation;
    public String workPlace;
    public String address;
    public String postalCode;
    public String nif;
    public String role;


    public RegisterData(){}

    public RegisterData(String username, String password, String email, String name, String phoneNumber, Boolean publicProfile,
                        String occupation, String workPlace, String address, String postalCode, String nif, String role){
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.publicProfile = publicProfile;
        this.occupation = occupation;
        this.workPlace = workPlace;
        this.address = address;
        this.postalCode = postalCode;
        this.nif = nif;
        this.role = role;
    }

    public boolean validRegistration(){
        if(username != null && password != null)
            return true;

        return false;
    }
}
