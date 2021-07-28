package instances;

/**
 * Enum for the status of the Applicant taking the course
 */
public enum Status {
    ON_HOLD("ON_HOLD"),
    IN_PROGRESS("IN_PROGRESS"),
    COMPLETED("COMPLETED");

    private final String value;

    Status(final String newValue) {
        value = newValue;
    }

    public String getValue() {
        return value;
    }
};