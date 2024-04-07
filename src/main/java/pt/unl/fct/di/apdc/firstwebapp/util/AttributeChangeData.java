package pt.unl.fct.di.apdc.firstwebapp.util;

public class AttributeChangeData {

    public String usernameToChange;
    public String attributeToChange;
    public String attributeValue;

    public AttributeChangeData(){}

    public AttributeChangeData(String usernameToChange, String attributeToChange, String attributeValue){
        this.usernameToChange = usernameToChange;
        this.attributeToChange = attributeToChange;
        this.attributeValue = attributeValue;
    }

}