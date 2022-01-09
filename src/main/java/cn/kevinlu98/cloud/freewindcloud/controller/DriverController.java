package cn.kevinlu98.cloud.freewindcloud.controller;

import cn.kevinlu98.cloud.freewindcloud.common.MessageUtils;
import cn.kevinlu98.cloud.freewindcloud.common.RedirectUtils;
import cn.kevinlu98.cloud.freewindcloud.pojo.Driver;
import cn.kevinlu98.cloud.freewindcloud.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import java.util.List;

/**
 * Author: Mr丶冷文
 * Date: 2022-01-08 16:53
 * Email: kevinlu98@qq.com
 * Description:
 */
@Controller
@RequestMapping("/driver")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @GetMapping("/")
    public String index(@RequestParam(name = "msgType", defaultValue = "") String msgType, @RequestParam(name = "message", defaultValue = "") String message, Model model) {
        if (!StringUtils.isEmptyOrWhitespace(msgType)) {
            MessageUtils.msg(model, msgType, message);
        }
        List<Driver> drivers = driverService.find();
        model.addAttribute("drivers", drivers);
        return "driver/index";
    }

    @GetMapping("/add")
    public String add(@RequestParam(name = "msgType", defaultValue = "") String msgType, @RequestParam(name = "message", defaultValue = "") String message, Model model) {
        if (!StringUtils.isEmptyOrWhitespace(msgType)) {
            MessageUtils.msg(model, msgType, message);
        }
        return "driver/add";
    }

    @PostMapping("/add")
    public String addDriver(Driver driver) {
        if (driverService.exist(driver))
            return RedirectUtils.redirectError("/driver/add","当前类型已经存在该路径或该名称的云盘啦");
        driverService.save(driver);
        return RedirectUtils.redirectSuccess("/driver/", "保存成功");
    }
}
