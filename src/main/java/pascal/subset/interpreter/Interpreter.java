package pascal.subset.interpreter;

import pascal.subset.interpreter.ast.*;
import pascal.subset.interpreter.memory.CallStack;
import pascal.subset.interpreter.memory.FrameType;
import pascal.subset.interpreter.memory.StackFrame;
import pascal.subset.interpreter.tokens.*;


public class Interpreter implements NodeVisitor {
    final CallStack callStack = new CallStack();
    final Node syntaxTree;

    public Interpreter(final Node syntaxTree) {
        this.syntaxTree = syntaxTree;
    }

    @Override
    public Object visit(final Node node) {
        if (node instanceof BinaryOpNode) {
            return visitBinaryOperator((BinaryOpNode) node);
        } else if (node instanceof NumberNode) {
            return visitNumber((NumberNode) node);
        } else if (node instanceof UnaryOpNode) {
            return visitUnaryOperator((UnaryOpNode) node);
        } else if (node instanceof AssignNode) {
            visitAssignNode((AssignNode) node);
        } else if (node instanceof CompoundNode) {
            visitCompound((CompoundNode) node);
        } else if (node instanceof NoOperationNode) {
            visitNoOperationNode((NoOperationNode) node);
        } else if (node instanceof VariableNode) {
            return visitVariableNode((VariableNode) node);
        } else if (node instanceof BlockNode) {
            visitBlock((BlockNode) node);
        } else if (node instanceof ProgramNode) {
            visitProgram((ProgramNode) node);
        } else if (node instanceof VariableDeclarationNode) {
            visitVarDeclaration((VariableDeclarationNode) node);
        } else if (node instanceof VariableTypeNode) {
            visitVarType((VariableTypeNode) node);
        } else if (node instanceof ProcedureDeclarationNode) {
            visitProcedureDeclaration((ProcedureDeclarationNode) node);
        } else if (node instanceof ProcedureCallNode) {
            visitProcedureCall((ProcedureCallNode) node);
        }

        return new Object();
    }

    public Object visitBinaryOperator(final BinaryOpNode node) {
        if (!(node.operator().type() == TokenType.OPERATOR || node.operator().type() == TokenType.KEYWORD
                && ((KeyWordToken) node.operator()).keyWord() == KeyWords.DIV)) {
            throw new IllegalStateException("Token type of the node " + node + " is not \"Operator\" or \"DIV\" " +
                    "keyword");
        }

        if (node.operator().type() == TokenType.KEYWORD && ((KeyWordToken) node.operator()).keyWord() == KeyWords.DIV) {
           return divAsFloats(visit(node.left()), visit(node.right()));
        }

        switch (((OperatorToken) node.operator()).operator()) {
            case '+':
                return sum(visit(node.left()), visit(node.right()));
            case '-':
                return sub(visit(node.left()), visit(node.right()));
            case '*':
                return mul(visit(node.left()), visit(node.right()));
            case '/':
                return div(visit(node.left()), visit(node.right()));
        }

        throw new IllegalStateException("Token of the node " + node + " does not match +|-|*|/");
    }

    public Object visitNumber(final NumberNode node) {
        final Token token = node.token();
        if (token.type() == TokenType.INTEGER_CONST) {
            return ((IntegerConstToken) token).value();
        } else if (token.type() == TokenType.REAL_CONST) {
            return ((RealConstToken) token).value();
        }

        throw new IllegalStateException("Number node expected to have \"INTEGER_CONST\" or \"REAL_CONST\" type, but " +
                "found: " + token.type());
    }

    public void visitCompound(final CompoundNode node) {
        for (final Node child : node.children()) {
            visit(child);
        }
    }

    public void visitProgram(final ProgramNode node) {
        callStack.push(new StackFrame(node.name(), FrameType.PROGRAM, 0));
        visit(node.block());
        callStack.pop();
    }

    public void visitBlock(final BlockNode node) {
        for (final Node declaration : node.declarations()) {
            visit(declaration);
        }

        visit(node.compoundStatement());
    }

    public void visitProcedureCall(final ProcedureCallNode node) {
       // pass
    }

    public void visitVarDeclaration(final VariableDeclarationNode node) {
        // todo implement declaration
    }

    public void visitVarType(final VariableTypeNode node) {
        // todo implement type checks
    }

    public void visitNoOperationNode(final NoOperationNode node) {
        // do nothing
    }

    public void visitAssignNode(final AssignNode node) {
        final String varName =  ((VariableNode) node.left()).name();
        final Object varValue = visit(node.right());
        callStack.peek().set(varName, varValue);
    }

    public Object visitVariableNode(final VariableNode node) {
        return callStack.peek().get(node.name());
    }

    public Object visitUnaryOperator(final UnaryOpNode node) {
        switch (((OperatorToken) node.operator()).operator()) {
            case '+':
                return visit(node.expression());
            case '-':
                final Object result = visit(node.expression());
                if (result instanceof Integer) {
                    return -((Integer) result);
                } else if (result instanceof Float) {
                    return -((Float) result);
                } else {
                    throw new IllegalStateException(
                            "Expression result expected to be Integer or Float, but found: " + result);
                }
        }

        throw new IllegalStateException("Token of the node " + node + " does not match +|-");
    }

    void visitProcedureDeclaration(final ProcedureDeclarationNode node) {
        // pass
    }

    public Object interpret() {
        return visit(syntaxTree);
    }

    private static Object sum(final Object a, final Object b) {
        if (a instanceof Integer && b instanceof Integer) {
            return (Integer) a + (Integer) b;
        } else if (a instanceof Float && b instanceof Float) {
            return (Float) a + (Float) b;
        } else if (a instanceof Integer && b instanceof Float) {
            return (Integer) a + (Float) b;
        } else if (a instanceof Float && b instanceof Integer) {
            return (Float) a + (Integer) b;
        }

        throw new IllegalStateException(
                "Sum expected to be performed with integer/float, but received: " + a + ", " + b);
    }

    private static Object sub(final Object a, final Object b) {
        if (a instanceof Integer && b instanceof Integer) {
            return (Integer) a - (Integer) b;
        } else if (a instanceof Float && b instanceof Float) {
            return (Float) a - (Float) b;
        } else if (a instanceof Integer && b instanceof Float) {
            return (Integer) a - (Float) b;
        } else if (a instanceof Float && b instanceof Integer) {
            return (Float) a - (Integer) b;
        }

        throw new IllegalStateException(
                "Subtract expected to be performed with integer/float, but received: " + a + ", " + b);
    }

    private static Object mul(final Object a, final Object b) {
        if (a instanceof Integer && b instanceof Integer) {
            return (Integer) a * (Integer) b;
        } else if (a instanceof Float && b instanceof Float) {
            return (Float) a * (Float) b;
        } else if (a instanceof Integer && b instanceof Float) {
            return (Integer) a * (Float) b;
        } else if (a instanceof Float && b instanceof Integer) {
            return (Float) a * (Integer) b;
        }

        throw new IllegalStateException(
                "Multiply expected to be performed with integer/float, but received: " + a + ", " + b);
    }

    private static Object div(final Object a, final Object b) {
        if (a instanceof Integer && b instanceof Integer) {
            return (Integer) a / (Integer) b;
        } else if (a instanceof Float && b instanceof Float) {
            return (Float) a / (Float) b;
        } else if (a instanceof Integer && b instanceof Float) {
            return (Integer) a / (Float) b;
        } else if (a instanceof Float && b instanceof Integer) {
            return (Float) a / (Integer) b;
        }

        throw new IllegalStateException(
                "Divide expected to be performed with integer/float, but received: " + a + ", " + b);
    }

    private static Object divAsFloats(final Object a, final Object b) {
        if (a instanceof Integer && b instanceof Integer) {
            return ((float) (Integer) a) / (Integer) b;
        } else if (a instanceof Float && b instanceof Float) {
            return (Float) a / (Float) b;
        } else if (a instanceof Integer && b instanceof Float) {
            return ((float) (Integer) a) / (Float) b;
        } else if (a instanceof Float && b instanceof Integer) {
            return (Float) a / ((float) (Integer) b);
        }

        throw new IllegalStateException(
                "Divide expected to be performed with integer/float, but received: " + a + ", " + b);
    }
}
