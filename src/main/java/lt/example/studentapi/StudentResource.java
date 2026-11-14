package lt.example.studentapi;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;
import java.util.Objects;

/** HTTP endpoints for reading student data. */
@Path("students")
@Produces(MediaType.APPLICATION_JSON)
public final class StudentResource {
    private final StudentRepository repository;

    /**
     * Creates a resource with its data source supplied from outside.
     *
     * @param repository student data source
     */
    @Inject
    public StudentResource(StudentRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    /**
     * Lists all students.
     *
     * @return HTTP 200 and a JSON array
     */
    @GET
    public Response list() {
        return Response.ok(repository.findAll()).build();
    }

    /**
     * Gets one student.
     *
     * @param id student ID
     * @return HTTP 200 with a student, or HTTP 404
     */
    @GET
    @Path("{id}")
    public Response getById(@PathParam("id") int id) {
        return repository.findById(id)
                .map(student -> Response.ok(student).build())
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND).build());
    }
}
