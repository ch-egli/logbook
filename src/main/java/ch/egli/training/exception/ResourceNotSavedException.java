package ch.egli.training.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by christian on 1/25/16.
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ResourceNotSavedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceNotSavedException() {}

    public ResourceNotSavedException(String message) {
        super(message);
    }

    public ResourceNotSavedException(String message, Throwable cause) {
        super(message, cause);
    }
}
