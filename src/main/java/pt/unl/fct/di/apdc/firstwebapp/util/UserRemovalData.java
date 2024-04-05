package pt.unl.fct.di.apdc.firstwebapp.util;

import com.google.appengine.api.users.User;

public class UserRemovalData {

    public String usernameToChange;

    public UserRemovalData(){}

    public UserRemovalData(String usernameToChange){
        this.usernameToChange = usernameToChange;
    }
}
