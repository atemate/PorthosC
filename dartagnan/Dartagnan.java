package dartagnan;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.io.FileUtils;

import com.microsoft.z3.Context;
import com.microsoft.z3.Solver;
import com.microsoft.z3.Status;
import com.microsoft.z3.Z3Exception;
import com.microsoft.z3.enumerations.Z3_ast_print_mode;

import dartagnan.program.Program;
import dartagnan.wmm.Domain;
import dartagnan.wmm.Wmm;
import java.util.logging.Logger;

import org.apache.commons.cli.*;

@SuppressWarnings("deprecation")
public class Dartagnan {
    private static final Logger log = Logger.getLogger(Dartagnan.class.getName());
    public static void main(String[] args) throws Z3Exception, IOException {

        List<String> MCMs = Arrays.asList("sc", "tso", "pso", "rmo", "alpha", "power", "arm");

        Options options = new Options();

        Option targetOpt = new Option("t", "target", true, "target MCM");
        targetOpt.setRequired(true);
        options.addOption(targetOpt);

        Option inputOpt = new Option("i", "input", true, "input file path");
        inputOpt.setRequired(true);
        options.addOption(inputOpt);

        Option fileOpt = new Option("f", "file", true, "target MCM file");
        fileOpt.setRequired(false);
        options.addOption(fileOpt);

        CommandLineParser parserCmd = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;
        
Wmm mcm;
        try {
            cmd = parserCmd.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("DARTAGNAN", options);
            System.exit(1);
            return;
        }

        String target = cmd.getOptionValue("target");
        if (!MCMs.stream().anyMatch(mcms -> mcms.trim().equals(target))) {
            System.out.println("Unrecognized target");
            System.exit(0);
            return;
        }

        String inputFilePath = cmd.getOptionValue("input");
        if (!inputFilePath.endsWith("pts") && !inputFilePath.endsWith("litmus")) {
            System.out.println("Unrecognized program format");
            System.exit(0);
            return;
        }
        File file = new File(inputFilePath);

        String program = FileUtils.readFileToString(file, "UTF-8");
        ANTLRInputStream input = new ANTLRInputStream(program);

        Program p = new Program(inputFilePath);

        if (inputFilePath.endsWith("litmus")) {
            LitmusLexer lexer = new LitmusLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            LitmusParser parser = new LitmusParser(tokens);
            p = parser.program(inputFilePath).p;
        }

        if (inputFilePath.endsWith("pts")) {
            PorthosLexer lexer = new PorthosLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            PorthosParser parser = new PorthosParser(tokens);
            p = parser.program(inputFilePath).p;
        }
        mcm=Wmm.getWmm(target);

        if (cmd.hasOption("file")) {
            String filePath = cmd.getOptionValue("file");
            if (!filePath.endsWith("cat")) {
                System.out.println("Unrecognized memory model format");
                System.exit(0);
                return;
            }
            File modelfile = new File(filePath);

            String mcmtext = FileUtils.readFileToString(modelfile, "UTF-8");
            ANTLRInputStream mcminput = new ANTLRInputStream(mcmtext);
            ModelLexer lexer = new ModelLexer(mcminput);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            ModelParser parser = new ModelParser(tokens);
            //System.out.println((parser.mcm()).getText());
            //System.out.println(parser.mcm());
            mcm=parser.mcm().value;
        } else {

        }

        p.initialize();
        p.compile(target, false, true);

        Context ctx = new Context();
        Solver s = ctx.mkSolver();

        s.add(p.encodeDF(ctx));
        s.add(p.ass.encode(ctx));
        s.add(p.encodeCF(ctx));
        s.add(p.encodeDF_RF(ctx));
        s.add(Domain.encode(p, ctx));
        if(mcm!=null){
                        log.warning(mcm.write());

                s.add(mcm.encode(p, ctx));
                s.add(mcm.Consistent(p, ctx));          
        }else{
            log.info("Using static model.");
        s.add(p.encodeMM(ctx, target));
        s.add(p.encodeConsistent(ctx, target));              
        }


        ctx.setPrintMode(Z3_ast_print_mode.Z3_PRINT_SMTLIB_FULL);

        if (s.check() == Status.SATISFIABLE) {
            System.out.println("       0");
        } else {
            System.out.println("       1");
        }

    }

}
