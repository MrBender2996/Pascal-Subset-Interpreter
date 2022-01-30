package pascal.subset.interpreter.memory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StackFrame {
    final String name;
    final FrameType frameType;
    final int nestingLevel;
    final Map<String, Object> memory = new HashMap<>();

    public StackFrame(final String name, final FrameType frameType, final int nestingLevel) {
        this.name = name;
        this.frameType = frameType;
        this.nestingLevel = nestingLevel;
    }

    public void set(final String name, final Object value) {
        memory.put(name, value);
    }

    public Object get(final String name) {
        return memory.get(name);
    }

    public int nestingLevel() {
        return nestingLevel;
    }

    @Override
    public String toString() {
        return "StackFrame{" +
                "name='" + name + '\'' +
                ", frameType=" + frameType +
                ", nestingLevel=" + nestingLevel +
                ", memory=" + Arrays.toString(memory.entrySet().toArray()) +
                '}';
    }
}
