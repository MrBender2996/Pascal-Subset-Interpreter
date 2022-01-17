package pascal.subset.interpreter;

import pascal.subset.interpreter.ast.Node;
import pascal.subset.interpreter.symbol_table.SymbolTableBuilder;

public class Main {
    public static void main(String[] args) {
        final String program = "PROGRAM Part11;\n" +
                "VAR\n" +
                "   number : INTEGER;\n" +
                "   a, b   : INTEGER;\n" +
                "   y      : REAL;\n" +
                "\n" +
                "BEGIN {Part11}\n" +
                "   number := 2;\n" +
                "   a := number ;\n" +
                "   b := 10 * a + 10 * number DIV 4;\n" +
                "   y := 20 / 7 + 3.14\n" +
                "END.  {Part11}";

        final Lexer lexer = new Lexer(program);
        final Parser parser = new Parser(lexer);
        final Node syntaxTree = parser.parse();
        final SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder(syntaxTree);
        final Interpreter interpreter = new Interpreter(syntaxTree);

        symbolTableBuilder.buildTable();
        interpreter.interpret();

        System.out.println("MEMORY STATE AFTER THE PROGRAM EXECUTED:");
        Interpreter.GLOBAL_MEMORY.entrySet().forEach(System.out::println);
    }
}