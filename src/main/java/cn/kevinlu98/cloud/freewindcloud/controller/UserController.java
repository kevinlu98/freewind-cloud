package cn.kevinlu98.cloud.freewindcloud.controller;

import cn.kevinlu98.cloud.freewindcloud.common.FWResult;
import cn.kevinlu98.cloud.freewindcloud.common.MessageUtils;
import cn.kevinlu98.cloud.freewindcloud.common.Passwd;
import cn.kevinlu98.cloud.freewindcloud.common.RedirectUtils;
import cn.kevinlu98.cloud.freewindcloud.common.SecurityUser;
import cn.kevinlu98.cloud.freewindcloud.common.Website;
import cn.kevinlu98.cloud.freewindcloud.common.enums.Role;
import cn.kevinlu98.cloud.freewindcloud.common.enums.Site;
import cn.kevinlu98.cloud.freewindcloud.common.enums.SizeConverter;
import cn.kevinlu98.cloud.freewindcloud.pojo.User;
import cn.kevinlu98.cloud.freewindcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
    public String index(@RequestParam(name = "msgType", defaultValue = "") String msgType, @RequestParam(name = "message", defaultValue = "") String message, Model model) {
        if (!StringUtils.isEmptyOrWhitespace(msgType)) {
            MessageUtils.msg(model, msgType, message);
        }
        return "user/index";
    }

    @PostMapping("/pass")
    public String pass(String cpass, String npass, String vpass, Model model) {
        if (npass.length() < 6) {
            return RedirectUtils.redirectError("/user/", "密码不能短于6位");
        }
        if (!StringUtils.equals(npass, vpass)) {
            return RedirectUtils.redirectError("/user/", "两次密码输入不一致");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loginUser = ((SecurityUser) authentication.getPrincipal()).getLoginUser();
        if (!StringUtils.equalsIgnoreCase(Passwd.md5(cpass), loginUser.getPassword())) {
            return RedirectUtils.redirectError("/user/", "原始密码输入错误");
        }
        if (StringUtils.equals(npass, cpass)) {
            return RedirectUtils.redirectError("/user/", "新密码与原始密码相同");
        }
        User user = userService.save(User.builder().id(loginUser.getId()).password(Passwd.md5(npass)).build());
        updateSecurity(user);
        return RedirectUtils.redirectSuccess("/user/", "密码修改成功");
    }


    @PostMapping("/save")
    public String save(String username, String nickname, String email, String avatar, Model model) {
        User user = new User();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loginUser = ((SecurityUser) authentication.getPrincipal()).getLoginUser();
        user.setId(loginUser.getId());
        if (Objects.equals(loginUser.getRole(), Role.ROLE_ADMIN.getValue())) {
            user.setUsername(username);
        }
        user.setAvatar(avatar);
        user.setNickname(nickname);
        if (!StringUtils.equals(email, loginUser.getEmail())) {
            if (userService.exists(email)) {
                return RedirectUtils.redirectError("/user/", "邮箱已存在");
            }
            user.setEmail(email);
        }
        updateSecurity(user);
        return RedirectUtils.redirectSuccess("/user/", "保存成功");
    }

    private void updateSecurity(User user) {
        Role role = Role.find(user.getRole());
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        user = userService.save(user);
        SecurityUser designer = new SecurityUser(user.getUsername(), user.getPassword(), authorities, user);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(designer, designer.getPassword(), designer.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

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
        String filename = ResourceUtils.getURL("uploads").getPath() + File.separator + authentication.getName() + "." + ename;
        System.out.println(filename);
        File dest = new File(filename);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        file.transferTo(dest);
        return FWResult.success("上传成功", "/uploads/" + authentication.getName() + "." + ename);
    }
}
