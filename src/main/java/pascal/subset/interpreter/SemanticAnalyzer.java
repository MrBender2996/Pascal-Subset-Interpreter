package pascal.subset.interpreter;


import pascal.subset.interpreter.ast.*;
import pascal.subset.interpreter.symbol_table.Symbol;
import pascal.subset.interpreter.symbol_table.SymbolTable;
import pascal.subset.interpreter.symbol_table.VarSymbol;

public class SemanticAnalyzer implements NodeVisitor {
    final SymbolTable symbolTable = new SymbolTable();
    final Node syntaxTree;

    public SemanticAnalyzer(final Node syntaxTree) {
        this.syntaxTree = syntaxTree;
    }

    void analyze() {
        visit(syntaxTree);
    }

    @Override
    public Object visit(final Node node) {
        if (node instanceof ProgramNode) {
           visitProgram((ProgramNode) node);
        } else if (node instanceof BlockNode) {
           visitBlock((BlockNode) node);
        } else if (node instanceof BinaryOpNode) {
            visitBinaryOperation((BinaryOpNode) node);
        } else if (node instanceof NumberNode) {
            visitNumber((NumberNode) node);
        } else if (node instanceof UnaryOpNode) {
            visitUnaryOperation((UnaryOpNode) node);
        } else if (node instanceof AssignNode) {
            visitAssign((AssignNode) node);
        } else if (node instanceof CompoundNode) {
            visitCompound((CompoundNode) node);
        } else if (node instanceof NoOperationNode) {
            visitNoOperation((NoOperationNode) node);
        } else if (node instanceof VariableNode) {
            visitVariable((VariableNode) node);
        } else if (node instanceof VariableDeclarationNode) {
            visitVariableDeclaration((VariableDeclarationNode) node);
        } else if (node instanceof ProcedureNode) {
            visitProcedure((ProcedureNode) node);
        }

        return new Object(); // todo think is this the right pattern
    }

    void visitProgram(final ProgramNode node) {
        visit(node.block());
    }

    void visitBlock(final BlockNode node) {
        for (final Node declaration : node.declarations()) {
            visit(declaration);
        }

        visit(node.compoundStatement());
    }

    void visitCompound(final CompoundNode node) {
        for (final Node child : node.children()) {
            visit(child);
        }
    }

    void visitBinaryOperation(final BinaryOpNode node) {
        visit(node.left());
        visit(node.right());
    }

    void visitNumber(final NumberNode node) {
        // skip
    }

    void visitUnaryOperation(final UnaryOpNode node) {
        visit(node.expression());
    }

    void visitNoOperation(final NoOperationNode node) {
        // skip
    }

    void visitVariableDeclaration(final VariableDeclarationNode node) {
        final Symbol lookupTypeResult = symbolTable.lookup(node.varType().variableType().varType());
        if (lookupTypeResult == null) {
            throw new IllegalStateException(node.varType().variableType().varType() + " was not recognized");
        }

        if (symbolTable.lookup(node.variable().name()) != null) {
            throw new IllegalStateException("Variable \"" + node.variable().name() + "\" have already been declared.");
        }

        symbolTable.define(new VarSymbol(node.variable().name(), lookupTypeResult));
    }

    void visitProcedure(final ProcedureNode node) {
        // pass
    }

    void visitAssign(final AssignNode node) {
        final Node left = node.left();

        if (!(left instanceof VariableNode)) {
            throw new IllegalStateException("Symbol table builder expected assign node left child to be VariableNode," +
                    " but found: " + left);
        }

        final String name = ((VariableNode) left).name();
        final Symbol lookupResult = symbolTable.lookup(name);

        if (lookupResult == null) {
           throw new IllegalStateException("Failed to find variable \"" + name + "\" in the symbol table");
        }

        visit(node.right());
    }

    void visitVariable(final VariableNode node) {
        final String name = node.name();
        final Symbol lookupResult = symbolTable.lookup(name);

        if (lookupResult == null) {
            throw new IllegalStateException("Failed to find variable " + name + " in the symbol table");
        }
    }

    public SymbolTable symbolTable() {
        return symbolTable;
    }
}
