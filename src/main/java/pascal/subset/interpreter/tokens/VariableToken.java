package pascal.subset.interpreter.tokens;

public class VariableToken extends Token {
    final String variable;

    public VariableToken(final TokenType type, final String variable, final int line, final int column) {
        this.type = type;
        this.variable = variable;
        this.line = line;
        this.column = column;
    }

    public String variable() {
        return variable;
    }

    @Override
    public String toString() {
        return "VariableToken{" +
                "type=" + type +
                ", line=" + line +
                ", column=" + column +
                ", variable='" + variable + '\'' +
                '}';
    }
}
