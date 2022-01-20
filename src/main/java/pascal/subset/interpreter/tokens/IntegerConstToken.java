package pascal.subset.interpreter.tokens;

public class IntegerConstToken extends Token {
    int value;

    public IntegerConstToken(final TokenType type, final int value, final int line, final int column) {
        this.type = type;
        this.value = value;
        this.line = line;
        this.column = column;
    }

    public int value() {
        return value;
    }

    @Override
    public String toString() {
        return "IntegerConstToken{" +
                "value=" + value +
                ", type=" + type +
                ", line=" + line +
                ", column=" + column +
                '}';
    }
}
