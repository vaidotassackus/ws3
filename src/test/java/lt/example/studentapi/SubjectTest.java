package lt.example.studentapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class SubjectTest {
    @Test
    void storesFields() {
        Subject subject = new Subject("Matematika", 6);
        assertEquals("Matematika", subject.name());
        assertEquals(6, subject.credits());
    }

    @Test
    void rejectsInvalidFields() {
        assertThrows(IllegalArgumentException.class, () -> new Subject("", 6));
        assertThrows(IllegalArgumentException.class, () -> new Subject("Test", 0));
    }
}
