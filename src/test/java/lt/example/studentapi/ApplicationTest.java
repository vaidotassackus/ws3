package lt.example.studentapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.junit.jupiter.api.Test;

class ApplicationTest {
    @Test
    void servesJsonAndWadl() throws Exception {
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(
                URI.create("http://localhost:0/api/"),
                Application.config(new InMemoryStudentRepository()));
        try {
            int port = server.getListeners().iterator().next().getPort();
            String base = "http://localhost:" + port + "/api/";
            try (HttpClient client = HttpClient.newHttpClient()) {
                HttpResponse<String> students = client.send(
                        HttpRequest.newBuilder(URI.create(base + "students")).build(),
                        HttpResponse.BodyHandlers.ofString());
                assertEquals(200, students.statusCode());
                assertTrue(students.body().contains("Aistė"));
                assertTrue(students.body().contains("subjects"));
                assertEquals("*", students.headers().firstValue("Access-Control-Allow-Origin").orElseThrow());

                HttpResponse<String> wadl = client.send(
                        HttpRequest.newBuilder(URI.create(base + "application.wadl")).build(),
                        HttpResponse.BodyHandlers.ofString());
                assertEquals(200, wadl.statusCode());
                assertTrue(wadl.body().contains("students"));
            }
        } finally {
            server.shutdownNow();
        }
    }
}
