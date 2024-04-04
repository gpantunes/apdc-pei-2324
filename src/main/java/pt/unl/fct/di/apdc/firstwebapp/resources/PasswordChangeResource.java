package pt.unl.fct.di.apdc.firstwebapp.resources;

import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
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
import pt.unl.fct.di.apdc.firstwebapp.util.PasswordChangeData;


@Path("/password_change")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class PasswordChangeResource {

    private static final Logger LOG = Logger.getLogger(LoginResource.class.getName());
    private final Gson g = new Gson();

    private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    private final KeyFactory userKeyFactory = datastore.newKeyFactory().setKind("User");

    private static final String key = "dhsjfhndkjvnjdsd";


    @POST
    @Path("/v1")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doPasswordChange(@CookieParam("session::apdc") Cookie sessionCookie, PasswordChangeData data) {

        String[] params = sessionCookie.getValue().split("\\.");

        LOG.fine("Login attempt by user: " + sessionCookie.getValue());

        //return Response.ok("####### Cookie value " + sessionCookie.getValue() + " params length " + params[0]).build();

        Transaction txn = datastore.newTransaction();
        try {
            Key userKey = datastore.newKeyFactory().setKind("User").newKey(params[0]);
            Entity user = txn.get(userKey);

            if(user == null) {
                txn.rollback();
                return Response.status(Status.BAD_REQUEST).entity("User doesn't exist.").build(); }
            else {
                Entity.Builder tempUser = Entity.newBuilder(user);
                tempUser.set("password", DigestUtils.sha512Hex(data.newPassword));

                Entity update = tempUser.build();
                txn.update(update);
                LOG.info("Password Changed" + params[0]);
                txn.commit();
                return Response.ok("Password changed").build();
            }
        } finally {
            if (txn.isActive()) {
                txn.rollback();
            }
        }

    }

}
