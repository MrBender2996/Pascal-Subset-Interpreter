package pascal.subset.interpreter.memory;

public enum FrameType {
    PROGRAM("PROGRAM"),
    PROCEDURE("PROCEDURE");

    final String frameType;

    FrameType(final String frameType) {
        this.frameType = frameType;
    }

    public String frameType()  {
        return frameType;
    }

    @Override
    public String toString() {
        return "FrameType{" +
                "frameType='" + frameType + '\'' +
                '}';
    }
}
