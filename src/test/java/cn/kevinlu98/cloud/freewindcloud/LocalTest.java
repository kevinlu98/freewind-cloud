package cn.kevinlu98.cloud.freewindcloud;

import lombok.val;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Author: Mr丶冷文
 * Date: 2022-01-09 00:00
 * Email: kevinlu98@qq.com
 * Description:
 */
public class LocalTest {

    @Test
    public void test_path() {
        String driver = "/Users/lengwen/driver/admin";
        String path = "/Users/lengwen/driver/admin/ripro";
        System.out.println(path.replaceFirst(driver, ""));

        String s = "/1231321/1231231";
        if (s.startsWith("/"))
            s = s.substring(1);
        System.out.println(Arrays.toString(s.split("/")));
    }

    @Test
    public void filetype() throws IOException {
        String path = "/Users/lengwen/Desktop/type.txt";
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        int index = 1;
        while ((line = br.readLine()) != null) {
            String enumStr = "FILE_TYPE_" + line.toUpperCase() + "(" + index++ + ",\"" + line + "\",\"/static/images/filetype/" + line + ".png\"),";
            System.out.println(enumStr);
        }
    }
}
