package pascal.subset.interpreter.ast;

public class VariableDeclarationNode implements Node {
    final VariableNode variable;
    final VariableTypeNode varType;

    public VariableDeclarationNode(final VariableNode variable, final VariableTypeNode varType) {
        this.variable = variable;
        this.varType = varType;
    }

    public VariableNode variable() {
        return variable;
    }

    public VariableTypeNode varType() {
        return varType;
    }

    @Override
    public String toString() {
        return "VariableDeclarationNode{" +
                "variable=" + variable +
                ", varType=" + varType +
                '}';
    }
}
