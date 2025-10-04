package com.craftinginterpreters.lox;

import java.util.List;
import java.util.StringJoiner;

class AstPrinter implements Expr.Visitor<String>, Stmt.Visitor<String> {
    private String indent = "";

    String print(List<Stmt> statements) {
        return listStmt(statements);
    }

    @Override
    public String visitBlockStmt(Stmt.Block stmt) {
        return listStmt(stmt.statements);
    }

    @Override
    public String visitClassStmt(Stmt.Class stmt) {
        String previous = this.indent;
        String Class = this.indent + "(class " + stmt.name.lexeme + "\n";
        this.indent = this.indent + "  ";
        Class += listStmt(stmt.methods);
        Class += ")";
        this.indent = previous;
        return Class;
    }

    @Override
    public String visitExpressionStmt(Stmt.Expression stmt) {
        return this.indent + stmt.expression.accept(this);
    }

    @Override
    public String visitIfStmt(Stmt.If stmt) {
        String prvious = this.indent;
        String statements = this.indent + "(if " + stmt.condition.accept(this) + "\n";
        this.indent = prvious + "  ";
        statements = statements + this.indent + stmt.thenBranch.accept(this);
        if (stmt.elseBranch != null) {
            statements = statements + "\n" + this.indent + stmt.elseBranch.accept(this);
        }
        this.indent = prvious;
        return statements  + ")";
    }

    @Override
    public String visitPrintStmt(Stmt.Print stmt) {
        return this.indent + parenthesize("print", stmt.expression);
    }

    @Override
    public String visitReturnStmt(Stmt.Return stmt) {
        if (stmt.value != null) {
            return this.indent + parenthesize("return", stmt.value);
        }
        return this.indent + "(return)";
    }

    @Override
    public String visitWhileStmt(Stmt.While stmt) {
        String prvious = this.indent;
        this.indent = this.indent + "  ";
        StringBuilder builder = new StringBuilder();
        builder.append(prvious);
        builder.append("(while ").append(stmt.condition.accept(this)).append("\n");
        builder.append(stmt.body.accept(this)).append(")");
        this.indent = prvious;
        return builder.toString();
    }

    @Override
    public String visitBreakStmt(Stmt.Break stmt) {
        return this.indent + "(break)";
    }

    @Override
    public String visitVarStmt(Stmt.Var stmt) {
        if (stmt.initializer != null) {
            return this.indent + parenthesize("var " + stmt.name.lexeme, stmt.initializer);
        }
        return this.indent + "(var " + stmt.name.lexeme + ")";
    }

    @Override
    public String visitFunctionStmt(Stmt.Function stmt) {
        String defun = this.indent + "(defun " + stmt.name.lexeme + " (";
        for (Token param : stmt.params) {
            defun += param.lexeme + " ";
        }
        defun = defun.stripTrailing();
        defun += ")\n";
        String previous = this.indent;
        this.indent += "  ";
        defun += listStmt(stmt.body);
        defun += ")";
        this.indent = previous;
        return defun;
    }

    @Override
    public String visitCallExpr(Expr.Call expr) {
        String arguments = " ";
        for (Expr arg : expr.arguments){
            arguments += arg.accept(this);
        }
        return "(" + expr.callee.accept(this) + arguments.stripTrailing() + ")";
    }

    @Override
    public String visitGetExpr(Expr.Get expr) {
        return  "(" + expr.object.accept(this) + " . " + expr.name.lexeme + ")";
    }

    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        return parenthesize(expr.operator.lexeme, expr.left, expr.right);
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

    @Override
    public String visitSetExpr(Expr.Set expr) {
        return "(set (" + expr.object.accept(this) + " . " + expr.name.lexeme + ") " + expr.value.accept(this) + ")";
    }

    @Override
    public String visitThisExpr(Expr.This expr) {
        return expr.keyword.lexeme;
    }

    @Override
    public String visitLambdaExpr(Expr.Lambda expr) {
        String defun = this.indent + "(lambda (";
        for (Token param : expr.fun.params) {
            defun += param.lexeme + " ";
        }
        defun = defun.stripTrailing();
        defun += ")\n";
        String previous = this.indent;
        this.indent += " ";
        defun += listStmt(expr.fun.body);
        defun += ")";
        this.indent = previous;
        return defun;
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

    private String listStmt(List<? extends Stmt> stmt) {
        String statements = "";
        for (int i=0; i < stmt.size(); i++) {
            statements += stmt.get(i).accept(this);
            if (i != stmt.size() - 1) {
                statements += "\n";
            }
        }
        return statements;
    }

    public static void main(String[] args) {
    }
}
