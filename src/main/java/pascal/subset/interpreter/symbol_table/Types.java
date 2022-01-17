package pascal.subset.interpreter.symbol_table;

public enum Types {
    INTEGER("INTEGER"),
    REAL("REAL");

    final String varType;

    Types(final String varType) {
       this.varType = varType;
    }

    public String varType() {
        return varType;
    }

    public static boolean isVarType(final String s) {
        for (final Types value : Types
                .values()) {
            if (s.compareToIgnoreCase(value.varType) == 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "VariableTypes{" +
                "varType='" + varType + '\'' +
                '}';
    }
}
