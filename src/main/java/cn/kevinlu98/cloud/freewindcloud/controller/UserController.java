package cn.kevinlu98.cloud.freewindcloud.controller;

import cn.kevinlu98.cloud.freewindcloud.common.*;
import cn.kevinlu98.cloud.freewindcloud.common.enums.Role;
import cn.kevinlu98.cloud.freewindcloud.common.enums.Size;
import cn.kevinlu98.cloud.freewindcloud.pojo.User;
import cn.kevinlu98.cloud.freewindcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

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

    @GetMapping("/add")
    public String add(@RequestParam(name = "msgType", defaultValue = "") String msgType, @RequestParam(name = "message", defaultValue = "") String message, Model model) {
        if (!StringUtils.isEmptyOrWhitespace(msgType)) {
            MessageUtils.msg(model, msgType, message);
        }
        return "user/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(@RequestParam(name = "msgType", defaultValue = "") String msgType, @RequestParam(name = "message", defaultValue = "") String message, @PathVariable long id, Model model) {
        if (!StringUtils.isEmptyOrWhitespace(msgType)) {
            MessageUtils.msg(model, msgType, message);
        }
        User user = userService.find(id);
        if (user == null) {
            return RedirectUtils.redirectError("/user/list", "id为[" + id + "]的用户不存在");
        }
        model.addAttribute("user", user);
        return "user/edit";
    }

    @PostMapping("/add")
    public String add(User user) {
        user.setAvatar(website.userDefaultAvatar());
        if (userService.existsEmail(user.getEmail())) {
            return RedirectUtils.redirectError("/user/add", "邮箱" + user.getEmail() + "已存在");
        }
        if (userService.existsUsername(user.getUsername())) {
            return RedirectUtils.redirectError("/user/add", "用户名" + user.getUsername() + "已存在");
        }
        user.setPassword(Passwd.md5(user.getUsername()));
        userService.save(user);
        return RedirectUtils.redirectSuccess("/user/add", "新增用户成功");
    }

    @GetMapping("/list")
    public String list(@RequestParam(name = "msgType", defaultValue = "") String msgType, @RequestParam(name = "message", defaultValue = "") String message, Integer page, Integer size, Model model) {
        if (!StringUtils.isEmptyOrWhitespace(msgType)) {
            MessageUtils.msg(model, msgType, message);
        }
        page = page == null ? 1 : page;
        size = size == null ? 10 : size;
        Page<User> usersPage = userService.listUser(page, size);
        FWPage<User> users = new FWPage<>(page, usersPage.getTotalPages(), usersPage.getTotalElements(), usersPage.getContent());
        model.addAttribute("userPage", users);
        return "user/list";
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
            if (userService.existsEmail(email)) {
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
        if (file.getSize() > Size.convertBase(website.avatarSize())) {
            return FWResult.fail("文件上传失败，请上传" + website.avatarSize() + "之内的图片");
        }
        return FileUtils.uploadImg(file, authentication.getName());
    }
}
