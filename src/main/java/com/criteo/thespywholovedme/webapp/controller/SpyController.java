package com.criteo.thespywholovedme.webapp.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.criteo.thespywholovedme.webapp.SpyApplication;

@Controller
public class SpyController {

    private static final Logger logger = LoggerFactory.getLogger(SpyController.class);

    @RequestMapping("/")
    public String home() {

        return "index";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    public String handlePdfUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        String name = file.getOriginalFilename();
        if (!file.isEmpty()) {
            try {
                File uploadedPdf = new File(SpyApplication.PDF_DIR + "/" + name);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedPdf));
                FileCopyUtils.copy(file.getInputStream(), stream);
                stream.close();
                redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + name + "!");
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("message", "You failed to upload " + name + " => " + e.getMessage());
            }
        } else {
            redirectAttributes.addFlashAttribute("message", "You failed to upload " + name + " because the file was empty");
        }
        return "redirect:/";
    }

    @ExceptionHandler(Exception.class)
    public @ResponseBody String handleException(Exception e, HttpServletResponse response) {
        logger.error("Error caught", e);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return e.getMessage();
    }
}
