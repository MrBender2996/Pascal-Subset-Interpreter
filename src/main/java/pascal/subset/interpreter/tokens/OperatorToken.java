package pascal.subset.interpreter.tokens;

public class OperatorToken extends Token {
    char operator;

    public OperatorToken(final TokenType type, final char operator, final int line, final int column) {
        this.type = type;
        this.operator = operator;
        this.line = line;
        this.column = column;
    }

    public char operator() {
       return operator;
    }

    @Override
    public String toString() {
        return "OperatorToken{" +
                "operator=" + operator +
                ", type=" + type +
                ", line=" + line +
                ", column=" + column +
                '}';
    }
}
