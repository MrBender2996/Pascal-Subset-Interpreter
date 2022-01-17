package pascal.subset.interpreter.tokens;

public class OperatorToken extends Token {
    char operator;

    public OperatorToken(final TokenType type, final char operator) {
        this.type = type;
        this.operator = operator;
    }

    public char operator() {
       return operator;
    }

    @Override
    public String toString() {
        return "OperatorToken{" +
                "type=" + type +
                ", operator=" + operator +
                '}';
    }
}
