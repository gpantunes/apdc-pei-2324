package pt.unl.fct.di.apdc.firstwebapp.resources;

import javax.ws.rs.CookieParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/logout")
public class LogoutResource {

    @POST
    @Path("/v1")
    public Response doLogout(@CookieParam("session::apdc") String sessionCookie) {
        if (sessionCookie != null) {
            // Invalidate the cookie by setting its value to empty and maxAge to 0
            NewCookie invalidatedCookie = new NewCookie("session::apdc", "", "/", null, "comment", 0, false, true);
            return Response.ok().cookie(invalidatedCookie).build();
        } else {
            return Response.status(Status.BAD_REQUEST).entity("No session cookie found.").build();
        }
    }
}