package Clases;

import java.util.*;

public class Evaluator {

    public static int calculate(String expr) {
        Token[] tokens = Token.getTokens(expr);
        List<Token> listRPN = new ArrayList<>();
        LinkedList<Token> operators = new LinkedList<>();
        for (Token t : tokens) {
            addTokens(t, listRPN, operators);
        }
        clean(listRPN, operators);
        return calcRPN(listRPN.toArray(new Token[listRPN.size()]));
    }

    public static int calcRPN(Token[] list) {
        LinkedList<Token> tokenResult = new LinkedList<>();
        for (Token t: list) {
            if (t.getTtype() == Token.Toktype.PAREN) continue;
            if (t.getTtype() == Token.Toktype.NUMBER) {
                tokenResult.push(t);
            } else if (t.getTtype() != Token.Toktype.NUMBER) {
                tokenResult.push(operate(tokenResult, t.getTk()));
            }
        }
        return tokenResult.pop().getValue();
    }

    // Limpiar la pila de Paréntesis
    public static void clean(List<Token> tokenRPN, LinkedList<Token> tokenOp) {
        while (!tokenOp.isEmpty()) {
            // Si el Clases.Token es de tipo Paren, sacamos el primer elemento en la pila
            if (tokenOp.peek().getTtype() == Token.Toktype.PAREN) {
                tokenOp.pop();
                break;
            }
            tokenRPN.add(tokenOp.pop());
        }
    }

    // Método que determina la precedencia del Clases.Token
    public static void determinePrecedence(List<Token> tokenRPN, LinkedList<Token> tokenOp, Token t) {
        if (t.getTk() == ')') {
            clean(tokenRPN, tokenOp);
        } else if (Token.getPrecedence(t) > Token.getPrecedence(tokenOp.peek()) ||
                tokenOp.peek().getTtype() == Token.Toktype.PAREN) {
            tokenOp.push(t);
        } else if (Token.getPrecedence(tokenOp.peek()) == Token.getPrecedence(t)) {
            tokenRPN.add(tokenOp.pop());
            tokenOp.push(t);
        } else if (Token.getPrecedence(t) < Token.getPrecedence(tokenOp.peek())) {
            clean(tokenRPN, tokenOp);
            tokenOp.push(t);
        }
    }

    // Método que realiza las operaciones correspondientes
    public static Token operate(LinkedList<Token> tokenResult, char c) {
        Token t1 = tokenResult.pop(), t2 = tokenResult.pop();
        return (c == '+') ? Token.tokNumber(t2.getValue() + t1.getValue()) :
                (c == '-') ? Token.tokNumber(t2.getValue() - t1.getValue()) :
                        (c == '*') ? Token.tokNumber(t2.getValue() * t1.getValue()):
                                Token.tokNumber(t2.getValue() / t1.getValue());
    }

    // Método que añade los Tokens de tipo número y los Tokens de tipo operador
    public static void addTokens(Token t, List<Token> listRPN, LinkedList<Token> operators){
        if (t.getTtype() == Token.Toktype.NUMBER) {
            listRPN.add(t);
        } else if (t.getTtype() != Token.Toktype.NUMBER) {
            if (operators.isEmpty() || t.getTk() == '(') {
                operators.push(t);
            } else {
                determinePrecedence(listRPN, operators, t);
            }
        }
    }
}