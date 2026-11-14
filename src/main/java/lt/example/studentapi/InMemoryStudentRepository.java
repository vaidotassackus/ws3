package lt.example.studentapi;

import java.util.List;
import java.util.Optional;

/** Small in-memory data source for this demonstration. */
public final class InMemoryStudentRepository implements StudentRepository {
    /** Creates a repository containing the example students. */
    public InMemoryStudentRepository() {
    }

    private final List<Student> students = List.of(
            new Student(1, "Aistė", 8.7f, true, 'A',
                    List.of(new Subject("Matematika", 6), new Subject("Programavimas", 8))),
            new Student(2, "Jonas", 7.5f, false, 'B',
                    List.of(new Subject("Tinklai", 5))));

    /** {@inheritDoc} */
    @Override
    public List<Student> findAll() {
        return students;
    }

    /** {@inheritDoc} */
    @Override
    public Optional<Student> findById(int id) {
        return students.stream().filter(student -> student.id() == id).findFirst();
    }
}
