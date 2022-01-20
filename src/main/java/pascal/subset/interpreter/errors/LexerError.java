package pascal.subset.interpreter.errors;

public class LexerError extends RuntimeException {
    final ErrorCodes errorCode;
    final String message;

    public LexerError(final ErrorCodes errorCode) {
        this(errorCode, "");
    }

    public LexerError(final ErrorCodes errorCode, final String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    @Override
    public String toString() {
        return "LexerError{" +
                "errorCode=" + errorCode +
                ", message='" + message + '\'' +
                '}';
    }
}
