package pascal.subset.interpreter;

import pascal.subset.interpreter.symbol_table.Types;
import pascal.subset.interpreter.tokens.*;

public class Lexer {
    public final char EOF_CHAR = Character.MAX_VALUE;

    final String text;

    int crtPos = 0;

    public Lexer(final String text) {
        this.text = text;
    }

    Token nextToken() {
        if (crtPos >= text.length()) {
            return new EOFToken(TokenType.EOF);
        }

        if (skipSpacesAndEOLs()) {
            return new EOFToken(TokenType.EOF);
        }

        // todo decide how to process FLOAT and INT division???
        char crtChar = text.charAt(crtPos);
        if (crtChar == '+' || crtChar == '-' || crtChar == '/' || crtChar == '*' || crtChar == '(' || crtChar == ')') {
            advance();
            return new OperatorToken(TokenType.OPERATOR, crtChar);
        }

        if (crtChar == '{') {
            advance();
            skipComment();
            return nextToken(); // as comments are skipped lexer can continue tokenizing
        }

        if (isDigit(crtChar)) {
            final Token token = searchForNumber();
            if (token != null) {
                return token;
            }
        }

        if (crtChar == ':' && peek() == '=') {
            advance();
            advance();
            return new AssignToken(TokenType.ASSIGN);
        }

        if (crtChar == ':') {
            advance();
            return new ColonToken(TokenType.COLON);
        }

        if (crtChar == ',') {
            advance();
            return new CommaToken(TokenType.COMMA);
        }

        if (isAlphaNumeric(crtChar)) {
            return nextIdentifier();
        }

        if (crtChar == ';') {
            advance();
            return new SemiColonToken(TokenType.SEMICOLON);
        }

        if (crtChar == '.') {
            advance();
            return new DotToken(TokenType.DOT);
        }

        throw new IllegalArgumentException("Error parsing input text");
    }

    Token nextIdentifier() {
        final StringBuilder sb = new StringBuilder();

        while (!isSpaceOrEol(text.charAt(crtPos)) && isAlphaNumeric(text.charAt(crtPos))) {
            sb.append(text.charAt(crtPos++));
        }

        final String result = sb.toString();
        if (KeyWords.isKeyWord(result)) {
            return new KeyWordToken(TokenType.KEYWORD, KeyWords.valueOf(result));
        } else if (Types.isVarType(result)) {
            return new VariableTypeToken(TokenType.VARIABLE_TYPE, Types.valueOf(result));
        } else {
            return new VariableToken(TokenType.VARIABLE, result);
        }
    }

    boolean skipSpacesAndEOLs() {
        char crtChar = text.charAt(crtPos);

        while (isSpaceOrEol(crtChar)) {
            if (++crtPos >= text.length()) {
                return true;
            }

            crtChar = text.charAt(crtPos);
        }

        return false;
    }

    void skipComment() {
        while (advance() != '}') {
            //
        }

        advance(); // move position from '}' to the next char
    }

    boolean isAlphaNumeric(final char c) {
        return c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9';
    }

    boolean isSpaceOrEol(final char c) {
        return c == ' ' || c == '\n';
    }

    char peek() {
        final int nextPos = crtPos + 1;
        if (nextPos >= text.length()) {
            return EOF_CHAR;
        }

        return text.charAt(nextPos);
    }

    char advance() {
        crtPos++;
        if (crtPos >= text.length()) {
            return EOF_CHAR;
        }

        return text.charAt(crtPos);
    }

    boolean isDigit(final char c) {
        return c >= '0' && c <= '9';
    }

    Token searchForNumber() {
        final StringBuilder sb = new StringBuilder();
        for (char c = text.charAt(crtPos); isDigit(c) && c != EOF_CHAR; c = advance()) {
            sb.append(c);
        }

        if (sb.length() == 0) {
            return null;
        }

        if (text.charAt(crtPos) == '.') {
            sb.append('.');
            for (char c = advance(); isDigit(c) && c != EOF_CHAR; c = advance()) {
                sb.append(c);
            }

            return new RealConstToken(TokenType.REAL_CONST, Float.parseFloat(sb.toString()));
        } else {
            return new IntegerConstToken(TokenType.INTEGER_CONST, Integer.parseInt(sb.toString()));
        }
    }
}
