package com.criteo.thespywholovedme.webapp;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.criteo.thespywholovedme.model.MLModel;
import com.criteo.thespywholovedme.prediction.Prediction;
import com.criteo.thespywholovedme.tokenizer.Processor;
import com.criteo.thespywholovedme.tools.PdfToTextService;

@Service
public class ResumeScoringService {

    @Autowired
    private PdfToTextService pdfToTextService;

    @Autowired
    private Processor processor;

    @Autowired
    private Prediction prediction;

    public Double runPredictionForFile(File pdfInput) {
        List<File> file = pdfToTextService.convert(pdfInput);

        List<Double> weight = MLModel.getWeightVector();

        List<Double> XWithTfIdf = processor.GetXWithTfIdf(file.get(0));

        return prediction.getFinalScore(weight, XWithTfIdf);
    }

}
