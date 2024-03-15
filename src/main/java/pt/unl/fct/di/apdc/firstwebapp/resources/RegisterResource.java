package pt.unl.fct.di.apdc.firstwebapp.resources;

import pt.unl.fct.di.apdc.firstwebapp.util.LoginData;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Entity.Builder;


@Path("/register")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class RegisterResource {

    //Logger object
    private static final Logger LOG = Logger.getLogger(LoginResource.class.getName());

    public RegisterResource() {} //Construtor vaziu

    private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doRegister(LoginData data){
        LOG.fine("Attempt to register user: " + data.userName);

        Key userKey = datastore.newKeyFactory().setKind("Person").newKey("Carlos");
        Entity person = Entity.newBuilder(userKey).set("username", "goncas").set("password", "password").build();

        datastore.put(person);

        return Response.ok().build();
    }
}
