package pt.unl.fct.di.apdc.firstwebapp.resources;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.appengine.repackaged.org.apache.commons.codec.digest.DigestUtils;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.gson.Gson;

import pt.unl.fct.di.apdc.firstwebapp.util.AuthToken;
import pt.unl.fct.di.apdc.firstwebapp.util.LoginData;
import pt.unl.fct.di.apdc.firstwebapp.util.RegisterData;

@Path("/register")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")//truque para evitar que os clients tenham problemas com o encoding
public class RegisterResource {

    /*
     * DataStora
     */
    private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

    /*
     * Logger Object
     */
    private static final Logger LOG = Logger.getLogger(RegisterResource.class.getName());

    //usado para manipular objetos json!
    private final Gson g = new Gson();

    public RegisterResource() {
    } //Nothing to be done here

    @POST
    @Path("/v1")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doRegister(RegisterData data) {
        Key userKey = datastore.newKeyFactory().setKind("User").newKey(data.username);
        Entity user = Entity.newBuilder(userKey)
                .set("user_password", DigestUtils.sha512Hex(data.password))//passwords devem sempre ser guardadas de maneira encriptada (hash)
                .set("user_creation_time", Timestamp.now())
                .build();
        //convem avaliar se o user ja existe ou não -> essencial
        datastore.put(user);
        LOG.info("User registered " + data.username);

        return Response.ok().build();
    }


    @POST
    @Path("/v2")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doRegister2(RegisterData data) {

        Key userKey = datastore.newKeyFactory().setKind("User").newKey(data.username);

        if (datastore.get(userKey) != null)
            return Response.status(Status.FORBIDDEN).entity("Username already exists.").build();

        else if (!data.password.equals(data.confirmation))
            return Response.status(Status.FORBIDDEN).entity("Password and confirmation password are diferent").build();

        else {
            Entity user = Entity.newBuilder(userKey)
                    .set("user_password", DigestUtils.sha512Hex(data.password))//passwords devem sempre ser guardadas de maneira encriptada (hash)
                    .set("email", data.email)
                    .set("name", data.name)
                    .set("user_creation_time", Timestamp.now())
                    .build();
            //convem avaliar se o user ja existe ou não -> essencial
            datastore.add(user);//devemos usar o add, porque ja faz a avaliação se o user existe ou não
            LOG.info("User registered " + data.username);

            return Response.ok().build();
        }

    }
}

