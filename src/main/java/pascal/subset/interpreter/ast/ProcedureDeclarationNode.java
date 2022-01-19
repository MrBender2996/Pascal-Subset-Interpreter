package pascal.subset.interpreter.ast;

import java.util.ArrayList;
import java.util.List;

public class ProcedureDeclarationNode implements Node {
    final String name;
    final List<ParameterNode> params = new ArrayList<>();
    final BlockNode block;

    public ProcedureDeclarationNode(final String name, final List<ParameterNode> params, final BlockNode block) {
        this.name = name;
        this.block = block;

        if (params != null) {
            this.params.addAll(params);
        }
    }

    public String name() {
        return name;
    }

    public List<ParameterNode> params() {
        return params;
    }

    public BlockNode block() {
        return block;
    }

    boolean hasParams() {
        return params.size() > 0;
    }

    @Override
    public String toString() {
        return "ProcedureNode{" +
                "name='" + name + '\'' +
                ", block=" + block +
                '}';
    }
}
