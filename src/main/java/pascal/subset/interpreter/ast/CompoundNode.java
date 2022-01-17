package pascal.subset.interpreter.ast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompoundNode implements Node {
    final List<Node> children = new ArrayList<>();

    public CompoundNode(final List<Node> children) {
        this.children.addAll(children);
    }

    public List<Node> children() {
       return children;
    }

    @Override
    public String toString() {
        return "CompoundNode{" +
                "children=" + Arrays.toString(children.toArray()) +
                '}';
    }
}
