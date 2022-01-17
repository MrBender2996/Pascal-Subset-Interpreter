package pascal.subset.interpreter.ast;

public class ProgramNode implements Node {
    final String name;
    final BlockNode block;

    public ProgramNode(final String name, final BlockNode block) {
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
        return "ProgramNode{" +
                "name='" + name + '\'' +
                ", block=" + block +
                '}';
    }
}
