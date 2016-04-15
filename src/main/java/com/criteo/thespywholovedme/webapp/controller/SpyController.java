package com.criteo.thespywholovedme.webapp.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.python.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.criteo.thespywholovedme.webapp.ResumeScoringService;

@Controller
public class SpyController {

    private static final Logger logger = LoggerFactory.getLogger(SpyController.class);

    @Value("${web.upload_directory}")
    private String uploadDirectory;

    @Autowired
    private ResumeScoringService resumePredictionService;

    @RequestMapping("/")
    public String home() {

        return "index";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public @ResponseBody ResponseMessage handlePdfUpload(@RequestParam("file") MultipartFile uploadedFile, RedirectAttributes redirectAttributes) {
        String uploadedFileName = uploadedFile.getOriginalFilename();
        ResponseMessage response = new ResponseMessage();
        if (!uploadedFile.isEmpty()) {
            try {
                File uploadedPdf = new File(uploadDirectory + "/" + uploadedFileName);
                try (InputStream input = uploadedFile.getInputStream()) {
                    try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedPdf))) {
                        FileCopyUtils.copy(uploadedFile.getInputStream(), stream);
                    }
                }
                Double prediction = resumePredictionService.runPredictionForFile(uploadedPdf);

                response.score = prediction;
            } catch (Exception e) {
                response.error = "You failed to upload " + uploadedFileName + " => " + e.getMessage();
            }
        } else {
            response.error = "You failed to upload " + uploadedFileName + " because the file was empty";
        }

        return response;
    }

    @ExceptionHandler(Exception.class)
    public @ResponseBody String handleException(Exception e, HttpServletResponse response) {
        logger.error("Error caught", e);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return e.getMessage();
    }

    public static class ResponseMessage {
        private String error;
        private Double score;

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public Double getScore() {
            return score;
        }

        public void setScore(Double score) {
            this.score = score;
        }
    }
}
