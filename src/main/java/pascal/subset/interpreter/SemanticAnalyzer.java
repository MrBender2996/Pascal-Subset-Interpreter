package pascal.subset.interpreter;


import pascal.subset.interpreter.ast.*;
import pascal.subset.interpreter.errors.ErrorCodes;
import pascal.subset.interpreter.errors.SemanticError;
import pascal.subset.interpreter.symbol_table.ProcedureSymbol;
import pascal.subset.interpreter.symbol_table.Symbol;
import pascal.subset.interpreter.symbol_table.ScopedSymbolTable;
import pascal.subset.interpreter.symbol_table.VarSymbol;

public class SemanticAnalyzer implements NodeVisitor {
    final Node syntaxTree;

    ScopedSymbolTable currentScope;

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
        } else if (node instanceof ProcedureDeclarationNode) {
            visitProcedureDeclaration((ProcedureDeclarationNode) node);
        } else if (node instanceof ProcedureCallNode) {
            visitProcedureCall((ProcedureCallNode) node);
        }

        return new Object();
    }

    void visitProgram(final ProgramNode node) {
        currentScope = new ScopedSymbolTable("global", 0, currentScope);

        visit(node.block());
        currentScope = currentScope.enclosingScope();
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
        if (currentScope == null) {
            throw new IllegalStateException("Visit variable declaration can not be called without a scope.");
        }

        final Symbol lookupTypeResult = currentScope.lookup(node.varType().variableType().varType());
        if (lookupTypeResult == null) {
            throw new SemanticError(ErrorCodes.ID_NOT_FOUND, node.variable().token(), "Identifier id not found -> " + node.variable().token());
        }

        if (currentScope.isDuplicate(node.variable().name())) {
            throw new SemanticError(ErrorCodes.DUPLICATE_ID, node.variable().token(), "Duplicate id found -> " + node.variable().token());
        }

        currentScope.define(new VarSymbol(node.variable().name(), lookupTypeResult));
    }

    void visitProcedureDeclaration(final ProcedureDeclarationNode node) {
        final ProcedureSymbol procSymbol = new ProcedureSymbol(node.name());
        currentScope.define(procSymbol);

        currentScope = new ScopedSymbolTable(node.name(), currentScope.scopeLevel() + 1, currentScope);

        for (final ParameterNode param : node.params()) {
            final String paramName = param.name();
            final Symbol paramType = currentScope.lookup(param.type().token().name());
            final VarSymbol paramSymbol = new VarSymbol(paramName, paramType);
            currentScope.define(paramSymbol);
            procSymbol.params().add(paramSymbol);
        }

        visit(node.block());
        currentScope = currentScope.enclosingScope();
    }

    void visitProcedureCall(final ProcedureCallNode node) {
        for (final Node actualParam : node.actualParams()) {
            visit(actualParam);
        }
    }

    void visitAssign(final AssignNode node) {
        final Node left = node.left();

        if (!(left instanceof VariableNode)) {
            throw new IllegalStateException("Expected assign node left child to be VariableNode," +
                    " but it was: " + left);
        }

        if (currentScope == null) {
            throw new IllegalStateException("Visit assign can not be called without a scope.");
        }

        final String name = ((VariableNode) left).name();
        final Symbol lookupResult = currentScope.lookup(name);

        if (lookupResult == null) {
            throw new SemanticError(ErrorCodes.ID_NOT_FOUND, ((VariableNode) left).token(), "Identifier id not found -> " + ((VariableNode) left).token());
        }

        visit(node.right());
    }

    void visitVariable(final VariableNode node) {
        if (currentScope == null) {
            throw new IllegalStateException("Visit variable can not be called without a scope.");
        }

        final String name = node.name();
        final Symbol lookupResult = currentScope.lookup(name);

        if (lookupResult == null) {
            throw new IllegalStateException("Failed to find variable \"" + name + "\" in the symbol table");
        }
    }
}
