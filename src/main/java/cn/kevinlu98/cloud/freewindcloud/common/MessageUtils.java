package cn.kevinlu98.cloud.freewindcloud.common;

import org.springframework.ui.Model;

/**
 * Author: Mr丶冷文
 * Date: 2022-01-05 23:40
 * Email: kevinlu98@qq.com
 * Description:
 */
public interface MessageUtils {

    String TYPE_SUCCESS = "success";
    String TYPE_ERROR = "error";

    static void success(Model model, String message) {
        MessageUtils.msg(model, TYPE_SUCCESS, message);
    }

    static void error(Model model, String message) {
        MessageUtils.msg(model, TYPE_ERROR, message);
    }

    static void msg(Model model, String type, String message) {
        model.addAttribute("msgType", type);
        model.addAttribute("message", message);
    }
}
