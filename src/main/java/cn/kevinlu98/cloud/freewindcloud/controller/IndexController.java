package cn.kevinlu98.cloud.freewindcloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author: 鲁恺文
 * Date: 2022-01-04 17:36
 * Email: lukaiwen@xiaomi.com
 * Description:
 */
@Controller
public class IndexController {

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        System.out.println(model.getAttribute("site"));
        return "login";
    }
}
