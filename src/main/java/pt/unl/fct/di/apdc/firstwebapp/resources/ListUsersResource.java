package pt.unl.fct.di.apdc.firstwebapp.resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.cloud.datastore.StructuredQuery.Filter;
import com.google.cloud.datastore.*;
import com.google.gson.Gson;

import pt.unl.fct.di.apdc.firstwebapp.util.PasswordChangeData;


@Path("/list_users")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ListUsersResource {

    private static final Logger LOG = Logger.getLogger(LoginResource.class.getName());
    private final Gson g = new Gson();

    private final Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

    @POST
    @Path("/v1")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doListUsers(@CookieParam("session::apdc") Cookie sessionCookie, PasswordChangeData data) {


        String[] params = sessionCookie.getValue().split("\\.");

        Filter queryFilter = generateRoleFilter(params[2]);
            
        Query<Entity> query = Query.newEntityQueryBuilder().setKind("User").setFilter(queryFilter).build();
        QueryResults<Entity> userList = datastore.run(query);

        List<String> usernameList = new ArrayList();
        userList.forEachRemaining((user -> {
            usernameList.add(user.getString("username"));
        }));

        return Response.ok(g.toJson(usernameList)).build();

    }
    

    private Filter generateRoleFilter(String loggedInUserRole) {
        Filter roleFilter = null;
        Filter profileFilter;
        Filter activationFilter;


        // If the logged-in user is GA, allow listing GA, GBO, and USER users
        if ("GA".equals(loggedInUserRole)) {
            roleFilter = StructuredQuery.PropertyFilter.in("role", ListValue.of(StringValue.of("GA"), StringValue.of("GBO"), StringValue.of("USER")));
        }
        // If the logged-in user is GBO, allow listing GBO and USER users
        else if ("GBO".equals(loggedInUserRole)) {
            roleFilter = StructuredQuery.PropertyFilter.in("role", ListValue.of(StringValue.of("GBO"), StringValue.of("USER")));
        }
        // If the logged-in user is USER, allow listing only USER users
        else if ("USER".equals(loggedInUserRole)) {
            roleFilter = StructuredQuery.PropertyFilter.eq("role", "USER");
            profileFilter = StructuredQuery.PropertyFilter.eq("public_profile", true);
            activationFilter = StructuredQuery.PropertyFilter.eq("activation_state", true);
            roleFilter = StructuredQuery.CompositeFilter.and(roleFilter, profileFilter, activationFilter);
        }

        return roleFilter;
    }
}
