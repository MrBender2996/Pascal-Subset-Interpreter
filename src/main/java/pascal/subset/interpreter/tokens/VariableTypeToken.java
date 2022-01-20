package pascal.subset.interpreter.tokens;

import pascal.subset.interpreter.symbol_table.Types;

public class VariableTypeToken extends Token {
    final Types variableType;

    public VariableTypeToken(final TokenType type, final Types variableType, final int line, final int column) {
        this.type = type;
        this.variableType = variableType;
        this.line = line;
        this.column = column;
    }

    public Types variableType() {
        return variableType;
    }

    @Override
    public String toString() {
        return "VariableTypeToken{" +
                "type=" + type +
                ", line=" + line +
                ", column=" + column +
                ", variableType=" + variableType +
                '}';
    }
}
