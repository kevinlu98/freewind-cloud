package cn.kevinlu98.cloud.freewindcloud.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Author: 鲁恺文
 * Date: 2022-01-05 20:37
 * Email: lukaiwen@xiaomi.com
 * Description:
 */
@Slf4j
public class FileUtils {
    public static String uploadPath() {
        File path;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
            return path.getParentFile().getParentFile().getParent() + File.separator + "logistics" + File.separator + "uploads" + File.separator;
//            return path.getParent() + File.separator + "logistics" + File.separator + "uploads" + File.separator;
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
            return "";
        }
    }

}
