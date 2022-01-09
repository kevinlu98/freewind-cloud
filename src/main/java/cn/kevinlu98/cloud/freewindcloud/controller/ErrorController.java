package cn.kevinlu98.cloud.freewindcloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Author: Mr丶冷文
 * Date: 2022-01-08 15:15
 * Email: kevinlu98@qq.com
 * Description:
 */
@Controller
public class ErrorController {

    @GetMapping("/403")
    public String page403() {
        return "error/403";
    }

    @GetMapping("/404")
    public String page404() {
        return "error/404";
    }

    @GetMapping("/500")
    public String page500() {
        return "error/500";
    }
}
