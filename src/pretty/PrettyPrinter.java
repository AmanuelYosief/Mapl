package pretty;

import java.io.PrintStream;
import syntaxtree.*;
import visitor.Visitor;
import java.util.Deque;
import java.util.List;
import java.util.LinkedList;

public class PrettyPrinter implements Visitor<Void> {

    private int indent;
    private static final String INDENT = "  ";
    private Deque<Boolean> bracketing;
    private PrintStream ps;

    /**
     * Initialise a new pretty printer.
     */
    public PrettyPrinter() {
        this(System.out);
    }

    /**
     * Initialise a new pretty printer.
     */
    public PrettyPrinter(PrintStream ps) {
        this.ps = ps;
        indent = 0;
        bracketing = new LinkedList<Boolean>();
        bracketing.push(false);
    }

    /** Start a new line of output. */
    private void newline() {
        ps.println();
    }

    /** Print a string prefixed by current indent whitespace. */
    private void iprint(String s) {
        indent();
        ps.print(s);
    }

    /** Print a string prefixed by current indent whitespace, followed by a newline. */
    private void iprintln(String s) {
        iprint(s);
        newline();
    }

    /** Print a string. */
    private void print(String s) {
        ps.print(s);
    }

    /** Print a string, followed by a newline. */
    private void println(String s) {
        ps.println(s);
    }

    /** Print current indent of whitespace. */
    private void indent() {
        for (int i = 0; i < indent; i++) {
            ps.print(INDENT);
        }
    }
    
    /** Print opening bracket if currently bracketing. */
    private void openBracket() {
        if (bracketing.peek()) print("(");
    }
    
    /** Print closing bracket if currently bracketing. */
    private void closeBracket() {
        if (bracketing.peek()) print(")");
    }
    
    private void prettyPrintActuals(List<Exp> actuals) {
        print("(");
        bracketing.push(false);
        for (int i = 0; i < actuals.size(); i++) {
            actuals.get(i).accept(this);
            if (i + 1 < actuals.size()) {
                print(", ");
            }
        }
        bracketing.pop();
        print(")");
    }
    
    private void prettyPrintMethodCall(String id, List<Exp> actuals) {
        openBracket();
        print(id);
        prettyPrintActuals(actuals);
        closeBracket();
    }

    // ProcDecl pd;
    // List<FunDecl> fds;
    public Void visit(Program n) {
        n.pd.accept(this);
        for (MethodDecl md : n.mds) {
            newline();
            md.accept(this);
        }
        return null;
    }

    // Type t;
    // String id;
    // List<Formal> fs;
    // List<Stm> ss;
    // Exp e;
    public Void visit(FunDecl n) {
        iprint("fun ");
        n.t.accept(this);
        print(" " + n.id + "(");
        for (int i = 0; i < n.fs.size(); i++) {
            n.fs.get(i).accept(this);
            if (i + 1 < n.fs.size()) {
                print(", ");
            }
        }
        println(") {");
        indent++;
        for (Stm s : n.ss) {
            s.accept(this);
        }
        iprint("return ");
        n.e.accept(this);
        println(";");
        indent--;
        iprintln("}");
        return null;
    }

    // String id;
    // List<Formal> fs;
    // List<Stm> ss;
    public Void visit(ProcDecl n) {
        iprint("proc");
        print(" " + n.id + "(");
        for (int i = 0; i < n.fs.size(); i++) {
            n.fs.get(i).accept(this);
            if (i + 1 < n.fs.size()) {
                print(", ");
            }
        }
        println(") {");
        indent++;
        for (Stm s : n.ss) {
            s.accept(this);
        }
        indent--;
        iprintln("}");
        return null;
    }

    // Type t;
    // String id;
    public Void visit(Formal n) {
        n.t.accept(this);
        print(" " + n.id);
        return null;
    }

    // Type t
    public Void visit(TypeArray n) {
        print("arrayof(");
        n.t.accept(this);
        print(")");
        return null;
    }

    public Void visit(TypeBoolean n) {
        print("boolean");
        return null;
    }

    public Void visit(TypeInt n) {
        print("int");
        return null;
    }

    // List<Stm> ss;
    public Void visit(StmBlock n) {
        iprintln("{");
        indent++;
        for (Stm s : n.ss) {
            s.accept(this);
        }
        indent--;
        iprintln("}");
        return null;
    }
    
    // Type t
    // String id
    public Void visit(StmVarDecl n) {
        iprint("");
        n.t.accept(this);
        println(" " + n.id + ";");
        return null;
    }

    // Exp e;
    // StmBlock b1,b2;
    public Void visit(StmIf n) {
        iprint("if (");
        n.e.accept(this);
        println(") then");
        n.b1.accept(this);
        iprintln("else");
        n.b2.accept(this);
        return null;
    }

    // Exp e;
    // Block b;
    public Void visit(StmWhile n) {
        iprint("while (");
        n.e.accept(this);
        println(") do");
        n.b.accept(this);
        return null;
    }

    // Exp e;
    public Void visit(StmOutput n) {
        iprint("output ");
        n.e.accept(this);
        println(";");
        return null;
    }

    // Exp e;
    public Void visit(StmOutchar n) {
        iprint("outchar ");
        n.e.accept(this);
        println(";");
        return null;
    }

    // Var v;
    // Exp e;
    public Void visit(StmAssign n) {
        iprint(n.v.id + " = ");
        //iprint(n.v.id + "(" + n.v.offset + "," + n.v.isField + ")" + " = ");
        n.e.accept(this);
        println(";");
        return null;
    }

    // Exp e1,e2,e3;
    public Void visit(StmArrayAssign n) {
        bracketing.push(true);
        iprint("");
        n.e1.accept(this);
        bracketing.pop();
        print("[");
        n.e2.accept(this);
        print("] = ");
        n.e3.accept(this);
        println(";");
        return null;
    }

    // Exp e;
    // String id;
    // List<Exp> es;
    public Void visit(StmCall n) {
        iprint("");
        prettyPrintMethodCall(n.id, n.es);
        println(";");
        return null;
    }

    // Exp e;
    // String id;
    // List<Exp> es;
    public Void visit(ExpCall n) {
        prettyPrintMethodCall(n.id, n.es);
        return null;
    }

    // int i;
    public Void visit(ExpInteger n) {
        print("" + n.i);
        return null;
    }

    public Void visit(ExpTrue n) {
        print("true");
        return null;
    }

    public Void visit(ExpFalse n) {
        print("false");
        return null;
    }

    // Var v;
    public Void visit(ExpVar n) {
        print(n.v.id);
        //print(n.v.id + "(" + n.v.offset + "," + n.v.isField + ")");
        return null;
    }

    // Exp e;
    public Void visit(ExpNot n) {
        print("!");
        bracketing.push(true);
        n.e.accept(this);
        bracketing.pop();
        return null;
    }

    // Exp e1, e2;
    // ExpOp.Op op;
    public Void visit(ExpOp n) {
        openBracket();
        bracketing.push(true);
        n.e1.accept(this);
        print(" " + n.op + " ");
        n.e2.accept(this);
        bracketing.pop();
        closeBracket();
        return null;
    }

    // Exp e1,e2;
    public Void visit(ExpArrayLookup n) {
        openBracket();
        bracketing.push(true);
        n.e1.accept(this);
        bracketing.pop();
        print("[");
        bracketing.push(false);
        n.e2.accept(this);
        bracketing.pop();
        print("]");
        closeBracket();
        return null;
    }

    // Exp e;
    public Void visit(ExpArrayLength n) {
        openBracket();
        bracketing.push(true);
        n.e.accept(this);
        bracketing.pop();
        print(".length");
        closeBracket();
        return null;
    }

    // Type t;
    // Exp e;
    public Void visit(ExpNewArray n) {
        print("new arrayof(");
        n.t.accept(this);
        print(")[");
        bracketing.push(false);
        n.e.accept(this);
        bracketing.pop();
        print("]");
        return null;
    }

    // Exp e;
    public Void visit(ExpIsnull n) {
        print("isnull ");
        bracketing.push(true);
        n.e.accept(this);
        bracketing.pop();
        return null;
    }
}
