package minDb.Core.Exceptions;

/**
 * ValidationException
 */
public class ValidationException extends Exception {
    
    public ValidationException(String message) {
        this(message, null);
    }

    public ValidationException(String message, Throwable inner) {
        super(message, inner);
    }    
}