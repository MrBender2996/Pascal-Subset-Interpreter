package pascal.subset.interpreter.ast;

import pascal.subset.interpreter.symbol_table.ProcedureSymbol;

import java.util.ArrayList;
import java.util.List;

public class ProcedureCallNode implements Node {
    final String name;
    final List<Node> actualParams = new ArrayList<>();

    ProcedureSymbol symbol;

    public ProcedureCallNode(final String name, final List<Node> actualParams) {
        this.name = name;
        if (actualParams != null) {
            this.actualParams.addAll(actualParams);
        }

        symbol = null;
    }

    public String name() {
        return name;
    }

    public List<Node> actualParams() {
        return actualParams;
    }

    public boolean hasSymbol() {
        return symbol != null;
    }

    public ProcedureSymbol symbol() {
        return symbol;
    }

    public void symbol(final ProcedureSymbol symbol) {
       this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "ProcedureCallNode{" +
                "name='" + name + '\'' +
                ", actualParams=" + actualParams +
                '}';
    }
}
