package pascal.subset.interpreter.errors;

public enum ErrorCodes {
    UNEXPECTED_TOKEN("Unexpected token."),
    UNEXPECTED_LEXEME("Unexpected lexeme."),
    ID_NOT_FOUND("Identifier not found."),
    INVALID_PARAMS_COUNT("Invalid parameters count."),
    DUPLICATE_ID("Duplicate id found.");

    final String errorCode;

    ErrorCodes(final String errorCode) {
       this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "ErrorCodes{" +
                "errorCode='" + errorCode + '\'' +
                '}';
    }
}
