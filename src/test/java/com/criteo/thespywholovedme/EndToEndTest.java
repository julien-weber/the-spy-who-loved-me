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

	static Logger log = LoggerFactory.getLogger(PredictionTest.class);

    @Test
    public void endToEndTest() {

    	//Training:

    	//1. Convert Files from Pdf to Text
    	String outputPathGoodResumes = "target/txts/positive";
    	File outputDirectoryGoodResumes = new File(outputPathGoodResumes);
        File goodResumesPdf = new File("src/test/resources/pdfs/positive");
        PdfToTextService pdfToTextServiceGoodResumes = new PdfToTextService(outputDirectoryGoodResumes);
        List<File> goodResumesInText = pdfToTextServiceGoodResumes.convert(goodResumesPdf);

    	String outputPathBadResumes = "target/txts/negative";
    	File outputDirectoryBadResumes = new File(outputPathBadResumes);
        File badResumesPdf = new File("src/test/resources/pdfs/negative");
        PdfToTextService pdfToTextServiceBadResumes = new PdfToTextService(outputDirectoryBadResumes);
        List<File> badResumesInText = pdfToTextServiceBadResumes.convert(badResumesPdf);

        //2. Tokenizer + ML
        Processor processor = new Processor();
		processor.process(goodResumesInText, badResumesInText);

    	//TODO integrate with ML
    	List<Double> weight = Arrays.asList(0.1, 0.5, 1.0);
    	//weight = processor.GetWeight();
        List<Double> XWithTfIdf = Arrays.asList(1.0, 1.0, 2.0);
        //String resumeToPredictStr = "target/txts/NewCadidate.pdf";
     	//File resumeToPredict = new File(resumeToPredictStr);
        //XWithTfIdf = processor.GetXWithTfIdf(resumeToPredict);

        //Making a prediction for a new Resume.
        Prediction prediction = Prediction.getInstance();
        double finalScore = prediction.getFinalScore(weight, null, XWithTfIdf);
        log.info("the final score is " + finalScore);
        if (finalScore >  0.5)
        {
        	log.info("Candidate is hired");
        }
        else
        {
        	log.info("Candidate is not hired");
        }


    }
}
