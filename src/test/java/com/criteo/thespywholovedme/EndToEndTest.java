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
    	String outputPathGoodResumes = "src/test/resources/txts/positive";
    	File outputDirectoryGoodResumes = new File(outputPathGoodResumes);
        File goodResumesPdf = new File("src/test/resources/pdfs/positive");
        PdfToTextService pdfToTextServiceGoodResumes = new PdfToTextService(outputDirectoryGoodResumes);
        List<File> goodResumesInText = pdfToTextServiceGoodResumes.convert(goodResumesPdf);

    	String outputPathBadResumes = "src/test/resources/txts/negative";
    	File outputDirectoryBadResumes = new File(outputPathBadResumes);
        File badResumesPdf = new File("src/test/resources/pdfs/negative");
        PdfToTextService pdfToTextServiceBadResumes = new PdfToTextService(outputDirectoryBadResumes);
        List<File> badResumesInText = pdfToTextServiceBadResumes.convert(badResumesPdf);

        //2. Tokenizer + ML
        //Processor processor = new Processor(goodResumesInText,badResumesInText);
		//processor.process();

    	//TODO get from ML
    	 List<Double> weight = Arrays.asList(0.1, 0.5, 1.0);
         List<Double> XWithTfIdf = Arrays.asList(1.0, 1.0, 2.0);
         Path pathToResume = null;


        //Making a prediction for a new Resume.
        Prediction prediction = Prediction.getInstance();
        double finalScore = prediction.getFinalScore(weight, pathToResume, XWithTfIdf);
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
