package pascal.subset.interpreter;

import pascal.subset.interpreter.errors.ErrorCodes;
import pascal.subset.interpreter.errors.LexerError;
import pascal.subset.interpreter.errors.ParserError;
import pascal.subset.interpreter.symbol_table.Types;
import pascal.subset.interpreter.tokens.*;

public class Lexer {
    public final char EOF_CHAR = Character.MAX_VALUE;

    final String text;

    int crtPos = 0;
    int line = 1;
    int column = 1;

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
            final OperatorToken operatorToken = new OperatorToken(TokenType.OPERATOR, crtChar, line, column);
            advance();
            return operatorToken;
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
            final AssignToken assignToken = new AssignToken(TokenType.ASSIGN, line, column);
            advance();
            advance();
            return assignToken;
        }

        if (crtChar == ':') {
            final ColonToken colonToken = new ColonToken(TokenType.COLON, line, column);
            advance();
            return colonToken;
        }

        if (crtChar == ',') {
            final CommaToken commaToken = new CommaToken(TokenType.COMMA, line, column);
            advance();
            return commaToken;
        }

        if (isAlphaNumeric(crtChar)) {
            return nextIdentifier();
        }

        if (crtChar == ';') {
            final SemiColonToken semiColonToken = new SemiColonToken(TokenType.SEMICOLON, line, column);
            advance();
            return semiColonToken;
        }

        if (crtChar == '.') {
            final DotToken dotToken = new DotToken(TokenType.DOT, line, column);
            advance();
            return dotToken;
        }

        throw formError();
    }

    Token nextIdentifier() {
        final StringBuilder sb = new StringBuilder();

        final int lexemeStartColumn = column;
        while (!isSpaceOrEol(text.charAt(crtPos)) && isAlphaNumeric(text.charAt(crtPos))) {
            sb.append(text.charAt(crtPos));
            advance();
        }

        final String result = sb.toString();
        if (KeyWords.isKeyWord(result)) {
            return new KeyWordToken(TokenType.KEYWORD, KeyWords.valueOf(result), line, lexemeStartColumn);
        } else if (Types.isVarType(result)) {
            return new VariableTypeToken(TokenType.VARIABLE_TYPE, Types.valueOf(result), line, lexemeStartColumn);
        } else {
            return new VariableToken(TokenType.VARIABLE, result, line, lexemeStartColumn);
        }
    }

    boolean skipSpacesAndEOLs() {
        char crtChar = text.charAt(crtPos);

        while (isSpaceOrEol(crtChar)) {
            crtChar = advance();

            if(crtChar == EOF_CHAR) {
                return true;
            }
        }

        return false;
    }

    void skipComment() {
        while (advance() != '}') {
            //
        }

        advance(); // move position from '}' to the next char
    }

    char currentChar() {
        return text.charAt(crtPos);
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
        if (text.charAt(crtPos) == '\n') {
            line++;
            column = 0;
        }

        crtPos++;
        column++;
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

        final int lexemeStatColumn = column;
        if (text.charAt(crtPos) == '.') {
            sb.append('.');
            for (char c = advance(); isDigit(c) && c != EOF_CHAR; c = advance()) {
                sb.append(c);
            }

            return new RealConstToken(TokenType.REAL_CONST, Float.parseFloat(sb.toString()), line, lexemeStatColumn);
        } else {
            return new IntegerConstToken(TokenType.INTEGER_CONST, Integer.parseInt(sb.toString()), line, lexemeStatColumn);
        }
    }

    private LexerError formError() throws LexerError {
        final String message = String.format("Lexer error on '%c' line: %d column: %d",
                text.charAt(crtPos),
                line,
                column);

       return new LexerError(ErrorCodes.UNEXPECTED_LEXEME, message);
    }
}
