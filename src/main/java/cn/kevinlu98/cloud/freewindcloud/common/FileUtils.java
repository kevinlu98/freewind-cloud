package cn.kevinlu98.cloud.freewindcloud.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Author: 鲁恺文
 * Date: 2022-01-05 20:37
 * Email: lukaiwen@xiaomi.com
 * Description:
 */
@Slf4j
public class FileUtils {
    public static FWResult<String> uploadImg(MultipartFile file,String name) throws IOException {
        if (file.isEmpty()) {
            return FWResult.fail("文件上传失败，请选择文件");
        }
        BufferedImage bi = ImageIO.read(file.getInputStream());
        if (bi == null) {
            return FWResult.fail("上传失败，上传的文件不是图片");
        }
        String oname = file.getOriginalFilename();
        String ename;
        if (oname != null && oname.contains(".")) {
            ename = oname.substring(oname.lastIndexOf(".") + 1);
        } else {
            ename = "png";
        }
        String filename = ResourceUtils.getURL("uploads").getPath() + File.separator + name + "." + ename;
        File dest = new File(filename);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        file.transferTo(dest);
        return FWResult.success("上传成功", "/uploads/" + name + "." + ename);
    }

}
