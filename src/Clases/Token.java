package Clases;

import java.util.*;

public class Token {
    enum Toktype {
        NUMBER, OP, PAREN
    }

    private Token.Toktype ttype;
    private int value;
    private char tk;

    // Getters
    public Token.Toktype getTtype() {
        return this.ttype;
    }
    public int getValue() {
        return this.value;
    }
    public char getTk() {
        return this.tk;
    }

    // Private constructor (we don't want others to build tokens)
    public Token() {}

    // Torna un token de tipus "NUMBER"
    static Token tokNumber(int value) {
        Token number = new Token();
        number.ttype = Toktype.NUMBER;
        number.value = value;
        return number;
    }

    // Torna un token de tipus "OP"
    static Token tokOp(char c) {
        Token operador = new Token();
        operador.ttype = Toktype.OP;
        operador.tk = c;
        return operador;
    }

    // Torna un token de tipus "PAREN"
    static Token tokParen(char c) {
        Token parentesis = new Token();
        parentesis.ttype = Toktype.PAREN;
        parentesis.tk = c;
        return parentesis;
    }

    // Mostra un token (conversió a String)
    @Override
    public String toString() {
        return String.valueOf(this.ttype);
    }

    // Mètode equals. Comprova si dos objectes Clases.Token són iguals
    @Override
    public boolean equals(Object o) {
        if (o instanceof Token) {
            Token t = (Token) o;
            return (t.value == this.value) || (t.tk == this.tk);
        }
        return false;
    }

    // A partir d'un String, torna una llista de tokens
    public static Token[] getTokens(String expr) {
        List<Token> tokens = new ArrayList<>();
        for (int i = 0; i < expr.length(); i++) {
            if (Character.isDigit(expr.charAt(i))) {
                i = addNumber(tokens, i, expr);
            } else if (expr.charAt(i) != ' '){
                checkChar(expr.charAt(i), tokens);
            }
        }
        return tokens.toArray(new Token[tokens.size()]);
    }

    // Método que comprueba si el carácter es un operador o un paréntesis
    public static void checkChar(char c, List<Token> tokens) {
        if (c == '+' || c == '-' || c == '*' || c == '/'){
            tokens.add(Token.tokOp(c));
        } else if (c == '(' || c == ')' ) {
            tokens.add(Token.tokParen(c));
        }
    }

    /* Si es un token de multiplicación o división, devolvemos 2 sino 1.
     Ya que la suma y la resta tienen menos preferencia */
    public static int getPrecedence(Token t) {
        return !(t.getTk() == '+' || t.getTk() == '-') ? 2 : 1;
    }

    // Método que añade los numeros correspondientes a la lista de Tokens
    public static int addNumber(List<Token> tokens, int i, String expr){
        StringBuilder actualToken = new StringBuilder();
        for (int j = i; j < expr.length(); j++) {
            if (!Character.isDigit(expr.charAt(j))) { break; }
            actualToken.append(expr.charAt(j));
            i = j;
        }
        tokens.add(Token.tokNumber(Integer.parseInt(actualToken.toString())));
        return i;
    }
}