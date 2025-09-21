package com.craftinginterpreters.lox;

// class RpnPrinter implements Expr.Visitor<String> {
class RpnPrinter {
    // String print(Expr expr) {
    //     return expr.accept(this);
    // }

    // @Override
    // public String visitBinaryExpr(Expr.Binary expr) {
    //     return spaceDelimiter(expr.operator.lexeme,
    //                         expr.left, expr.right);
    // }

    // @Override
    // public String visitGroupingExpr(Expr.Grouping expr) {
    //     return spaceDelimiter("", expr.expression);
    // }

    // @Override
    // public String visitLiteralExpr(Expr.Literal expr) {
    //     if (expr.value == null) return "nil";
    //     return expr.value.toString();
    // }

    // @Override
    // public String visitUnaryExpr(Expr.Unary expr) {
    //     return spaceDelimiter(expr.operator.lexeme, expr.right);
    // }

    // @Override
    // public String visitTernaryExpr(Expr.Ternary expr) {
    //     return spaceDelimiter("?", expr.condition, expr.ifTrue, expr.ifFalse);
    // }

    // @Override
    // public String visitVariableExpr(Expr.Variable expr) {
    //     return "";
    // }

    // @Override
    // public String visitAssignExpr(Expr.Assign expr) {
    //     return "";
    // }

    // private String spaceDelimiter(String name, Expr... exprs) {
    //     StringBuilder builder = new StringBuilder();

    //     for (Expr expr : exprs) {
    //         builder.append(expr.accept(this));
    //         builder.append(" ");
    //     }
    //     builder.append(name);

    //     return builder.toString();
    // }

    // public static void main(String[] args) {
    //     Expr expression = new Expr.Binary(new Expr.Grouping(new Expr.Binary(new Expr.Literal(1),
    //                                                                         new Token(TokenType.PLUS, "+", null , 1),
    //                                                                         new Expr.Literal(2))),
    //                                       new Token(TokenType.STAR, "*", null, 1),
    //                                       new Expr.Grouping(new Expr.Binary(new Expr.Literal(4),
    //                                                                         new Token(TokenType.MINUS, "-", null , 1),
    //                                                                         new Expr.Literal(3))));
    //         System.out.println(new RpnPrinter().print(expression));
    // }

}
