package lt.example.studentapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

class StudentResourceTest {
    private final StudentResource resource = new StudentResource(new InMemoryStudentRepository());

    @Test
    void listsStudents() {
        try (Response response = resource.list()) {
            assertEquals(200, response.getStatus());
        }
    }

    @Test
    void returnsStudentOrNotFound() {
        try (Response found = resource.getById(1);
             Response missing = resource.getById(999)) {
            assertEquals(200, found.getStatus());
            assertEquals(1, ((Student) found.getEntity()).id());
            assertEquals(404, missing.getStatus());
        }
    }

    @Test
    void requiresRepository() {
        assertThrows(NullPointerException.class, () -> new StudentResource(null));
    }
}
