package pascal.subset.interpreter.tokens;

public class AssignToken extends Token {
    public AssignToken(final TokenType type, final int line, final int column) {
        this.type = type;
        this.line = line;
        this.column = column;
    }

    @Override
    public String toString() {
        return "AssignToken{" +
                "type=" + type +
                ", line=" + line +
                ", column=" + column +
                '}';
    }
}
