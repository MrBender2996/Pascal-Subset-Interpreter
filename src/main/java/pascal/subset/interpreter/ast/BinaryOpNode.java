package pascal.subset.interpreter.ast;

import pascal.subset.interpreter.tokens.Token;

public class BinaryOpNode implements Node {
    final Node left;
    final Token operator;
    final Node right;

    public BinaryOpNode(final Node left, final Token operator, final Node right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    public Node left() {
        return left;
    }

    public Token operator() {
        return operator;
    }

    public Node right() {
        return right;
    }

    @Override
    public String toString() {
        return "BinaryOpNode{" +
                "left=" + left +
                ", operator=" + operator +
                ", right=" + right +
                '}';
    }
}
