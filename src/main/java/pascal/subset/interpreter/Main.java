package pascal.subset.interpreter;


import pascal.subset.interpreter.ast.Node;

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

        final String scopedProgram = "PROGRAM Main;\n" +
                "\n" +
                "PROCEDURE Alpha(a : INTEGER; b : INTEGER);\n" +
                "VAR x : INTEGER;\n" +
                "\n" +
                "   PROCEDURE Beta(a : INTEGER; b : INTEGER);\n" +
                "   VAR x : INTEGER;\n" +
                "   BEGIN\n" +
                "      x := a * 10 + b * 2;\n" +
                "   END;\n" +
                "\n" +
                "BEGIN\n" +
                "   x := (a + b ) * 2;\n" +
                "\n" +
                "   Beta(5, 10);      { procedure call }\n" +
                "END;\n" +
                "\n" +
                "BEGIN { Main }\n" +
                "\n" +
                "   Alpha(3 + 5, 7);  { procedure call }\n" +
                "\n" +
                "END.  { Main }";

        final Lexer lexer = new Lexer(scopedProgram);
        final Parser parser = new Parser(lexer);
        final Node syntaxTree = parser.parse();
        final SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer(syntaxTree);
        final Interpreter interpreter = new Interpreter(syntaxTree);

        semanticAnalyzer.analyze();
        interpreter.interpret();

        System.out.println("MEMORY STATE AFTER THE PROGRAM EXECUTED:");
        System.out.println(interpreter.callStack);
    }
}
