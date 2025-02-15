package pookie.exception;

/**
 * Represents the base exception class for handling errors in the Pookie application.
 * <p>
 * This class extends {@link Exception} and provides specific subclasses for different types of errors,
 * such as missing descriptions, missing keywords, and invalid task indices.
 * </p>
 */
public class PookieException extends Exception {

    /**
     * Constructs a {@code PookieException} with the specified error message.
     *
     * @param message The error message associated with this exception.
     */
    public PookieException(String message) {
        super(message);
    }

    /**
     * Represents an exception for empty task descriptions.
     * <p>
     * This exception is thrown when a user tries to create a task without providing a description.
     * </p>
     */
    public static class EmptyDescriptionException extends PookieException {
        /**
         * Constructs an {@code EmptyDescriptionException} with the specified error message.
         *
         * @param message The error message associated with this exception.
         */
        public EmptyDescriptionException(String message) {
            super(message);
        }
    }

    /**
     * Represents an exception for missing required keywords in task inputs.
     * <p>
     * This exception is thrown when a task command is missing necessary keywords, such as "/by" for deadlines.
     * </p>
     */
    public static class MissingKeywordException extends PookieException {
        public MissingKeywordException(String message) {
            super(message);
        }
    }
}
