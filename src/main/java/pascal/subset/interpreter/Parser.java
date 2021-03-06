package pascal.subset.interpreter;


import pascal.subset.interpreter.ast.*;
import pascal.subset.interpreter.errors.ErrorCodes;
import pascal.subset.interpreter.errors.ParserError;
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
            throw new ParserError(ErrorCodes.UNEXPECTED_TOKEN, crtToken, "Expected " + expectedType +
                    ", but found " + crtToken);
        }

        crtToken = lexer.nextToken();
    }

    Node expression() {
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

    ProgramNode program() {
        crtToken = lexer.nextToken();
        feed(TokenType.KEYWORD);

        final VariableNode varNode = variable();
        final String programName = varNode.name();
        feed(TokenType.SEMICOLON);

        final ProgramNode programNode = new ProgramNode(programName, block());
        feed(TokenType.DOT);

        return programNode;
    }

    BlockNode block() {
        final List<Node> declarations = declarations();
        final CompoundNode compoundStatement = compoundStatement();
        return new BlockNode(declarations, compoundStatement);
    }

    List<Node> declarations() {
        final List<Node> declarations = new ArrayList<>();
        if (crtToken.type() == TokenType.KEYWORD && ((KeyWordToken) crtToken).keyWord() == KeyWords.VAR) {
            feed(TokenType.KEYWORD);

            while (crtToken.type() == TokenType.VARIABLE) {
                declarations.addAll(varDeclarations());
                feed(TokenType.SEMICOLON);
            }
        }

        while (crtToken.type() == TokenType.KEYWORD) {
            final KeyWordToken keyWordToken = (KeyWordToken) crtToken;
            if (keyWordToken.keyWord() != KeyWords.PROCEDURE) {
                break;
            }
            feed(TokenType.KEYWORD);

            declarations.add(procedureDeclaration());
        }

        return declarations;
    }

    ProcedureDeclarationNode procedureDeclaration() {
        final VariableNode variable = variable();
        final String procedureName = variable.name();

        List<ParameterNode> params = null;
        if (crtToken.type() == TokenType.OPERATOR && ((OperatorToken) crtToken).operator() == '(') {
            feed(TokenType.OPERATOR);
            params = formalParameterList();
            feed(TokenType.OPERATOR);
        }

        feed(TokenType.SEMICOLON);
        final BlockNode block = block();
        feed(TokenType.SEMICOLON);

        return new ProcedureDeclarationNode(procedureName, params, block);
    }

    ProcedureCallNode procedureCall() {
        if (crtToken.type() != TokenType.VARIABLE) {
            throw new ParserError(ErrorCodes.UNEXPECTED_TOKEN, crtToken, "Expected " + TokenType.VARIABLE +
                    ", but found " + crtToken);
        }

        final String procedureName = ((VariableToken) crtToken).variable();
        feed(TokenType.VARIABLE);
        feed(TokenType.OPERATOR); //todo think may be we need to declare separate Token types for l/r paren???

        final List<Node> actualParams = new ArrayList<>();
        if (crtToken.type() != TokenType.OPERATOR || ((OperatorToken)crtToken).operator() != ')') {
            actualParams.add(expression());
        }

        while (crtToken.type() == TokenType.COMMA) {
            feed(TokenType.COMMA);
            actualParams.add(expression());
        }

        feed(TokenType.OPERATOR);

        return new ProcedureCallNode(procedureName, actualParams);
    }

    List<ParameterNode> formalParameters() {
        final List<ParameterNode> paramNodes = new ArrayList<>();
        final List<Token> paramsTokens = new ArrayList<>();

        paramsTokens.add(crtToken);
        feed(TokenType.VARIABLE);

        while (crtToken.type() == TokenType.COMMA) {
            feed(TokenType.COMMA);
            paramsTokens.add(crtToken);
            feed(TokenType.VARIABLE);
        }

        feed(TokenType.COLON);
        final VariableTypeNode typeNode = typeSpecification();
        for (final Token paramsToken : paramsTokens) {
            paramNodes.add(new ParameterNode(((VariableToken) paramsToken).variable(), typeNode));
        }

        return paramNodes;
    }

    List<ParameterNode> formalParameterList() {
        if (crtToken.type() != TokenType.VARIABLE) {
            return new ArrayList<>();
        }

        final List<ParameterNode> paramNodes = formalParameters();
        while (crtToken.type() == TokenType.SEMICOLON) {
            feed(TokenType.SEMICOLON);
            paramNodes.addAll(formalParameters());
        }

        return paramNodes;
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
        final VariableTypeNode varType = typeSpecification();
        for (final Node variable : variables) {
            result.add(new VariableDeclarationNode((VariableNode) variable, varType));
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
        } else if (crtToken.type() == TokenType.VARIABLE && lexer.currentChar() == '(') {
            return procedureCall();
        } else if (crtToken.type() == TokenType.VARIABLE) {
            return assignStatement();
        }

        return new NoOperationNode();
    }

    AssignNode assignStatement() {
        final Node left = variable();
        final Token token = crtToken;
        feed(TokenType.ASSIGN);
        final Node right = expression();

        return new AssignNode(left, (AssignToken) token, right);
    }

    VariableNode variable() {
        if (crtToken.type() != TokenType.VARIABLE) {
            throw new ParserError(ErrorCodes.UNEXPECTED_TOKEN, crtToken, "Expected " + TokenType.VARIABLE +
                    ", but found " + crtToken);
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
            throw new ParserError(ErrorCodes.UNEXPECTED_TOKEN, crtToken, "Expected " + TokenType.EOF +
                    ", but found " + crtToken);
        }

        return result;
    }

    void checkKeyword(final KeyWordToken token, final KeyWords expected) {
        if (token.keyWord() != expected) {
            throw new IllegalStateException("Expected keyword " + expected + " , but found " + token.keyWord());
        }
    }
}
