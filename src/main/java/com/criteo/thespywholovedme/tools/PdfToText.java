package com.criteo.thespywholovedme.tools;

import java.io.File;

public class PdfToText {

    public static void main(String[] args) throws Exception {

        if(args.length != 2) {
            System.out.println("usage: {inputDirectory} {outputDirectory}");
            System.exit(1);
        }

        String inputDirectory = args[0];
        String outputDirectory = args[1];

        PdfToTextService pdfToTextService = new PdfToTextService(new File(outputDirectory));

        pdfToTextService.convert(new File(inputDirectory));
    }
}
