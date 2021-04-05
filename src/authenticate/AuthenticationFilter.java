package authenticate;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.io.IOException;
import java.lang.annotation.ElementType;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.annotation.Priority;
import javax.ws.rs.NameBinding;
import javax.ws.rs.ext.Provider;



@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
	
	private static final String REALM = "example";
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Get the Authorization header from the request
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Validate the Authorization header
//        if (!isTokenBasedAuthentication(authorizationHeader)) {
//            abortWithUnauthorized(requestContext);
//            return;
//        }

        // Extract the token from the Authorization header
        String token = authorizationHeader.substring(AUTHENTICATION_SCHEME.length()).trim();
        System.out.println(token);
        
        //check if token is in users DB
        if(true) {
        	requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("You cannot access this resource").build());
        }
    }
}
