package pascal.subset.interpreter.tokens;

public class DotToken extends Token {
    public DotToken(final TokenType type, final int line, final int column) {
        this.type = type;
        this.line = line;
        this.column = column;
    }

    @Override
    public String toString() {
        return "DotToken{" +
                "type=" + type +
                ", line=" + line +
                ", column=" + column +
                '}';
    }
}
