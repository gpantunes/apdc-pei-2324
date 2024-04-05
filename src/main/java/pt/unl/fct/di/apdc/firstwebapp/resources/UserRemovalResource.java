package pt.unl.fct.di.apdc.firstwebapp.resources;

import com.google.cloud.datastore.*;

import pt.unl.fct.di.apdc.firstwebapp.util.UserRemovalData;

import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.logging.Logger;

@Path("/remove_user")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class UserRemovalResource {

    /*
     * DataStore
     */
    private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

    /*
     * Logger Object
     */
    private static final Logger LOG = Logger.getLogger(RegisterResource.class.getName());

    public UserRemovalResource(){
    }

    @POST
    @Path("/v1")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doUserRemoval(@CookieParam("session::apdc") Cookie sessionCookie, UserRemovalData data) {

        String[] params = sessionCookie.getValue().split("\\.");

        Transaction txn = datastore.newTransaction();
        try {
            Key userKey = datastore.newKeyFactory().setKind("User").newKey(data.usernameToChange);
            Entity user = txn.get(userKey);

            String userRole = user.getString("role");

            //return Response.ok().entity(params[0] + " " + data.usernameToChange).build();

            if (params[2].equals("USER") ) {
                if(params[0].equals(data.usernameToChange))
                    if(deleteUser(data, userKey, txn))
                        return Response.ok().entity("User deleted").build();
                else return Response.status(Status.FORBIDDEN).build();

            }else if(params[2].equals("GBO")){
                return Response.status(Status.FORBIDDEN).build();

            } else if (params[2].equals("GA")) {
                if (userRole.equals("USER") || userRole.equals("GBO")) {
                    if(deleteUser(data, userKey, txn))
                        return Response.ok().entity("User apagado com sucesso.").build();
                    else return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error while deleting user.").build();
                } else {
                    return Response.status(Status.FORBIDDEN).build();
                }

            }else if(params[2].equals("SU")){

                if(deleteUser(data, userKey, txn))
                    return Response.ok().entity("User apagado com sucesso").build();
                else return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error while deleting user.").build();
            }

            return Response.status(Status.BAD_REQUEST).build();
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
    }


    private boolean deleteUser(UserRemovalData data, Key userKey, Transaction txn){
        txn.delete(userKey);
        LOG.info("User " + data.usernameToChange + " deleted.");
        txn.commit();

        return true;
    }

}
