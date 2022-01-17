package pascal.subset.interpreter.ast;


import pascal.subset.interpreter.tokens.Token;
import pascal.subset.interpreter.tokens.VariableToken;

public class VariableNode implements Node {
    final VariableToken token;
    final String name;

    public VariableNode(final VariableToken token) {
        this.token = token;
        this.name = token.variable();
    }

    public Token token() {
        return token;
    }

    public String name() {
        return name;
    }

    @Override
    public String toString() {
        return "VariableNode{" +
                "token=" + token +
                ", name='" + name + '\'' +
                '}';
    }
}
