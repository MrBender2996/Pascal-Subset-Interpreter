package pascal.subset.interpreter.symbol_table;

public abstract class Symbol {
    final String name;
    final Symbol type;

    int scopeLevel;

    protected Symbol(final String name) {
        this(name, null, 0);
    }

    protected Symbol(final String name, final Symbol type) {
        this(name, type, 0);
    }

    protected Symbol(final String name, final Symbol type, final int scopeLevel) {
        this.name = name;
        this.type = type;
        this.scopeLevel = scopeLevel;
    }

    public boolean hasType() {
        return type == null;
    }

    public Symbol type() {
        return type;
    }

    public String name() {
        return name;
    }

    public int scopeLevel() {
        return scopeLevel;
    }

    public void scopeLevel(final int scopeLevel) {
        this.scopeLevel = scopeLevel;
    }

    @Override
    public String toString() {
        return "Symbol{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", scopeLevel=" + scopeLevel +
                '}';
    }
}
