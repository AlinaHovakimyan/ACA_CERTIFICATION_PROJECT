package dto;

/**
 * Enum for statuses of the response to request
 */
public enum ResponseStatus {
    OK(200),
    MOSTLY_OK(202),
    BAD_REQUEST(400),
    NOT_AUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    NOT_ACCEPTABLE(406),
    FAIL(500);

    private int code;

    private ResponseStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
