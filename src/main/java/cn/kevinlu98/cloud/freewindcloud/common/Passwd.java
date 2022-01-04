package cn.kevinlu98.cloud.freewindcloud.common;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * Author: 鲁恺文
 * Date: 2022-01-04 17:11
 * Email: lukaiwen@xiaomi.com
 * Description:
 */
public interface Passwd {

    static String md5(String pwd) {
        return DigestUtils.md5DigestAsHex(pwd.getBytes(StandardCharsets.UTF_8));
    }

    static String rdmPass(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
