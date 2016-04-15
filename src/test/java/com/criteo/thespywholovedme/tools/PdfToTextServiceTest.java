package com.criteo.thespywholovedme.tools;

import org.junit.Test;

import java.io.File;
import java.util.List;

public class PdfToTextServiceTest {

    private String outputPath = "target/pdfToTextOutput/";

    @Test
    public void testPdfToText() throws Exception {
        File inputFile = new File("src/test/resources/pdfs");

        PdfToTextService pdfToTextService = new PdfToTextService(outputPath);
        List<File> outputFiles = pdfToTextService.convert(inputFile);
    }
}
