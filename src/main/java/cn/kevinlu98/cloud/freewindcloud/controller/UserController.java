package cn.kevinlu98.cloud.freewindcloud.controller;

import cn.kevinlu98.cloud.freewindcloud.pojo.User;
import cn.kevinlu98.cloud.freewindcloud.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

/**
 * Author: Mr丶冷文
 * Date: 2022-01-04 23:35
 * Email: kevinlu98@qq.com
 * Description:
 */
@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "user/index";
    }


    @PostMapping("/save")
    public String save(String nickname, String email, String pass, String npass, String rpass, MultipartFile avatar) {
        System.out.println(nickname);
        return "redirect:/user/";
    }
}
