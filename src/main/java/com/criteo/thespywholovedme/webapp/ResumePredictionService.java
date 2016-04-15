package com.criteo.thespywholovedme.webapp;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.criteo.thespywholovedme.tools.PdfToTextService;

@Service
public class ResumePredictionService {

    @Autowired
    private PdfToTextService pdfToTextService;

    public String runPredictionForFile(File pdfInput) {
        List<File> file = pdfToTextService.convert(pdfInput);

        return "80%";
    }
}
