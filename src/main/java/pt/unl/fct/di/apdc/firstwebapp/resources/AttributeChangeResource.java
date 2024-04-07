package pt.unl.fct.di.apdc.firstwebapp.resources;

import com.google.cloud.datastore.*;
import pt.unl.fct.di.apdc.firstwebapp.util.AttributeChangeData;

import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;
import javax.ws.rs.core.Response.Status;

@Path("/state_change")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class AttributeChangeResource {

    /*
     * DataStore
     */
    private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

    /*
     * Logger Object
     */
    private static final Logger LOG = Logger.getLogger(RegisterResource.class.getName());

    private static String USERNAME = "username";
    private static String EMAIL = "email";
    private static String NOME = "name";
    private static String ROLE = "role";
    private static String ESTADO = "activation_state";


    public AttributeChangeResource(){
    }

    @POST
    @Path("/v1")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doStateChange(@CookieParam("session::apdc") Cookie sessionCookie, AttributeChangeData data) {

        String[] params = sessionCookie.getValue().split("\\.");

        Transaction txn = datastore.newTransaction();
        try {
            Key userKey = datastore.newKeyFactory().setKind("User").newKey(data.usernameToChange);
            Entity user = txn.get(userKey);

            String userRole = user.getString("role");

            if (params[2].equals("USER") && params[0].equals(user.getString("username"))) {

                if(checkAttribute(data.attributeToChange)){
                    if(changeAttribute(data, user, txn))
                        return Response.ok().entity("Atributo alterado com sucesso").build();
                    else return Response.status(Response.Status.BAD_REQUEST).build();
                }

            }else if(params[2].equals("GBO")){

                if(!userRole.equals("USER"))
                    return Response.status(Response.Status.FORBIDDEN).build();
                else if(userRole.equals("USER") && checkAttribute(data.attributeToChange))
                    if(changeAttribute(data, user, txn))
                        return Response.ok().entity("Atributo alterado com sucesso.").build();
                    else return Response.status(Status.BAD_REQUEST).entity("Error while changing role.").build();

            } else if (params[2].equals("GA")) {

                if ((userRole.equals("USER") || userRole.equals("GBO")) && checkAttribute(data.attributeToChange)) {
                    if(changeAttribute(data, user, txn))
                        return Response.ok().entity("Status alterado com sucesso.").build();
                    else return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error while changing status.").build();
                }

            }else if(params[2].equals("SU")){

                if(checkAttribute(data.attributeToChange)) {
                    if (changeAttribute(data, user, txn))
                        return Response.ok().entity("Status alterado com sucesso").build();
                    else
                        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error while changing status.").build();
                }
            } else return Response.status(Response.Status.FORBIDDEN).build();


            return Response.ok().build();
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
    }

    private boolean checkAttribute(String attributeToChange) {
        boolean validAttribute = true;

        if(attributeToChange.equals(ESTADO) || attributeToChange.equals(USERNAME) || attributeToChange.equals(EMAIL)
                || attributeToChange.equals(NOME) || attributeToChange.equals(ROLE)){
            validAttribute = false;
        }

        return validAttribute;
    }


    private boolean changeAttribute(AttributeChangeData data, Entity user, Transaction txn){

        Entity.Builder tempUser = Entity.newBuilder(user);
        tempUser.set(data.attributeToChange, data.attributeValue);

        Entity update = tempUser.build();
        txn.update(update);
        LOG.info(data.attributeToChange + " changed to: " + data.attributeValue);
        txn.commit();

        return true;
    }
}