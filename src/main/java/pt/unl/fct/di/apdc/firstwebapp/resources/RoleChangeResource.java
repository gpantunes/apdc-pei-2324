package pt.unl.fct.di.apdc.firstwebapp.resources;

import com.google.cloud.datastore.*;
import com.google.gson.Gson;
import pt.unl.fct.di.apdc.firstwebapp.util.RoleChangeData;

import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.logging.Logger;

@Path("/role_change")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class RoleChangeResource {

    /*
     * DataStore
     */
    private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

    /*
     * Logger Object
     */
    private static final Logger LOG = Logger.getLogger(RegisterResource.class.getName());

    //usado para manipular objetos json!
    private final Gson g = new Gson();

    public RoleChangeResource() {
    } //Nothing to be done here


    @POST
    @Path("/v1")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doRoleChange(@CookieParam("session::apdc") Cookie sessionCookie, RoleChangeData data) {

        String[] params = sessionCookie.getValue().split("\\.");

        Transaction txn = datastore.newTransaction();
        try {
            Key userKey = datastore.newKeyFactory().setKind("User").newKey(data.usernameToChange);
            Entity user = txn.get(userKey);

            if (params[2].equals("USER") || params[2].equals("GBO")) {
                return Response.ok().entity("Não tem as permissões necessárias.").build();
            } else if (params[2].equals("GA")) {

                String userRole = user.getString("role");
                if (userRole.equals("USER") || userRole.equals("GBO")) {
                    if(changeRole(data, user, txn))
                        return Response.ok().entity("Role alterado com sucesso").build();
                    else return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error while changing role.").build();
                } else {
                    return Response.ok().entity("Não tem as permissões necessárias.").build();
                }

            }else if(params[2].equals("SU")){

                if(changeRole(data, user, txn))
                    return Response.ok().entity("Role alterado com sucesso").build();
                else return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error while changing role.").build();
            }


            return Response.ok().build();
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }
    }



    private boolean changeRole(RoleChangeData data, Entity user, Transaction txn){

        Entity.Builder tempUser = Entity.newBuilder(user);
        tempUser.set("role", data.role);

        Entity update = tempUser.build();
        txn.update(update);
        LOG.info("Role changed " + data.role);
        txn.commit();

        return true;
    }
}

