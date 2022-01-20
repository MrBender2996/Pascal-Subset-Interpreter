package pascal.subset.interpreter.tokens;

public class ColonToken extends Token {
    public ColonToken(final TokenType type, final int line, final int column) {
        this.type = type;
        this.line = line;
        this.column = column;
    }

    @Override
    public String toString() {
        return "ColonToken{" +
                "type=" + type +
                ", line=" + line +
                ", column=" + column +
                '}';
    }
}
