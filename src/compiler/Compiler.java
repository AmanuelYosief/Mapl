package compiler;

import visitor.VisitorAdapter;
import syntaxtree.*;

// Use makeLabel to generate fresh labels for jump targets.
import static compiler.FreshLabelGenerator.makeLabel;

/**
 * Prototype Mapl compiler.
 */
public class Compiler extends VisitorAdapter<String> {
    //Visitor adapers throws back error, handy so we can progressively override with implementations that are useful
    //step by step. 
    public Compiler() {}

    private String seq(String l, String r) {
        return "SEQ(" + l + ", " + r + ")";
    }

    @Override
    public String visit(Program n) { // In the first version, not worry about methods
        // A Mapl consists of a bunch of method definitions, usually called main. 
        // The visit method assumes that the program is a simple main method with no arguements.
        // and so this loop compiles just the main body of that method and assumes, 
        // that there is nothing else to worry in the program
        
        // The body main method is a sequence of statements run one after the other.
        // Visit methods are defined mutually recusrive, and so we are expected to visit method 
        // of a program to call other vistor methods. 
        // expect we got s.accept(this). What is "s"?. S is a statement in the body of the main method
        // We got a sequence of statements and want to compile them, so we call visit on them, expect we call accept. 
        
        // We use accept to glue all the compiled code into a valid IR statement using SEQ nodes. 
        // SEQ is just a convinence method, takes two strings which IR statement strings 
        // and build a new one by turning it into a SEQ node. Its just to glue things in a sequences
        
        String ir = "NOOP";
        for (Stm s : n.pd.ss) {
            ir = seq(ir, s.accept(this));
        }
        return ir;
    }

    /*==========================================================*/
    /* Expression visitors (all return an IR expression string) */
    /*==========================================================*/
    
    // Implementing a visitor method. E.g. A visit method for integer constants, that is an experssion 
    // the AST for an integer is simple. It just contains an integer field. 
    /*  
    public ExpInteger(int ai) {
        i = ai;
    }
      */  
    // The translation for the IR code of an Integer constant. (What is the IR instructions -> Const)
        // IEEXP -> CONST INT 
    // so we return an CONST
    // The integer is stored in the field called i
    // this is an easy case
    
    @Override
    public String visit(ExpInteger n) {
        return "CONST " + n.i;
    }
    
    @Override
    public String visit(ExpTrue n) { return "CONST 1";}
    
    @Override
    public String visit(ExpFalse n) { return "CONST 0";}
    
    @Override
    public String visit(ExpCall n){
        return "CALL(NAME " + n.id + ", " + n.es + ")";
    }
    
    @Override
    public String visit(ExpVar n){
        return "TEMP " + n.v;
    }
    
    @Override
    public String visit(ExpOp n){
        
    switch (n.op.name()) {
                    case "PLUS":
                        return "BINOP(" + n.e1.accept(this) + "," + "ADD" + "," + n.e2.accept(this) + ")";
                    case "MINUS":                   
                        return "BINOP(" + n.e1.accept(this) + "," + "SUB" + "," + n.e2.accept(this) + ")";
                    case "TIMES":
                        return "BINOP(" + n.e1.accept(this) + "," + "MUL" + "," + n.e2.accept(this) + ")";
                    case "DIV":
                        return "BINOP(" + n.e1.accept(this) + "," + "DIV" + "," + n.e2.accept(this) + ")";
                    case "LESSTHAN":
                        return "BINOP(" + n.e1.accept(this) + "," + "LT" + "," + n.e2.accept(this) + ")";
                    case "EQUALS":
                        return "BINOP(" + n.e1.accept(this) + "," + "EQ" + "," + n.e2.accept(this) + ")";
                    // AND
                        // LESS THAN EQUAL TO
                    default:
                        return "NOOP";
                }
    }

    /*========================================================*/
    /* Statement visitors (all return an IR statement string) */
    /*========================================================*/
  
    @Override
    public String visit(StmVarDecl n) {
        return "NOOP";
    }
    
    @Override
    public String visit(StmOutchar n) {
        return "EXP(CALL(NAME _printchar, " + n.e.accept(this)+ "))";
        // visit for stmOutChar, but will use method _printChar instead of print_int, it will print hello
    }
    
    @Override
    public String visit(StmOutput n) {
        return "EXP(CALL(NAME _printint, " + n.e.accept(this) + "))";
    } 
    
    @Override
    public String visit(StmAssign n){
        return "MOVE(TEMP " + n.v + "," + n.e.accept(this) + ")";
    }
    
    // <editor-fold>
    /*
    // An output statement in Maple. Is something that takes an integer expression
    and prints it, there is no IR instructions for printing integer. But 
    there is a predefined printint method, which we can use. 
    We are going to call it, using a IR call instruction but remember 
    we have to wrap it up around an Stm, as a call is a expression and we need a statement. 
    return "EXP(CALL ("  + ")";
   
    the first arguement is the name/address of the method we want to call. Which is _printint, 
    then we provide the integer we want to print. 
    
    The integer we want to print, is the value of the expression that is stored in this statement output tree
    this is called e (n.e - is the IR complication of e).
    its just e,that we need to compile. N.e into IR code.
    
    the way we compile n.e is to use one of the visit expression? however which one?
    n.e. could be a boolean value, a method calls, a binop addition. Which visitor method do we use?
    
    Java does provide an instant method build in, to see what kind of tree it is. However instead
    we are going to use n.e.accept(this).
    In every AST classes there is an accept method. It takes a visitor and calls it back.
    The accept method know what kind of AST classes they are so they will call the appropriate visit method themselves. 
    
    To make a recursive call, use this patterns
    
    N.E.ACCEPT(THIS) 
    N.ACCEPT(THIS) is a recursive call and is a infinite call, which will cause the compiler to crash
    
        public StmOutput(Exp ae) {
        e = ae;
    }

    */
    
    /*
    Compiling StmCall to IR. 
    This is the same (compiling ExpCall to IR) but wrapped in a Exp(..)node
    Call() is an IRExp, but the complication of an StmCall must be an IRStm, not an IRExp
    Exp(e) converts e from an IRExp to an IRStm
    
        public StmCall(String aid, List<Exp> aes) {
        id = aid;
        es = aes;
    }

       public T visit(StmCall n);
    */
        // </editor-fold>
    @Override
    public String visit(StmCall n){
        return "EXP(CALL(NAME" + n.id + "," + n.es + "))";
    }
    
    
}