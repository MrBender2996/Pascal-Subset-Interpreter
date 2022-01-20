package pascal.subset.interpreter.tokens;

public class RealConstToken extends Token {
    float value;

    public RealConstToken(final TokenType type, final float value, final int line, final int column) {
        this.type = type;
        this.value = value;
        this.line = line;
        this.column = column;
    }

    public float value() {
        return value;
    }

    @Override
    public String toString() {
        return "RealConstToken{" +
                "value=" + value +
                ", type=" + type +
                ", line=" + line +
                ", column=" + column +
                '}';
    }
}
