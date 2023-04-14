package directories.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This @code{AdapterExceptionHandler} class handles exceptions.
 */

@ControllerAdvice
public class RegionExceptionHandler {

    private static final Logger LOGGER = LogManager.getLogger(RegionExceptionHandler.class);
    private static final String EMPTY_ID = "ID can't be zero and must be Long";

    /**
     * Handles response exception.
     *
     * @param e - exception thrown.
     * @return response entity with error code
     */
    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<String> handleResponseException(Exception e) {
        LOGGER.error("Database response error", e);

        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_GATEWAY);
    }
    /**
     * Handles request exception.
     *
     * @param e - exception thrown.
     * @return response entity with error code
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleMessageNotReadableException(Exception e) {
        LOGGER.error("Request error", e);

        return new ResponseEntity<>(EMPTY_ID, HttpStatus.BAD_GATEWAY);
    }

}
