package pascal.subset.interpreter.ast;


import pascal.subset.interpreter.symbol_table.Types;
import pascal.subset.interpreter.tokens.TokenType;

public class VariableTypeNode implements Node {
    final TokenType token;
    final Types varType;

    public VariableTypeNode(final TokenType token, final Types varType) {
        if (varType != Types.INTEGER && varType!= Types.REAL) {
            throw new IllegalArgumentException("Type node expected var type to be \"INTEGER\" or \"REAL\", but " +
                    "received: " + varType);
        }

        this.token = token;
        this.varType = varType;
    }

    public TokenType token() {
        return token;
    }

    public Types variableType() {
        return varType;
    }

    @Override
    public String toString() {
        return "VariableTypeNode{" +
                "token=" + token +
                ", varType=" + varType +
                '}';
    }
}
