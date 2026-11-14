package lt.example.studentapi;

import java.net.URI;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

/** Starts the embedded JAX-RS service on localhost. */
public final class Application {
    /** Public address used by examples and API tools. */
    public static final String BASE_URI = "http://localhost:8081/api/";

    private Application() {
    }

    /**
     * Creates the JAX-RS resource configuration.
     *
     * @param repository data source to inject into the HTTP resource
     * @return Jersey configuration
     */
    public static ResourceConfig config(StudentRepository repository) {
        return new ResourceConfig()
                .register(StudentResource.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(repository).to(StudentRepository.class);
                    }
                })
                .register(JacksonFeature.class)
                .register(CorsFilter.class);
    }

    /**
     * Starts the HTTP server.
     *
     * @param args optional port number (defaults to 8081)
     * @throws Exception if the server cannot start
     */
    public static void main(String[] args) throws Exception {
        int port = args.length == 0 ? 8081 : Integer.parseInt(args[0]);
        String baseUri = "http://localhost:" + port + "/api/";
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(
                URI.create(baseUri), config(new InMemoryStudentRepository()));
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));
        System.out.println("Service: " + baseUri + "students");
        System.out.println("WADL: " + baseUri + "application.wadl");
        Thread.currentThread().join();
    }
}
