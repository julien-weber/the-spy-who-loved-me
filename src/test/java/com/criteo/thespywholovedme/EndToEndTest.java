package com.criteo.thespywholovedme;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.criteo.thespywholovedme.model.MLModel;
import com.criteo.thespywholovedme.prediction.Prediction;
import com.criteo.thespywholovedme.prediction.PredictionTest;
import com.criteo.thespywholovedme.tokenizer.Processor;
import com.criteo.thespywholovedme.tools.PdfToTextService;
import com.google.common.collect.Lists;

public class EndToEndTest {

    static Logger log = LoggerFactory.getLogger(EndToEndTest.class);

    @Test
    @Ignore
    public void training() {

        String goodResumesPath = "profiles_hired_output";
        List<File> goodResumesInText = Lists.newArrayList(FileUtils.listFiles(new File(goodResumesPath), FileFilterUtils.trueFileFilter(), FileFilterUtils.directoryFileFilter()));

        String badResumesPath = "profiles_rejected_output";
        List<File> badResumesInText = Lists.newArrayList(FileUtils.listFiles(new File(badResumesPath), FileFilterUtils.trueFileFilter(), FileFilterUtils.directoryFileFilter()));

        // 2. Tokenizer + ML
        Processor processor = new Processor();
        processor.process(goodResumesInText, badResumesInText);

    }

    @Test
    public void PredictTest() {
        // String resumeToPredictStr = "target/txts/JonWuTechResume.pdf";
        String resumeToPredictStr = "src/test/resources/pdfs/JonWuTechResume.pdf";

        String outputPath = "target/txts";
        File resumeToPredict = new File(resumeToPredictStr);
        PdfToTextService pdfToTextService = new PdfToTextService(outputPath);
        List<File> convertedResumes = pdfToTextService.convert(resumeToPredict);
        File convertedResume = convertedResumes.get(0);

        Processor processor = new Processor();
        List<Double> weight = Arrays.asList(0.1, 0.5, 1.0);
        //List<Double> weight = MLModel.getWeightVector();

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

        String goodResumesPath = "profiles_hired_output";
        List<File> goodResumesInText = Lists.newArrayList(FileUtils.listFiles(new File(goodResumesPath), FileFilterUtils.trueFileFilter(), FileFilterUtils.directoryFileFilter()));

        String badResumesPath = "profiles_rejected_output";
        List<File> badResumesInText = Lists.newArrayList(FileUtils.listFiles(new File(badResumesPath), FileFilterUtils.trueFileFilter(), FileFilterUtils.directoryFileFilter()));

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
