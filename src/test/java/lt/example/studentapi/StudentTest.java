package lt.example.studentapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class StudentTest {
    @Test
    void storesDifferentTypesAndCopiesSubjects() {
        List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject("Tinklai", 5));
        Student student = new Student(1, "Aistė", 8.7f, true, 'A', subjects);
        subjects.clear();
        assertEquals(1, student.id());
        assertEquals("Aistė", student.name());
        assertEquals(8.7f, student.average());
        assertEquals(true, student.active());
        assertEquals('A', student.group());
        assertEquals(1, student.subjects().size());
    }

    @Test
    void rejectsInvalidData() {
        assertThrows(IllegalArgumentException.class,
                () -> new Student(0, "Test", 5f, true, 'A', List.of()));
        assertThrows(IllegalArgumentException.class,
                () -> new Student(1, "Test", Float.NaN, true, 'A', List.of()));
    }
}
