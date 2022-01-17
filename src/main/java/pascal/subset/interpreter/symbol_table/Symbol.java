package pascal.subset.interpreter.symbol_table;

public abstract class Symbol {
    final String name;
    final Symbol type;

    protected Symbol(final String name, final Symbol type) {
        this.name = name;
        this.type = type;
    }

    protected Symbol(final String name) {
        this.name = name;
        this.type = null;
    }

    public boolean hasType() {
        return type == null;
    }

    public Symbol type() {
        return type;
    }

    @Override
    public String toString() {
        return "Symbol{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
