package pascal.subset.interpreter.symbol_table;

import pascal.subset.interpreter.ast.BlockNode;

import java.util.ArrayList;
import java.util.List;

public class ProcedureSymbol extends Symbol {
    final List<VarSymbol> params = new ArrayList<>();
    BlockNode blockNode;

    public ProcedureSymbol(final String name) {
        super(name);
    }

    public ProcedureSymbol(final String name, final Symbol symbol) {
        super(name, symbol);
    }

    public ProcedureSymbol(final String name, final List<VarSymbol> params) {
        super(name);
        this.params.addAll(params);
    }

    public String name() {
        return name;
    }

    public List<VarSymbol> params() {
        return params;
    }

    public void blockNode(final BlockNode node) {
        this.blockNode = node;
    }

    public boolean hasNode() {
        return blockNode != null;
    }

    public BlockNode blockNode() {
        return blockNode;
    }

    @Override
    public String toString() {
        return "ProcedureSymbol{" +
                "name='" + name + '\'' +
                ", params=" + params +
                '}';
    }
}
