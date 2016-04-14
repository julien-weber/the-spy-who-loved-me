package com.criteo.thespywholovedme.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SpyController {

    @RequestMapping("/")
    public @ResponseBody String home() {
        return "home";
    }
}
