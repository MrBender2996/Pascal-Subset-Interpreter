package pascal.subset.interpreter.tokens;

public class KeyWordToken extends Token {
    final KeyWords keyWord;

    public KeyWordToken(final TokenType type, final KeyWords keyWord) {
        this.type = type;
        this.keyWord = keyWord;
    }

    public KeyWords keyWord() {
        return keyWord;
    }

    @Override
    public String toString() {
        return "KeyWordToken{" +
                "keyWord=" + keyWord +
                '}';
    }
}
