package pt.unl.fct.di.apdc.firstwebapp.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

import pt.unl.fct.di.apdc.firstwebapp.util.*;

@Path("/login")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class LoginResource {

    //Logger object
    private static final Logger LOG = Logger.getLogger(LoginResource.class.getName());


    public LoginResource() {} //Construtor vaziu


    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doLogin(LoginData data){
        LOG.fine("Attempt to login user: " + data.userName);
        return Response.ok().build();

    }
}
