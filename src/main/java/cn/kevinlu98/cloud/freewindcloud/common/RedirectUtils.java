package cn.kevinlu98.cloud.freewindcloud.common;

import lombok.SneakyThrows;

import java.net.URLEncoder;

/**
 * Author: Mr丶冷文
 * Date: 2022-01-06 00:31
 * Email: kevinlu98@qq.com
 * Description:
 */
public interface RedirectUtils {

    static String redirect(String url) {
        return "redirect:" + url;
    }

    static String redirectSuccess(String url, String message) {
        return redirect(url, MessageUtils.TYPE_SUCCESS, message);
    }

    static String redirectError(String url, String message) {
        return redirect(url, MessageUtils.TYPE_ERROR, message);
    }

    @SneakyThrows
    static String redirect(String url, String msgType, String message) {
        return String.format("redirect:%s?msgType=%s&message=%s", url, msgType, URLEncoder.encode(message, "utf-8"));
    }
}
