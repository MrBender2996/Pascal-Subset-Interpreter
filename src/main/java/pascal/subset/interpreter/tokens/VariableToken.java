package pascal.subset.interpreter.tokens;

public class VariableToken extends Token {
    final String variable;

    public VariableToken(final TokenType type, final String variable) {
        this.type = type;
        this.variable = variable;
    }

    public String variable() {
        return variable;
    }

    @Override
    public String toString() {
        return "VarToken{" +
                "variable='" + variable + '\'' +
                '}';
    }
}
