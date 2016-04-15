package com.criteo.thespywholovedme.webapp;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.criteo.thespywholovedme.model.MLModel;
import com.criteo.thespywholovedme.prediction.Prediction;
import com.criteo.thespywholovedme.tokenizer.Processor;
import com.criteo.thespywholovedme.tools.PdfToTextService;
import com.google.common.collect.Lists;

@Service
public class ResumeScoringService {

    @Autowired
    private PdfToTextService pdfToTextService;

    public Double runPredictionForFile(File pdfInput) {
        List<File> file = pdfToTextService.convert(pdfInput);

        List<Double> weight = MLModel.getWeightVector();
        Processor processor = initializeProcessor();

        List<Double> XWithTfIdf = processor.GetXWithTfIdf(file.get(0));

        Prediction prediction = Prediction.getInstance();
        return prediction.getFinalScore(weight, XWithTfIdf);
    }

    private Processor initializeProcessor() {
        String goodResumesPath = "profiles_hired_output";
        List<File> goodResumesInText = Lists.newArrayList(FileUtils.listFiles(new File(goodResumesPath), FileFilterUtils.trueFileFilter(), FileFilterUtils.directoryFileFilter()));

        String badResumesPath = "profiles_rejected_output";
        List<File> badResumesInText = Lists.newArrayList(FileUtils.listFiles(new File(badResumesPath), FileFilterUtils.trueFileFilter(), FileFilterUtils.directoryFileFilter()));

        // 2. Tokenizer + ML
        Processor processor = new Processor();
        processor.process(goodResumesInText, badResumesInText);

        return processor;
    }
}
