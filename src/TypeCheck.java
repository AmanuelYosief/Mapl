
import parser.TokenMgrError;
import parser.ParseException;
import parser.MaplParser;
import staticanalysis.TypeChecker;
import staticanalysis.SymbolTable;
import staticanalysis.SymbolTableBuilder;
import syntaxtree.*;
import staticanalysis.StaticAnalysisException;

/**
 * A harness to run the type checker.
 */
public class TypeCheck {
    
    private TypeCheck(){}

    public static void main(String[] args) {
        Program root;
        try {
            System.out.print("Parsing...");
            System.out.flush();
            if (args.length == 0) {
                // Read program to be parsed from standard input
                root = new MaplParser(System.in).nt_Program();
            } else {
                // Read program to be parsed from file
                try {
                    root = new MaplParser(new java.io.FileInputStream(args[0])).nt_Program();
                } catch (java.io.FileNotFoundException e) {
                    System.err.println("Unable to read file " + args[0]);
                    return;
                }
            }
            System.out.println("...parsed OK.");
            //root.accept(new PrettyPrinter());
            SymbolTableBuilder stvisit = new SymbolTableBuilder();
            System.out.print("Building Symbol Table...");
            System.out.flush();
            root.accept(stvisit);
            System.out.println("...done");
            SymbolTable symTab = stvisit.getSymTab();
            System.out.print("Type checking...");
            System.out.flush();
            root.accept(new TypeChecker(symTab));
            System.out.println("...type checked OK.");
        } catch (TokenMgrError e) {
            System.out.println("\nLexical error: "+e.getMessage());
        } catch (ParseException e) {
            System.out.println("\nSyntax error: "+e.getMessage());
        } catch (StaticAnalysisException e) {
            System.out.println("\nStatic semantics error: "+e.getMessage());
        } catch (Throwable e) {
            System.out.println("Unexpected exception:");
            e.printStackTrace();
        }
    }
}
