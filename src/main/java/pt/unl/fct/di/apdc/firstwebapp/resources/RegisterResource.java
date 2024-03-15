package pt.unl.fct.di.apdc.firstwebapp.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

import pt.unl.fct.di.apdc.firstwebapp.util.*;

@Path("/register")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class RegisterResource {

    //Logger object
    private static final Logger LOG = Logger.getLogger(LoginResource.class.getName());

    public RegisterResource() {} //Construtor vaziu


    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doRegister(LoginData data){
        LOG.fine("Attempt to register user: " + data.userName);
        return Response.ok().build();
    }
}
