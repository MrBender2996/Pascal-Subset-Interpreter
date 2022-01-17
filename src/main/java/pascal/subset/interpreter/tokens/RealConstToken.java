package pascal.subset.interpreter.tokens;

public class RealConstToken extends Token {
    float value;

    public RealConstToken(final TokenType type, final float value) {
        this.type = type;
        this.value = value;
    }

    public float value() {
        return value;
    }

    @Override
    public String toString() {
        return "RealToken{" +
                "type=" + type +
                ", value=" + value +
                '}';
    }
}
