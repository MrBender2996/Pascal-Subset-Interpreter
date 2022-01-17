package pascal.subset.interpreter.symbol_table;

public class BuiltInTypeSymbol extends Symbol {
    final Types builtInType;

    public BuiltInTypeSymbol(final String name, final Types builtInType) {
        super(name);
        this.builtInType = builtInType;
    }

    public Types builtInType() {
        return builtInType;
    }

    @Override
    public String toString() {
        return "BuiltInTypeSymbol{" +
                "name=" + name +
                ", builtInType=" + builtInType +
                '}';
    }
}
