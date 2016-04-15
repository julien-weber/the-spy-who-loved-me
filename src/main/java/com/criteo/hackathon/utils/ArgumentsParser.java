package com.criteo.hackathon.utils;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;

import com.google.common.base.Preconditions;
import org.slf4j.LoggerFactory;

/**
 * Created by z.guo on 4/14/16.
 */
public class ArgumentsParser {

    private final static Logger logger = LoggerFactory.getLogger(ArgumentsParser.class);
    private final static Options __options;

    private static final String INPUT_FILE_OPT = "input-file";
    private static final String INPUT_URL_OPT = "input-url";
    private static final String OUTPUT_DIR_OPT = "output-dir";

    private final String inputFile;
    private final String inputUrl;
    private final String outputDir;

    static {
        __options = new Options();
        addOption(false, "i", INPUT_FILE_OPT, true, "the input file contains linkedin urls");
        addOption(true, "o", OUTPUT_DIR_OPT, true, "the output dir containing crawled profiles");
        addOption(false, "u", INPUT_URL_OPT, true, "the input linkedin url");
    }

    private static void addOption(boolean required
            , String opt, String longOpt, boolean hasArg, String description) {
        Option option = new Option(opt, longOpt, hasArg, description);
        option.setRequired(required);
        __options.addOption(option);
    }

    public static ArgumentsParser parse(Class<?> runner, String[] args) {
        ArgumentsParser parser = null;

        try {
            parser = new ArgumentsParser(args);
        } catch (ParseException pe) {
            logger.error("Failed to parse the arguments:" + pe.getMessage());
            new HelpFormatter().printHelp(runner.getSimpleName(), __options);
            System.exit(0);
        }

        return parser;
    }

    public ArgumentsParser(String[] args) throws ParseException {
        Preconditions.checkNotNull(args, "Null arguments");

        CommandLine cmd = new BasicParser().parse(__options, args);

        String optVal;

        optVal = cmd.getOptionValue("input-file");
        this.inputFile = null == optVal ? null : optVal;

        optVal = cmd.getOptionValue("output-dir");
        this.outputDir = null == optVal ? null : optVal;

        optVal = cmd.getOptionValue("input-url");
        this.inputUrl = null == optVal ? null : optVal;
    }

    public String getInputFile() {
        return inputFile;
    }

    public String getOutputDir() {
        return outputDir;
    }

    public String getInputUrl() {
        return inputUrl;
    }

}
