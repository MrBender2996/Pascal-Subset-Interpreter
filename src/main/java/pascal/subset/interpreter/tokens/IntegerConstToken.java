package pascal.subset.interpreter.tokens;

public class IntegerConstToken extends Token {
    int value;

    public IntegerConstToken(final TokenType type, final int value) {
        this.type = type;
        this.value = value;
    }

    public int value() {
        return value;
    }

    @Override
    public String toString() {
        return "NumberToken{" +
                "type=" + type +
                ", value=" + value +
                '}';
    }
}
