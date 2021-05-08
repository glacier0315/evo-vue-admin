package com.glacier.controller;

import com.glacier.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * date 2021-05-07 21:52
 *
 * @author glacier
 * @version 1.0
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping()
    public String index(Model model) {
        log.info("测试thymeleay");
        List<User> list = new ArrayList<>();
        User user = null;
        for (int i = 0; i < 50; i++) {
            user = new User("id_" + i, "name_" + i, "pw_" + i, i % 2);
            list.add(user);
        }
        model.addAttribute("users", list);
        return "user/list";
    }


}
