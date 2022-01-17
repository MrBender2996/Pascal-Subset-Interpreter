package pascal.subset.interpreter.tokens;

public class DotToken extends Token {
    public DotToken(final TokenType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DotToken{" +
                "type=" + type +
                '}';
    }
}
