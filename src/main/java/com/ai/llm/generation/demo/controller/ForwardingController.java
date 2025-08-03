package com.ai.llm.generation.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForwardingController {
    @RequestMapping(value = {
            "/{path:[^\\.]*}",            // Root-level SPA paths like /home, /login
            "/**/{path:[^\\.]*}",          // Nested paths like /admin/xyz
            "/error"
    })
    public String forwardAdminPaths() {
        return "forward:/index.html";
    }
}