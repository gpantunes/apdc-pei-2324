package pt.unl.fct.di.apdc.firstwebapp.util;

public class StateChangeData {

    public String usernameToChange;
    public boolean state;

    public StateChangeData(){}

    public StateChangeData(String usernameToChange, boolean state){
        this.usernameToChange = usernameToChange;
        this.state = state;
    }
}
