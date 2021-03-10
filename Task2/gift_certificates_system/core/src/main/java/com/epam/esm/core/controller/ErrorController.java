package com.epam.esm.core.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {
    @RequestMapping("/error")
    public String getOne(@ModelAttribute("msg") String msg) {
        return msg;
    }
}
