package lt.example.studentapi;

/**
 * A subject studied by a student.
 *
 * @param name subject name
 * @param credits number of credits
 */
public record Subject(String name, int credits) {
    /**
     * Creates a subject.
     *
     * @param name subject name
     * @param credits number of credits
     */
    public Subject {
        if (name == null || name.isBlank() || credits <= 0) {
            throw new IllegalArgumentException("Subject name and positive credits are required");
        }
    }
}
