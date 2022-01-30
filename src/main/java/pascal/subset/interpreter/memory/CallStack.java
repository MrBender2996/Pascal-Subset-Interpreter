package pascal.subset.interpreter.memory;

import java.util.Arrays;
import java.util.LinkedList;

public class CallStack {
    final LinkedList<StackFrame> frames = new LinkedList<>();

    public void push(final StackFrame frame) {
        frames.push(frame);
    }

    public StackFrame pop() {
        return frames.pop();
    }

    public StackFrame peek() {
        return frames.peek();
    }

    @Override
    public String toString() {
        return "CallStack{" +
                "frames=" + Arrays.toString(frames.toArray()) +
                '}';
    }
}
