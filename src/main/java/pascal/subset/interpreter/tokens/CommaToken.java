package pascal.subset.interpreter.tokens;

public class CommaToken extends Token {
    public CommaToken(final TokenType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CommaToken{" +
                "type=" + type +
                '}';
    }
}
