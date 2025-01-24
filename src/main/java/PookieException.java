public class PookieException extends Exception {
    public PookieException(String message) {
        super(message);
    }

    // Subclass for empty description errors
    public static class EmptyDescriptionException extends PookieException {
        public EmptyDescriptionException(String message) {
            super(message);
        }
    }

    // Subclass for missing keyword errors
    public static class MissingKeywordException extends PookieException {
        public MissingKeywordException(String message) {
            super(message);
        }
    }

    // Subclass for invalid task index errors
    public static class InvalidTaskIndexException extends PookieException {
        public InvalidTaskIndexException(String message) {
            super(message);
        }
    }
}
