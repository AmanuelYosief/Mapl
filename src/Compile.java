
import parser.TokenMgrError;
import parser.ParseException;
import parser.MaplParser;
import cloptions.CLOptions;
import syntaxtree.*;
import staticanalysis.*;
import pretty.PrettyPrinter;
import visitor.Visitor;
import compiler.Compiler;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * A harness to run the compiler.
 */
public class Compile {

    private static boolean source = false, usedef = true, type = true, quiet = false;
    
    private Compile(){}

    /**
     * Compile Mapl source code to IR code.
     * <p>
     * Command line arguments: Mapl source file name
     * <p>
     * Options: <ul>
     * <li> -notype (disable type checking)
     * <li> -source (pretty-print parsed input)
     * <li> -quiet (suppress progress messages)
     * <li> -nousedef (disable check for uninitialised local variables)
     * </ul>
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        List<String> argList = new ArrayList<>(Arrays.asList(args));
        Set<String> options = CLOptions.options(argList, "notype", "source", "quiet", "nousedef");
        type = !options.contains("notype");
        source = options.contains("source");
        quiet = options.contains("quiet");
        usedef = !options.contains("nousedef");
        Program root;
        try {
            String inputFileName = "-";
            report("Parsing...");
            System.out.flush();
            if (argList.size() == 0) {
                // Read program to be parsed from standard input
                report("(reading from standard input)\n");
                System.out.flush();
                root = new MaplParser(System.in).nt_Program();
            } else {
                // Read program to be parsed from file
                inputFileName = argList.get(0);
                try {
                    root = new MaplParser(new java.io.FileInputStream(inputFileName)).nt_Program();
                } catch (java.io.FileNotFoundException e) {
                    System.err.println("Unable to read file " + inputFileName);
                    return;
                }
            }
            reportln("...parsed OK.");
            SymbolTable symTab;
            {
                SymbolTableBuilder stvisit = new SymbolTableBuilder();
                report("Building Symbol Table...");
                System.out.flush();
                root.accept(stvisit);
                reportln("...done");
                symTab = stvisit.getSymTab();
            }
            if (type) {
                TypeChecker typeChecker = new TypeChecker(symTab);
                report("Type checking...");
                System.out.flush();
                root.accept(typeChecker);
                reportln("...type checked OK.");
            }
            if (usedef) {
                report("Checking local variable def-before-use...");
                UseDefChecker udChecker = new UseDefChecker();
                root.accept(udChecker);
                reportln("...OK.");
            }
            {
                report("Allocating variables...");
                System.out.flush();
                Visitor<Void> varAllocator = new staticanalysis.VarAllocator(symTab);
                root.accept(varAllocator);
                reportln("...done.");
            }
            if (source) {
                root.accept(new PrettyPrinter());
            }
            Compiler compiler = new Compiler();
            String irText = root.accept(compiler);
            String outputFileName = "out.ir";
            if (!"-".equals(inputFileName)) {
                outputFileName = inputFileName.split("\\.")[0] + ".ir";
            }
            reportln("Writing IR code to " + outputFileName);
            output(outputFileName, irText);
        } catch (ParseException | TokenMgrError e) {
            System.out.println("\nSyntax error: " + e.getMessage());
        } catch (StaticAnalysisException e) {
            System.out.println("\nStatic semantics error: " + e.getMessage());
        }
    }

    private static void output(String fileName, String text) {
        try (Writer w = new FileWriter(fileName)) {
            w.write(text);
        } catch (IOException e) {
            System.err.println(e);
            throw new Error("Errror writing to file: " + fileName);
        }
    }

    private static void report(String msg) {
        if (!quiet) {
            System.out.print(msg);
            System.out.flush();
        }
    }

    private static void reportln(String msg) {
        report(msg + "\n");
    }
}
