package pascal.subset.interpreter.ast;

import pascal.subset.interpreter.tokens.Token;
import pascal.subset.interpreter.tokens.TokenType;

public class NumberNode implements Node {
    final Token token;

    public NumberNode(final Token token) {
        if (token.type() != TokenType.INTEGER_CONST && token.type() != TokenType.REAL_CONST) {
            throw new IllegalArgumentException(
                    "Number node expected token with type \"INTEGER_CONST\" or \"REAL_CONST\", but received: " + token);
        }

        this.token = token;
    }

    public Token token() {
        return token;
    }

    @Override
    public String toString() {
        return "NumberNode{" +
                "token=" + token +
                '}';
    }
}
