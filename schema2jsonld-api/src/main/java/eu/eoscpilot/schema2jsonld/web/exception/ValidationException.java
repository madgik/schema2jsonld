package eu.eoscpilot.schema2jsonld.web.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
