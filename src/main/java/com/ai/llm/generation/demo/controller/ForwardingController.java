package com.ai.llm.generation.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForwardingController {
    @RequestMapping(value = {"/admin/{path:[^\\.]*}", "/admin/**/{path:[^\\.]*}"})
    public String forwardAdminPaths() {
        return "forward:/index.html";
    }
}