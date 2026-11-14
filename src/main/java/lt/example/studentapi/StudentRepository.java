package lt.example.studentapi;

import java.util.List;
import java.util.Optional;

/** Read-only access to students. */
public interface StudentRepository {
    /**
     * Lists students.
     *
     * @return all students
     */
    List<Student> findAll();

    /**
     * Finds a student by ID.
     *
     * @param id student ID
     * @return matching student, if present
     */
    Optional<Student> findById(int id);
}
