package com.finalproj.amr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ForwardController {

    @RequestMapping(value = "/{[path:[^\\.]*}")
    public String forward() {
        // Forward to React's index.html
        return "forward:/index.html";
    }
}

