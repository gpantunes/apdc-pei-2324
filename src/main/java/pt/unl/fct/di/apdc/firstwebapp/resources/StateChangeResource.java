package pt.unl.fct.di.apdc.firstwebapp.resources;

import com.google.cloud.datastore.*;
import com.google.gson.Gson;
import pt.unl.fct.di.apdc.firstwebapp.util.RoleChangeData;
import pt.unl.fct.di.apdc.firstwebapp.util.StateChangeData;

import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.logging.Logger;

@Path("/state_change")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class StateChangeResource {

    /*
     * DataStore
     */
    private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

    /*
     * Logger Object
     */
    private static final Logger LOG = Logger.getLogger(RegisterResource.class.getName());

    public StateChangeResource(){
    }

    @POST
    @Path("/v1")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doRoleChange(@CookieParam("session::apdc") Cookie sessionCookie, StateChangeData data) {

        String[] params = sessionCookie.getValue().split("\\.");

        Transaction txn = datastore.newTransaction();
        try {
            Key userKey = datastore.newKeyFactory().setKind("User").newKey(data.usernameToChange);
            Entity user = txn.get(userKey);

            String userRole = user.getString("role");

            if (params[2].equals("USER")) {
                return Response.status(Status.FORBIDDEN).build();

            }else if(params[2].equals("GBO")){

                if(!userRole.equals("USER"))
                    return Response.status(Status.FORBIDDEN).build();
                else if(userRole.equals("USER"))
                    if(changeState(data, user, txn))
                        return Response.ok().entity("Status alterado com sucesso.").build();
                    else return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error while changing role.").build();

            } else if (params[2].equals("GA")) {

                if (userRole.equals("USER") || userRole.equals("GBO")) {
                    if(changeState(data, user, txn))
                        return Response.ok().entity("Status alterado com sucesso.").build();
                    else return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error while changing status.").build();
                } else {
                    return Response.status(Status.FORBIDDEN).build();
                }

            }else if(params[2].equals("SU")){

                if(changeState(data, user, txn))
                    return Response.ok().entity("Status alterado com sucesso").build();
                else return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error while changing status.").build();
            }


            return Response.ok().build();
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
    }


    private boolean changeState(StateChangeData data, Entity user, Transaction txn){

        Entity.Builder tempUser = Entity.newBuilder(user);
        tempUser.set("public_profile", data.state);

        Entity update = tempUser.build();
        txn.update(update);
        LOG.info("State changed " + data.state);
        txn.commit();

        return true;
    }

}
