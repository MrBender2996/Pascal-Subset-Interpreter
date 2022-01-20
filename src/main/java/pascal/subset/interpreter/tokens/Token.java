package pascal.subset.interpreter.tokens;

public abstract class Token {
    TokenType type;
    int line;
    int column;

    public TokenType type() {
        return type;
    }

    public int line() {
       return line;
    }

    public int column() {
        return column;
    }
}
