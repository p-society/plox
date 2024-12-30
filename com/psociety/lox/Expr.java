package com.psociety.lox;

import java.util.*;

abstract class Expr {

    // binary → expression operator expression ;
    static class Binary extends Expr {
        Binary(Expr left, Token operator, Expr right) {
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        final Expr left;
        final Token operator;
        final Expr right;
    }

    // grouping → "(" expression ")" ;
    static class Grouping extends Expr {
        Grouping(Expr expression) {
            this.expression = expression;
        }

        final Expr expression;
    }

    // literal → NUMBER | STRING | "true" | "false" | "nil" ;
    static class Literal extends Expr {
        Literal(Object value) {
            this.value = value;
        }

        final Object value;
    }

    // unary → ( "-" | "!" ) expression ;
    static class Unary extends Expr {
        Unary(Token operator, Expr right) {
            this.operator = operator;
            this.right = right;
        }

        final Token operator;
        final Expr right;
    }
}
