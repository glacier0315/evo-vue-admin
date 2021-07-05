package com.glacier.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * date 2021-05-07 21:52
 *
 * @author glacier
 * @version 1.0
 */
@Controller
public class IndexController {

    @GetMapping("")
    public String index() {
        return "index";
    }
}
