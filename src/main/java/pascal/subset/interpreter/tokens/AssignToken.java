package pascal.subset.interpreter.tokens;

public class AssignToken extends Token {
    public AssignToken(final TokenType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "AssignToken{" +
                "type=" + type +
                '}';
    }
}
