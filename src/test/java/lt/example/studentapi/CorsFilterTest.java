package lt.example.studentapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.core.MultivaluedHashMap;
import org.junit.jupiter.api.Test;

class CorsFilterTest {
    @Test
    void allowsBrowserRequests() throws Exception {
        ContainerResponseContext response = mock(ContainerResponseContext.class);
        MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
        when(response.getHeaders()).thenReturn(headers);
        new CorsFilter().filter(mock(ContainerRequestContext.class), response);
        assertEquals("*", headers.getFirst("Access-Control-Allow-Origin"));
    }
}
