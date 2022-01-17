package pascal.subset.interpreter.tokens;

public class SemiColonToken extends Token {
    public SemiColonToken(final TokenType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SemiColonToken{" +
                "type=" + type +
                '}';
    }
}
