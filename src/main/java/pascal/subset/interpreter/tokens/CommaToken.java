package pascal.subset.interpreter.tokens;

public class CommaToken extends Token {
    public CommaToken(final TokenType type, final int line, final int column) {
        this.type = type;
        this.line = line;
        this.column = column;
    }

    @Override
    public String toString() {
        return "CommaToken{" +
                "type=" + type +
                ", line=" + line +
                ", column=" + column +
                '}';
    }
}
