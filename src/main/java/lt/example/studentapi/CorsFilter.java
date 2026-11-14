package lt.example.studentapi;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

/** Allows Swagger Editor in a browser to call this local demonstration API. */
@Provider
public final class CorsFilter implements ContainerResponseFilter {
    /** Creates the filter. */
    public CorsFilter() {
    }

    /** {@inheritDoc} */
    @Override
    public void filter(ContainerRequestContext request, ContainerResponseContext response)
            throws IOException {
        response.getHeaders().putSingle("Access-Control-Allow-Origin", "*");
    }
}
