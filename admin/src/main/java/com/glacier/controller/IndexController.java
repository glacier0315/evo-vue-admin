package com.glacier.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/hello")
    public String hello(Model model) {
        String[] s = {"我是1", "我是2", "我是3", "我是5"};
        model.addAttribute("s", s);
        return "core/hello";
    }
}
