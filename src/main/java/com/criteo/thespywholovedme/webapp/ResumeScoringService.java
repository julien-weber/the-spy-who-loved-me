package com.criteo.thespywholovedme.webapp;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.criteo.thespywholovedme.tools.PdfToTextService;

@Service
public class ResumeScoringService {

    @Autowired
    private PdfToTextService pdfToTextService;

    public Double runPredictionForFile(File pdfInput) {
        List<File> file = pdfToTextService.convert(pdfInput);

        return 0.8d;
    }
}
