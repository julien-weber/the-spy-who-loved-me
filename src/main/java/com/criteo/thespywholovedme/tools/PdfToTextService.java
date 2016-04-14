package com.criteo.thespywholovedme.tools;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

public class PdfToTextService {

    private static final String[] EXTENSIONS = { "pdf" };

    private File outputDirectory;

    public PdfToTextService(File outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public List<File> convert(File input) {
        if (input.isFile()) {
            File output = convertFile(input);
            return Lists.newArrayList(output);
        } else if (input.isDirectory()) {
            return convertFromDirectory(input);
        } else {
            return Lists.newArrayList();
        }

    }

    private List<File> convertFromDirectory(File inputDirectory) {
        Collection<File> pdfFiles = FileUtils.listFiles(inputDirectory, EXTENSIONS, false);

        List<File> output = Lists.newArrayList();
        for (File pdfFile: pdfFiles) {
            File txtFile = convertFile(pdfFile);
            output.add(txtFile);
        }

        return output;
    }

    private File convertFile(File pdfFile) {

        try (InputStream input = FileUtils.openInputStream(pdfFile)) {
            BodyContentHandler handler = new BodyContentHandler();
            Metadata metadata = new Metadata();
            PDFParser parser = new PDFParser();
            ParseContext parseContext = new ParseContext();

            parser.parse(input, handler, metadata, parseContext);
            File outputFile = new File(outputDirectory, FilenameUtils.getBaseName(pdfFile.getName()) + ".txt");
            FileUtils.write(outputFile, handler.toString(), "UTF-8");
            System.out.println("Created: [" + outputFile.getAbsolutePath() + "]");

            return outputFile;
        } catch (Exception e) {
            throw new RuntimeException("Error converting pdf file [" + pdfFile.getAbsolutePath() + "]", e);
        }

    }
}
