package cn.kevinlu98.cloud.freewindcloud.controller;

import cn.kevinlu98.cloud.freewindcloud.common.*;
import cn.kevinlu98.cloud.freewindcloud.common.enums.Size;
import cn.kevinlu98.cloud.freewindcloud.service.OptionService;
import javafx.scene.input.DataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author: Mr丶冷文
 * Date: 2022-01-06 00:39
 * Email: kevinlu98@qq.com
 * Description:
 */
@Controller
@RequestMapping("/site")
public class SiteController {

    @Autowired
    private OptionService optionService;

    @GetMapping("/")
    public String index(@RequestParam(name = "msgType", defaultValue = "") String msgType, @RequestParam(name = "message", defaultValue = "") String message, Model model) {
        if (!StringUtils.isEmptyOrWhitespace(msgType)) {
            MessageUtils.msg(model, msgType, message);
        }
        return "site/index";
    }

    @CacheEvict(value = {"site_config"}, allEntries = true)
    @PostMapping("/save")
    public String save(HttpServletRequest request) {
        request.getParameterMap().forEach((k, v) -> optionService.update(k, v[0]));
        return RedirectUtils.redirectSuccess("/site/","站点设置保存成功");
    }

    @ResponseBody
    @PostMapping("/upload")
    public FWResult<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        DateFormat df = new SimpleDateFormat("yyyy" + File.separator + "MM" + File.separator + "dd" + File.separator);
        String filename = df.format(new Date());
        filename += Passwd.rdmPass(8);
        System.out.println(filename);
        return FileUtils.uploadImg(file, filename);
    }
}
