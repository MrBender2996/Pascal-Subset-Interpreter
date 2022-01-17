package pascal.subset.interpreter.tokens;

import pascal.subset.interpreter.symbol_table.Types;

public class VariableTypeToken extends Token {
    final Types variableType;

    public VariableTypeToken(final TokenType type, final Types variableType) {
        this.type = type;
        this.variableType = variableType;
    }

    public Types variableType() {
        return variableType;
    }

    @Override
    public String toString() {
        return "VariableTypeToken{" +
                "variableType=" + variableType +
                '}';
    }
}
