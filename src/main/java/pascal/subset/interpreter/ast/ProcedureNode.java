package pascal.subset.interpreter.ast;

public class ProcedureNode implements Node {
    final String name;
    final BlockNode block;

    public ProcedureNode(final String name, final BlockNode block) {
        this.name = name;
        this.block = block;
    }

    public String name() {
        return name;
    }

    public BlockNode block() {
        return block;
    }

    @Override
    public String toString() {
        return "ProcedureNode{" +
                "name='" + name + '\'' +
                ", block=" + block +
                '}';
    }
}
