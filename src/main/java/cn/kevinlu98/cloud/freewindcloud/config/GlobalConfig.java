package cn.kevinlu98.cloud.freewindcloud.config;

import cn.kevinlu98.cloud.freewindcloud.common.Website;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Author: Mr丶冷文
 * Date: 2022-01-04 21:19
 * Email: kevinlu98@qq.com
 * Description:
 */
@ControllerAdvice
public class GlobalConfig {

    @Autowired
    private Website website;

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("site", website);
    }
}
