package com.criteo.thespywholovedme;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.criteo.thespywholovedme.prediction.Prediction;
import com.criteo.thespywholovedme.prediction.PredictionTest;
import com.criteo.thespywholovedme.tokenizer.Processor;
import com.criteo.thespywholovedme.tools.PdfToTextService;

public class EndToEndTest {

    static Logger log = LoggerFactory.getLogger(EndToEndTest.class);

    @Test
    public void endToEndTest() {
        String resumeToPredictStr = "target/txts/NewCadidate.pdf";

        // Training:
        Processor processor = Training();

        // Make a prediction
        // TODO integrate with ML
        List<Double> weight = Arrays.asList(0.1, 0.5, 1.0);
        // List<Double> weight = processor.GetWeight();
        List<Double> XWithTfIdf = Arrays.asList(1.0, 1.0, 2.0);
        // File resumeToPredict = new File(resumeToPredictStr);
        // List<Double> XWithTfIdf = processor.GetXWithTfIdf(resumeToPredict);
        MakePrediction(weight, XWithTfIdf);
    }

    @Test
    public void PredictTest()
    {
    	 //String resumeToPredictStr = "target/txts/JonWuTechResume.pdf";
    	 String resumeToPredictStr = "src/test/resources/pdfs/JonWuTechResume.pdf";


    	 String outputPath = "target/txts";
         File resumeToPredict = new File(resumeToPredictStr);
         PdfToTextService pdfToTextService = new PdfToTextService(outputPath);
         List<File> convertedResumes = pdfToTextService.convert(resumeToPredict);
         File convertedResume = convertedResumes.get(0);

    	 Processor processor = new Processor();
    	 List<Double> weight = Arrays.asList(0.1, 0.5, 1.0);
    	 //List<Double> weight = processor.GetWeight();

    	 List<Double> XWithTfIdf = Arrays.asList(1.0, 1.0, 2.0);
    	 //List<Double> XWithTfIdf = processor.GetXWithTfIdf(convertedResume);

    	 Prediction prediction = Prediction.getInstance();
         double finalScore = prediction.getFinalScore(weight, XWithTfIdf);
         log.info("the final score is " + finalScore);
         if (finalScore > 0.5) {
             log.info("Candidate is hired");
         } else {
             log.info("Candidate is not hired");
         }

    }

    private Processor Training() {
        // 1. Convert Files from Pdf to Text
        String outputPathGoodResumes = "target/txts/positive";
        File goodResumesPdf = new File("src/test/resources/pdfs/positive");
        PdfToTextService pdfToTextServiceGoodResumes = new PdfToTextService(outputPathGoodResumes);
        List<File> goodResumesInText = pdfToTextServiceGoodResumes.convert(goodResumesPdf);

        String outputPathBadResumes = "target/txts/negative";
        File badResumesPdf = new File("src/test/resources/pdfs/negative");
        PdfToTextService pdfToTextServiceBadResumes = new PdfToTextService(outputPathBadResumes);
        List<File> badResumesInText = pdfToTextServiceBadResumes.convert(badResumesPdf);

        // 2. Tokenizer + ML
        Processor processor = new Processor();
        processor.process(goodResumesInText, badResumesInText);

        return processor;
    }

    private void MakePrediction(List<Double> weight, List<Double> XWithTfIdf) {
        // Making a prediction for a new Resume.
        Prediction prediction = Prediction.getInstance();
        double finalScore = prediction.getFinalScore(weight, XWithTfIdf);
        log.info("the final score is " + finalScore);
        if (finalScore > 0.5) {
            log.info("Candidate is hired");
        } else {
            log.info("Candidate is not hired");
        }

    }
}
