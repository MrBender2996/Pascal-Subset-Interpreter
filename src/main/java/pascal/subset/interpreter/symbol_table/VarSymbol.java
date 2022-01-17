package pascal.subset.interpreter.symbol_table;

public class VarSymbol extends Symbol {

    public VarSymbol(final String name, final Symbol type) {
        super(name, type);
    }

    public VarSymbol(final String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "VarSymbol{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
