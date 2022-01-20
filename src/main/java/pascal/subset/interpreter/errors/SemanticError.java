package pascal.subset.interpreter.errors;

import pascal.subset.interpreter.tokens.Token;

public class SemanticError extends RuntimeException {
    final ErrorCodes errorCode;
    final Token token;
    final String message;

    public SemanticError(final ErrorCodes errorCode, final Token token) {
        this(errorCode, token, "");
    }

    public SemanticError(final ErrorCodes errorCode, final Token token, final String message) {
        this.errorCode = errorCode;
        this.token = token;
        this.message = message;
    }

    @Override
    public String toString() {
        return "SemanticError{" +
                "errorCode=" + errorCode +
                ", token=" + token +
                ", message='" + message + '\'' +
                '}';
    }
}
