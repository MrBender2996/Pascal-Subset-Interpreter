package pascal.subset.interpreter.symbol_table;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ScopedSymbolTable {
    final String scopeName;
    final int scopeLevel;
    final ScopedSymbolTable enclosingScope;
    final Map<String, Symbol> symbols = new HashMap<>();

    public ScopedSymbolTable(final String scopeName, final int scopeLevel) {
        this(scopeName, scopeLevel, null);
    }

    public ScopedSymbolTable(final String scopeName, final int scopeLevel, final ScopedSymbolTable enclosingScope) {
        this.scopeName = scopeName;
        this.scopeLevel = scopeLevel;
        this.enclosingScope = enclosingScope;

        final BuiltInTypeSymbol integer = new BuiltInTypeSymbol("INTEGER", Types.INTEGER);
        final BuiltInTypeSymbol real = new BuiltInTypeSymbol("REAL", Types.REAL);

        symbols.put(integer.name, integer);
        symbols.put(real.name, real);
    }

    public void define(final Symbol symbol) {
        symbols.put(symbol.name, symbol);
    }

    public Symbol lookup(final String name) {
        final Symbol symbol = symbols.get(name);
        if (symbol != null) {
            return symbol;
        }

        return enclosingScope == null ? null : enclosingScope.lookup(name);
    }

    public boolean isDuplicate(final String name) {
        return symbols.containsKey(name);
    }

    public ScopedSymbolTable enclosingScope() {
        return enclosingScope;
    }

    public Map<String, Symbol> symbols() {
        return symbols;
    }

    public int scopeLevel() {
        return scopeLevel;
    }

    @Override
    public String toString() {
        return "SymbolTable{" +
                "scopeName=" + scopeName +
                ", scopeLevel=" + scopeLevel +
                ", symbols=" + Arrays.toString(symbols.entrySet().toArray()) +
                ", enclosingScope=" + (enclosingScope == null ? "" : enclosingScope) +
                '}';
    }
}
