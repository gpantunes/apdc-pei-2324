package pt.unl.fct.di.apdc.firstwebapp.resources;

import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/print_token")
public class PrintTokenResource {

    @POST
    @Path("/v1")
    public Response doPrintToken(@CookieParam("session::apdc") Cookie sessionCookie) {
        if (sessionCookie.getValue() != null) {
            // Invalidate the cookie by setting its value to empty and maxAge to 0

            return Response.ok().entity(sessionCookie.getValue()).build();
        } else {
            return Response.status(Status.BAD_REQUEST).entity("No session cookie found.").build();
        }
    }
}