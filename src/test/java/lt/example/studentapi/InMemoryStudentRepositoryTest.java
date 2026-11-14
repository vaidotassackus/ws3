package lt.example.studentapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class InMemoryStudentRepositoryTest {
    private final StudentRepository repository = new InMemoryStudentRepository();

    @Test
    void findsStudents() {
        assertEquals(2, repository.findAll().size());
        assertEquals("Aistė", repository.findById(1).orElseThrow().name());
        assertTrue(repository.findById(999).isEmpty());
    }
}
