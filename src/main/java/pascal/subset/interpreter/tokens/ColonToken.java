package pascal.subset.interpreter.tokens;

public class ColonToken extends Token {
    public ColonToken(final TokenType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ColonToken{" +
                "type=" + type +
                '}';
    }
}
