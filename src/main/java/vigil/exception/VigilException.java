package vigil.exception;

/**
 * Represents an exception specific to the Vigil application.
 * Thrown when the application encounters invalid user input or data errors.
 */
public class VigilException extends Exception {

    /**
     * Constructs a VigilException with the specified error message.
     *
     * @param message Description of the error.
     */
    public VigilException(String message) {
        super(message);
    }
}