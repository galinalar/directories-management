package directories.exception;

/**
 * Custom exception for database result error.
 */
public class ResponseException extends RuntimeException {

    /**
     * Creates exception.
     *
     * @param message of exception
     */
    public ResponseException(String message) {
        super(message);
    }
}
