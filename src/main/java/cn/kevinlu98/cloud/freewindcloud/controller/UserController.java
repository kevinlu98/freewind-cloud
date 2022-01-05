package cn.kevinlu98.cloud.freewindcloud.controller;

import cn.kevinlu98.cloud.freewindcloud.common.FWResult;
import cn.kevinlu98.cloud.freewindcloud.common.Website;
import cn.kevinlu98.cloud.freewindcloud.common.enums.Site;
import cn.kevinlu98.cloud.freewindcloud.common.enums.SizeConverter;
import cn.kevinlu98.cloud.freewindcloud.pojo.User;
import cn.kevinlu98.cloud.freewindcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import static cn.kevinlu98.cloud.freewindcloud.common.FileUtils.uploadPath;

/**
 * Author: Mr丶冷文
 * Date: 2022-01-04 23:35
 * Email: kevinlu98@qq.com
 * Description:
 */
@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private Website website;


    @GetMapping("/")
    public String index() {
        return "user/index";
    }


    @PostMapping("/save")
    public String save(String nickname, String email, String pass, String npass, String rpass, MultipartFile avatar) {
        System.out.println(nickname);
        return "redirect:/user/";
    }

    @PostMapping("/avatar")
    @ResponseBody
    public FWResult<String> avatar(@RequestParam("file") MultipartFile file) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (file.isEmpty()) {
            return FWResult.fail("文件上传失败，请选择文件");
        }
        if (file.getSize() > Long.parseLong(website.avatarSize())) {
            return FWResult.fail("文件上传失败，请上传" + SizeConverter.BTrim.convert(Long.parseLong(website.avatarSize())) + "之内的图片");
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
        String filename = ResourceUtils.getURL("uploads").getPath() + authentication.getName() + "." + ename;
        System.out.println(filename);
        File dest = new File(filename);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        file.transferTo(dest);
        return FWResult.success("上传成功", "/uploads/" + authentication.getName() + "." + ename);
    }
}
