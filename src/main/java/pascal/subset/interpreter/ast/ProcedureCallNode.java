package pascal.subset.interpreter.ast;

import java.util.ArrayList;
import java.util.List;

public class ProcedureCallNode implements Node {
    final String name;
    final List<Node> actualParams = new ArrayList<>();

    public ProcedureCallNode(final String name, final List<Node> actualParams) {
        this.name = name;
        if (actualParams != null) {
            this.actualParams.addAll(actualParams);
        }
    }

    public String name() {
        return name;
    }

    public List<Node> actualParams() {
        return actualParams;
    }

    @Override
    public String toString() {
        return "ProcedureCallNode{" +
                "name='" + name + '\'' +
                ", actualParams=" + actualParams +
                '}';
    }
}
