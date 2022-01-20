package pascal.subset.interpreter.tokens;

public class SemiColonToken extends Token {
    public SemiColonToken(final TokenType type, final int line, final int column) {
        this.type = type;
        this.line = line;
        this.column = column;
    }

    @Override
    public String toString() {
        return "SemiColonToken{" +
                "type=" + type +
                ", line=" + line +
                ", column=" + column +
                '}';
    }
}
