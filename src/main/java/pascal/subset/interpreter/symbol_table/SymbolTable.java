package pascal.subset.interpreter.symbol_table;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
   final Map<String, Symbol> symbols = new HashMap<>();

   public SymbolTable() {
      final BuiltInTypeSymbol integer = new BuiltInTypeSymbol("INTEGER", Types.INTEGER);
      final BuiltInTypeSymbol real = new BuiltInTypeSymbol("REAL", Types.REAL);

      symbols.put(integer.name, integer);
      symbols.put(real.name, real);
   }

   void define(final Symbol symbol) {
      symbols.put(symbol.name, symbol);
   }

   Symbol lookup(final String name) {
       return symbols.get(name);
   }


   @Override
   public String toString() {
      return "SymbolTable{" +
              "symbols=" + Arrays.toString(symbols.entrySet().toArray()) +
              '}';
   }
}