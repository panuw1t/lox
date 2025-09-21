package com.craftinginterpreters.lox;

import java.util.List;

class AstPrinter implements Expr.Visitor<String>, Stmt.Visitor<String> {
    private String indent = "";

    String print(List<Stmt> statements) {
        String s = "";
        for (Stmt statement : statements) {
            s = s + statement.accept(this) + "\n";
        }
        return s;
    }

    @Override
    public String visitBlockStmt(Stmt.Block stmt) {
        String statements = "";
        String prvious = this.indent;
        this.indent = prvious + "  ";
        for (Stmt statement : stmt.statements) {
            statements = statements + this.indent + statement.accept(this) + "\n";
        }
        this.indent = prvious;
        return statements;
    }

    @Override
    public String visitExpressionStmt(Stmt.Expression stmt) {
        return stmt.expression.accept(this);
    }

    @Override
    public String visitIfStmt(Stmt.If stmt) {
        String prvious = this.indent;
        this.indent = prvious + "  ";
        String statements = "(if " + stmt.condition.accept(this) + "\n";
        statements = statements + this.indent + stmt.thenBranch.accept(this);
        if (stmt.elseBranch != null) {
            statements = statements + "\n" + this.indent + stmt.elseBranch.accept(this);
        }
        this.indent = prvious;
        return statements  + ")";
    }

    @Override
    public String visitPrintStmt(Stmt.Print stmt) {
        return parenthesize("print", stmt.expression);
    }

    @Override
    public String visitVarStmt(Stmt.Var stmt) {
        if (stmt.initializer != null) {
            return parenthesize("var " + stmt.name.lexeme, stmt.initializer);
        }
        return "(var " + stmt.name.lexeme + ")";
    }

    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        return parenthesize(expr.operator.lexeme,
                            expr.left, expr.right);
    }

    @Override
    public String visitGroupingExpr(Expr.Grouping expr) {
        return parenthesize("group", expr.expression);
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        if (expr.value == null) return "nil";
        return expr.value.toString();
    }

    @Override
    public String visitUnaryExpr(Expr.Unary expr) {
        return parenthesize(expr.operator.lexeme, expr.right);
    }

    @Override
    public String visitTernaryExpr(Expr.Ternary expr) {
        return parenthesize("?:", expr.condition, expr.ifTrue, expr.ifFalse);
    }

    @Override
    public String visitVariableExpr(Expr.Variable expr) {
        return expr.name.lexeme;
    }

    @Override
    public String visitAssignExpr(Expr.Assign expr) {
        return parenthesize("assign " + expr.name.lexeme, expr.value);
    }
    @Override
    public String visitLogicalExpr(Expr.Logical expr){
        return parenthesize(expr.operator.lexeme, expr.left, expr.right);
    }

    private String parenthesize(String name, Expr... exprs) {
        StringBuilder builder = new StringBuilder();

        builder.append("(").append(name);
        for (Expr expr : exprs) {
            builder.append(" ");
            builder.append(expr.accept(this));
        }
        builder.append(")");

        return builder.toString();
    }

    public static void main(String[] args) {
    }
}
