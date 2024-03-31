package pt.unl.fct.di.apdc.firstwebapp.resources;

import com.google.appengine.repackaged.org.apache.commons.codec.digest.DigestUtils;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.gson.Gson;
import pt.unl.fct.di.apdc.firstwebapp.util.RegisterData;
import pt.unl.fct.di.apdc.firstwebapp.util.RoleChangeData;

import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

import static com.google.cloud.datastore.TransactionOperationExceptionHandler.build;

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
    public Response doRoleChange(@CookieParam("session::apdc") Cookie cookie, RoleChangeData data) {

        String[] values = cookie.getValue().split(".");

        Key userKey = datastore.newKeyFactory().setKind("User").newKey(data.usernameToChange);

        if(values[2].equals("SU")){
            Entity user = Entity.newBuilder(userKey)
                    .set("role", data.role)
                    .build();

            datastore.put(user);

            LOG.info("Role changed for user " + data.usernameToChange);
        }

        return Response.ok().build();
    }
}
