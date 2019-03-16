package visitor;

import syntaxtree.*;

/** Implements Visitor with trivial methods (all throw an error). */
public class VisitorAdapter<T> implements Visitor<T>  {

    // ProcDecl pd;
    // List<MethodDecl> mds;
    public T visit(Program n) {
        throw new Error("visitor called on unexpected AT node type: " + n);
    }

    // Type t;
    // String id;
    // List<Formal> fs;
    // List<Stm> ss;
    // Exp e;
    public T visit(FunDecl n) {
        throw new Error("visitor called on unexpected AT node type: " + n);
    }

    // String id;
    // List<Formal> fs;
    // List<Stm> ss;
    public T visit(ProcDecl n) {
        throw new Error("visitor called on unexpected AT node type: " + n);
    }

    // Type t;
    // String id;
    public T visit(Formal n) {
        throw new Error("visitor called on unexpected AT node type: " + n);
    }

    public T visit(TypeBoolean n) {
        throw new Error("visitor called on unexpected AT node type: " + n);
    }

    public T visit(TypeInt n) {
        throw new Error("visitor called on unexpected AT node type: " + n);
    }

    public T visit(TypeArray n) {
        throw new Error("visitor called on unexpected AT node type: " + n);
    }

    // List<Stm> ss;
    public T visit(StmBlock n) {
        throw new Error("visitor called on unexpected AT node type: " + n);
    }

    // Type t;
    // String id;
    public T visit(StmVarDecl n) {
        throw new Error("visitor called on unexpected AT node type: " + n);
    }

    // Exp e;
    // StmBlock b1,b2;
    public T visit(StmIf n) {
        throw new Error("visitor called on unexpected AT node type: " + n);
    }

    // Exp e;
    // StmBlock b;
    public T visit(StmWhile n) {
        throw new Error("visitor called on unexpected AT node type: " + n);
    }

    // Exp e;
    public T visit(StmOutput n) {
        throw new Error("visitor called on unexpected AT node type: " + n);
    }

    // Exp e;
    public T visit(StmOutchar n) {
        throw new Error("visitor called on unexpected AT node type: " + n);
    }

    // Var v;
    // Exp e;
    public T visit(StmAssign n) {
        throw new Error("visitor called on unexpected AT node type: " + n);
    }

    // Exp e1,e2,e3;
    public T visit(StmArrayAssign n) {
        throw new Error("visitor called on unexpected AT node type: " + n);
    }

    // String id;
    // List<Exp> es;
    public T visit(StmCall n) {
        throw new Error("visitor called on unexpected AT node type: " + n);
    }

    // String id;
    // List<Exp> es;
    public T visit(ExpCall n) {
        throw new Error("visitor called on unexpected AT node type: " + n);
    }

    // int i;
    public T visit(ExpInteger n) {
        throw new Error("visitor called on unexpected AT node type: " + n);
    }

    public T visit(ExpTrue n) {
        throw new Error("visitor called on unexpected AT node type: " + n);
    }

    public T visit(ExpFalse n) {
        throw new Error("visitor called on unexpected AT node type: " + n);
    }

    // Var v;
    public T visit(ExpVar n) {
        throw new Error("visitor called on unexpected AT node type: " + n);
    }

    // Exp e;
    public T visit(ExpNot n) {
        throw new Error("visitor called on unexpected AT node type: " + n);
    }
    
    // Exp e1, e2;
    // ExpOp.Op op;
    public T visit(ExpOp n) {
        throw new Error("visitor called on unexpected AT node type: " + n);
    }

    // Exp e1,e2;
    public T visit(ExpArrayLookup n) {
        throw new Error("visitor called on unexpected AT node type: " + n);
    }

    // Exp e;
    public T visit(ExpArrayLength n) {
        throw new Error("visitor called on unexpected AT node type: " + n);
    }

    // Type t;
    // Exp e;
    public T visit(ExpNewArray n) {
        throw new Error("visitor called on unexpected AT node type: " + n);
    }

    // Exp e;
    public T visit(ExpIsnull n) {
        throw new Error("visitor called on unexpected AT node type: " + n);
    }
}

