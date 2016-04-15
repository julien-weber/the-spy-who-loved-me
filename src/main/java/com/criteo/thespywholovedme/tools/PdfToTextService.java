package com.criteo.thespywholovedme.tools;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
public class PdfToTextService {

    private static final String[] EXTENSIONS = { "pdf", "doc", "docx", "txt", "output" };

    @Value("${pdf-2-txt.txt_output_directory}")
    private String outputDirectory;

    public PdfToTextService() {

    }

    public PdfToTextService(String outputDirectory) {
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
            AutoDetectParser parser = new AutoDetectParser();
            // PDFParser parser = new PDFParser();
            ParseContext parseContext = new ParseContext();

            parser.parse(input, handler, metadata, parseContext);
            File outputFile = new File(outputDirectory, FilenameUtils.getBaseName(pdfFile.getName()) + ".txt");
            FileUtils.write(outputFile, makeAnonymous(handler.toString()), "UTF-8");
            System.out.println("Created: [" + outputFile.getAbsolutePath() + "]");

            return outputFile;
        } catch (Exception e) {
            throw new RuntimeException("Error converting pdf file [" + pdfFile.getAbsolutePath() + "]", e);
        }

    }
    
    private String makeAnonymous(String initial) {
    	return initial
    			.replaceAll("Benoit DELAYEN.*\\n.*\\n.*\\n.*free\\.fr", "")
    			.replaceAll("Elena Smirnova.*\\n.*\\n.*\\n.*2075", "")
    			.replaceAll("Guillaume Bort.*\\n.*\\n.*bort\\.fr", "")
    			.replaceAll("Damien Lefortier.*", "")
    			.replaceAll("E-mail.*\\n.*87", "")
    			.replaceAll("Szehon Ho.*\\n.*\\n.*\\n.*\\n.*9961", "")
    			.replaceAll("Yoann Aubineau\\n.*\\n.*\\n.*gmail\\.com", "")
    			.replaceAll("Contacts.*\\n.*\\n.*\\n.*\\n.*\\n.*\\n.*41", "")
    			.replaceAll("Lucas Bruand", "")
    			.replaceAll(".*P. Moutoussamy.*", "")
    			.replaceAll("20 ann.*\\n.*gmail\\.com", "")
    			.replaceAll("François PAYS.*\\n.*\\n.*\\n.*\\n.*\\n.*nationality", "")
    			.replaceAll("Guillaume Lemoine.*\\n.*France", "")
    			.replaceAll(".*Philippe.*BRIEND.*\\n.*\\n.*\\n.*\\n.*\\n.*gmail\\.com", "");
    }
}
