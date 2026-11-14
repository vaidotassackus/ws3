package lt.example.studentapi;

import java.util.List;

/**
 * A student and the subjects associated with that student.
 *
 * @param id unique student ID
 * @param name student name
 * @param average grade average
 * @param active whether the student is active
 * @param group one-letter group code
 * @param subjects subjects studied by the student
 */
public record Student(int id, String name, float average, boolean active, char group,
                      List<Subject> subjects) {
    /**
     * Creates a student with an immutable subject list.
     *
     * @param id unique student ID
     * @param name student name
     * @param average grade average
     * @param active whether the student is active
     * @param group one-letter group code
     * @param subjects subjects studied by the student
     */
    public Student {
        if (id <= 0 || name == null || name.isBlank() || !Float.isFinite(average)
                || average < 0 || average > 10 || !Character.isLetter(group)
                || subjects == null) {
            throw new IllegalArgumentException("Invalid student data");
        }
        subjects = List.copyOf(subjects);
    }
}
