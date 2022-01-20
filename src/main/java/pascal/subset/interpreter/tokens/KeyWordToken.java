package pascal.subset.interpreter.tokens;

public class KeyWordToken extends Token {
    final KeyWords keyWord;

    public KeyWordToken(final TokenType type, final KeyWords keyWord, final int line, final int column) {
        this.type = type;
        this.keyWord = keyWord;
        this.line = line;
        this.column = column;
    }

    public KeyWords keyWord() {
        return keyWord;
    }

    @Override
    public String toString() {
        return "KeyWordToken{" +
                "keyWord=" + keyWord +
                ", type=" + type +
                ", line=" + line +
                ", column=" + column +
                '}';
    }
}
