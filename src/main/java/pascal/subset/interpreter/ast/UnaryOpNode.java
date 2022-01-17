package pascal.subset.interpreter.ast;

import pascal.subset.interpreter.tokens.Token;

public class UnaryOpNode implements Node {
    final Token operator;
    final Node expression;

    public UnaryOpNode(final Token operator, final Node expression) {
        this.operator = operator;
        this.expression = expression;
    }

    public Token operator() {
        return operator;
    }

    public Node expression() {
        return expression;
    }

    @Override
    public String toString() {
        return "UnaryOpNode{" +
                "operator=" + operator +
                ", expression=" + expression +
                '}';
    }
}
