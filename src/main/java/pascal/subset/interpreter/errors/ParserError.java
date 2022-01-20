package pascal.subset.interpreter.errors;

import pascal.subset.interpreter.tokens.Token;

public class ParserError extends RuntimeException {
    final ErrorCodes errorCode;
    final Token token;
    final String message;

    public ParserError(final ErrorCodes errorCode, final Token token) {
        this(errorCode, token, "");
    }

    public ParserError(final ErrorCodes errorCode, final Token token, final String message) {
        this.errorCode = errorCode;
        this.token = token;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ParserError{" +
                "errorCode=" + errorCode +
                ", token=" + token +
                ", message='" + message + '\'' +
                '}';
    }
}
