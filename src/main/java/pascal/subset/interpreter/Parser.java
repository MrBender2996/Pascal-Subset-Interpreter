package pascal.subset.interpreter;


import pascal.subset.interpreter.ast.*;
import pascal.subset.interpreter.tokens.*;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    final Lexer lexer;
    Token crtToken;

    public Parser(final Lexer lexer) {
        this.lexer = lexer;
    }

    void feed(final TokenType expectedType) {
        if (expectedType != crtToken.type()) {
            throw new IllegalArgumentException("Failed to parse exception, expected " + expectedType.name() +
                    ", but found " + crtToken);
        }

        crtToken = lexer.nextToken();
    }

    Node evaluateExpression() {
        return evaluateSumOrSubstr();
    }

    Node evaluateSumOrSubstr() {
        Node result = evaluateMultiplicationOrDivision();

        while (crtToken.type() == TokenType.OPERATOR &&
                (((OperatorToken) crtToken).operator() == '+' || ((OperatorToken) crtToken).operator() == '-')) {

            final Token token = crtToken;
            switch ((((OperatorToken) crtToken).operator())) {
                case '+':
                case '-':
                    feed(TokenType.OPERATOR);
                    break;
            }

            result = new BinaryOpNode(result, token, evaluateMultiplicationOrDivision());
        }

        return result;
    }

    Node evaluateMultiplicationOrDivision() {
        Node result = evaluateBracketsOrUnary();

        while (crtToken.type() == TokenType.OPERATOR &&
                (((OperatorToken) crtToken).operator() == '*' || ((OperatorToken) crtToken).operator() == '/')
                || crtToken.type() == TokenType.KEYWORD && ((KeyWordToken) crtToken).keyWord() == KeyWords.DIV) {

            final Token token = crtToken;

            if (token.type() == TokenType.OPERATOR) {
                switch ((((OperatorToken) crtToken).operator())) {
                    case '*':
                    case '/':
                        feed(TokenType.OPERATOR);
                        break;
                }
            } else {
                feed(TokenType.KEYWORD);
            }

            result = new BinaryOpNode(result, token, evaluateBracketsOrUnary());
        }

        return result;
    }

    Node evaluateBracketsOrUnary() {
        if (crtToken.type() == TokenType.OPERATOR && (((OperatorToken) crtToken).operator()) == '(') {
            feed(TokenType.OPERATOR);
            final Node result = evaluateSumOrSubstr();
            feed(TokenType.OPERATOR);
            return result;
        } else if (crtToken.type() == TokenType.OPERATOR && (((OperatorToken) crtToken).operator()) == '+') {
            final Token op = crtToken;
            feed(TokenType.OPERATOR);
            return new UnaryOpNode(op, evaluateBracketsOrUnary());
        } else if (crtToken.type() == TokenType.OPERATOR && (((OperatorToken) crtToken).operator()) == '-') {
            final Token op = crtToken;
            feed(TokenType.OPERATOR);
            return new UnaryOpNode(op, evaluateBracketsOrUnary());
        } else if (crtToken.type() == TokenType.INTEGER_CONST || crtToken.type() == TokenType.REAL_CONST) {
            final NumberNode result = new NumberNode(crtToken);
            feed(crtToken.type());
            return result;
        } else {
            return variable();
        }
    }

    Node program() {
        crtToken = lexer.nextToken();
        feed(TokenType.KEYWORD);

        final VariableNode varNode = variable();
        final String programName = varNode.name();
        feed(TokenType.SEMICOLON);

        final Node programNode = new ProgramNode(programName, block());
        feed(TokenType.DOT);

        return programNode;
    }

    BlockNode block() {
        final List<Node> declarations = declarations();
        final Node compoundStatement = compoundStatement();
        return new BlockNode(declarations, (CompoundNode) compoundStatement);
    }

    List<Node> declarations() {
        final List<Node> result = new ArrayList<>();
        if (crtToken.type() == TokenType.KEYWORD && ((KeyWordToken)crtToken).keyWord() == KeyWords.VAR) {
            feed(TokenType.KEYWORD);

            while (crtToken.type() == TokenType.VARIABLE) {
                result.addAll(varDeclarations()); // todo carefully with adding all vars to the declaration block;
                feed(TokenType.SEMICOLON);
            }
        }

        while (crtToken.type() == TokenType.KEYWORD) {
            final KeyWordToken keyWordToken = (KeyWordToken) crtToken;
            if (keyWordToken.keyWord() != KeyWords.PROCEDURE) {
                break;
            }
            feed(TokenType.KEYWORD);

            final VariableNode variable = variable();
            final String procedureName = variable.name();
            feed(TokenType.SEMICOLON);
            final BlockNode block = block();
            feed(TokenType.SEMICOLON);

            result.add(new ProgramNode(procedureName, block));
        }

        return result;
    }

    List<Node> varDeclarations() {
        final List<Node> variables = new ArrayList<>();

        variables.add(new VariableNode((VariableToken) crtToken));
        feed(TokenType.VARIABLE);

        while (crtToken.type() == TokenType.COMMA) {
            feed(TokenType.COMMA);
            variables.add(new VariableNode((VariableToken) crtToken));
            feed(TokenType.VARIABLE);
        }

        feed(TokenType.COLON);

        final List<Node> result = new ArrayList<>();
        final Node varType = typeSpecification();
        for (final Node variable : variables) {
            result.add(new VariableDeclarationNode((VariableNode) variable, (VariableTypeNode) varType));
        }

        return result;
    }

    VariableTypeNode typeSpecification() {
        final Token token = crtToken;
        feed(TokenType.VARIABLE_TYPE);
        return new VariableTypeNode(TokenType.VARIABLE_TYPE, ((VariableTypeToken) token).variableType());
    }

    List<Node> statementList() {
        final List<Node> result = new ArrayList<>();
        result.add(statement());

        while (crtToken.type() == TokenType.SEMICOLON) {
            feed(TokenType.SEMICOLON);
            result.add(statement());
        }

        return result;
    }

    CompoundNode compoundStatement() {
        checkKeyword((KeyWordToken) crtToken, KeyWords.BEGIN);
        feed(TokenType.KEYWORD);

        final List<Node> nodes = statementList();

        checkKeyword((KeyWordToken) crtToken, KeyWords.END);
        feed(TokenType.KEYWORD);

        return new CompoundNode(nodes);
    }

    Node statement() {
        if (crtToken.type() == TokenType.KEYWORD && ((KeyWordToken) crtToken).keyWord() == KeyWords.BEGIN) {
            return compoundStatement();
        } else if (crtToken.type() == TokenType.VARIABLE) {
            return assignStatement();
        }

        return new NoOperationNode();
    }

    AssignNode assignStatement() {
        final Node left = variable();
        final Token token = crtToken;
        feed(TokenType.ASSIGN);
        final Node right = evaluateExpression();

        return new AssignNode(left, (AssignToken) token, right);
    }

    VariableNode variable() {
        if (crtToken.type() != TokenType.VARIABLE) {
            throw new IllegalStateException(
                    "Parser method \"variable\" expected VARIABLE token, but found: " + crtToken);
        }

        final VariableNode result = new VariableNode((VariableToken) crtToken);
        feed(TokenType.VARIABLE);
        return result;
    }

    NoOperationNode noOperation() {
        return new NoOperationNode();
    }

    public Node parse() {
        final Node result = program();
        if (crtToken.type() != TokenType.EOF) {
            throw new IllegalStateException("Parser expected token \"EOF\", but found " + crtToken);
        }

        return result;
    }

    void checkKeyword(final KeyWordToken token, final KeyWords expected) {
        if (token.keyWord() != expected) {
            throw new IllegalStateException("Expected keyword " + expected + " , but found " + token.keyWord());
        }
    }
}
