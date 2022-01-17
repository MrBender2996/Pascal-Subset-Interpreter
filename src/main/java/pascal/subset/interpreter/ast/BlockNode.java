package pascal.subset.interpreter.ast;

import java.util.ArrayList;
import java.util.List;

public class BlockNode implements Node {
    final List<Node> declarations = new ArrayList<>();
    final CompoundNode compoundStatement;

    public BlockNode(final List<Node> declarations,
            final CompoundNode compoundStatement) {
        this.declarations.addAll(declarations) ;
        this.compoundStatement = compoundStatement;
    }

    public List<Node> declarations() {
        return declarations;
    }

    public CompoundNode compoundStatement() {
       return compoundStatement;
    }

    @Override
    public String toString() {
        return "BlockNode{" +
                "declarations=" + declarations +
                ", compoundStatement=" + compoundStatement +
                '}';
    }
}
