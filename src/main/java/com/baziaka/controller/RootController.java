package com.baziaka.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class RootController {

    @GetMapping
    public String getRootView() {
        return "redirect:/booking/select/event";
    }

    @GetMapping("/access-denied")
    public String getAccessDeniedView() {
        return "access-denied";
    }

}
