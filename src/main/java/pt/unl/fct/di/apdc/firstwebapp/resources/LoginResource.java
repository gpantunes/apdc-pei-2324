package pt.unl.fct.di.apdc.firstwebapp.resources;

import java.util.UUID;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.appengine.repackaged.org.apache.commons.codec.digest.DigestUtils;
import com.google.cloud.Timestamp;
import com.google.cloud.datastore.*;
import com.google.gson.Gson;

import pt.unl.fct.di.apdc.firstwebapp.authentication.SignatureUtils;
import pt.unl.fct.di.apdc.firstwebapp.util.AuthToken;
import pt.unl.fct.di.apdc.firstwebapp.util.LoginData;


@Path("/login")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class LoginResource {

    private static final Logger LOG = Logger.getLogger(LoginResource.class.getName());
    private final Gson g = new Gson();

    private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    private final KeyFactory userKeyFactory = datastore.newKeyFactory().setKind("User");

    private static final String key = "dhsjfhndkjvnjdsd";

    public LoginResource() {}

    @SuppressWarnings("exports")
    @POST
    @Path("/v1")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doLogin(LoginData data) {
        LOG.fine("Login attempt by user: " + data.username);

        Key userKey = userKeyFactory.newKey(data.username);
        Entity user = datastore.get(userKey);

        LOG.info(user.getString("role") + " " + user.getString("username"));

        if(user != null) {
            String hashedPWD = user.getString("password");
            String role = user.getString("role");

            if(hashedPWD.equals((DigestUtils.sha512Hex(data.password))) && user.getBoolean("activation_state")){

                String id = UUID.randomUUID().toString();
                long currentTime = System.currentTimeMillis();
                String fields = data.username+"."+ id +"."+role+"."+currentTime+"."+0*601000*6*2;

                String signature = SignatureUtils.calculateHMac(key, fields);

                if(signature == null) {
                    return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error while signing token. See logs.").build();
                }

                String value =  fields + "." + signature;
                NewCookie cookie = new NewCookie("session::apdc", value, "/", null, "comment", 1000*60*60*2, false, false);

                return Response.ok().cookie(cookie).build();

                //AuthToken at = new AuthToken(data.username, role);
                //return Response.ok(g.toJson(at)).build();
            }
        }
        return Response.status(Status.BAD_REQUEST).entity("Incorrect username or password.").build();
    }

    @GET
    @Path("/user")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getLoginTime(LoginData data) {
        AuthToken at = new AuthToken(data.username, "user");
        return Response.ok(g.toJson(at)).build();
    }
}
