package cn.kevinlu98.cloud.freewindcloud.controller;

import cn.kevinlu98.cloud.freewindcloud.common.*;
import cn.kevinlu98.cloud.freewindcloud.common.enums.Role;
import cn.kevinlu98.cloud.freewindcloud.common.enums.Size;
import cn.kevinlu98.cloud.freewindcloud.pojo.User;
import cn.kevinlu98.cloud.freewindcloud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('admin')")
    @ResponseBody
    @PostMapping("/reset/password")
    public FWResult<String> rePassword(Long id) {
        User loginUser = website.loginUser();
        if (Objects.equals(loginUser.getId(), id)) {
            return FWResult.fail("请去个人中心对自己账号完成操作");
        }
        userService.repwd(id);
        return FWResult.success("密码重置成功");
    }

    @PreAuthorize("hasRole('admin')")
    @ResponseBody
    @PostMapping("/reset/avatar")
    public FWResult<String> reAvatar(Long id) {
        User loginUser = website.loginUser();
        if (Objects.equals(loginUser.getId(), id)) {
            return FWResult.fail("请去个人中心对自己账号完成操作");
        }
        userService.reAvatar(id);
        return FWResult.success("密码头像成功");
    }

    @PreAuthorize("hasRole('admin')")
    @ResponseBody
    @PostMapping("/reset/nickname")
    public FWResult<String> reNickname(Long id) {
        User loginUser = website.loginUser();
        if (Objects.equals(loginUser.getId(), id)) {
            return FWResult.fail("请去个人中心对自己账号完成操作");
        }
        userService.reNickname(id);
        return FWResult.success("密码昵称成功");
    }

    @PreAuthorize("hasRole('admin')")
    @ResponseBody
    @PostMapping("/reset/delete")
    public FWResult<String> delete(Long id) {
        User loginUser = website.loginUser();
        if (Objects.equals(loginUser.getId(), id)) {
            return FWResult.fail("不能删除自已的账号哦");
        }
        userService.delete(id);
        return FWResult.success("删除用户成功");
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/add")
    public String add(@RequestParam(name = "msgType", defaultValue = "") String msgType, @RequestParam(name = "message", defaultValue = "") String message, Model model) {
        if (!StringUtils.isEmptyOrWhitespace(msgType)) {
            MessageUtils.msg(model, msgType, message);
        }
        return "user/add";
    }

    @PreAuthorize("hasRole('admin')")
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

    @PreAuthorize("hasRole('admin')")
    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable long id, User user, Model model) {
        User loginUser = website.loginUser();
        if (loginUser.getId().equals(id)) {
            return RedirectUtils.redirectError("/user/edit/" + id, "请去个人中心对自己账号完成操作");
        }
        userService.save(user);
        return RedirectUtils.redirectSuccess("/user/edit/" + id, "保存成功");
    }

    @PreAuthorize("hasRole('admin')")
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

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/list")
    public String list(@RequestParam(name = "msgType", defaultValue = "") String msgType, @RequestParam(name = "message", defaultValue = "") String message, @RequestParam(value = "keyword", defaultValue = "") String keyword, Integer page, Integer size, Model model) {
        if (!StringUtils.isEmptyOrWhitespace(msgType)) {
            MessageUtils.msg(model, msgType, message);
        }
        page = page == null ? 1 : page;
        size = size == null ? 10 : size;
        Page<User> usersPage = userService.listUser(keyword, page, size);
        FWPage<User> users = new FWPage<>(page, usersPage.getTotalPages(), usersPage.getTotalElements(), usersPage.getContent());
        model.addAttribute("userPage", users);
        model.addAttribute("keyword", keyword);
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
    public String save(String username, String nickname, String email, String avatar, String size, String limitSize) {
        User user = new User();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User loginUser = ((SecurityUser) authentication.getPrincipal()).getLoginUser();
        user.setId(loginUser.getId());
        if (Objects.equals(loginUser.getRole(), Role.ROLE_ADMIN.getValue())) {
            user.setUsername(username);
            user.setSize(size);
            user.setLimitSize(limitSize);
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
        return FileUploadUtils.uploadImg(file, authentication.getName());
    }
}
