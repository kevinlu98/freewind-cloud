package cn.kevinlu98.cloud.freewindcloud.controller;

import cn.kevinlu98.cloud.freewindcloud.common.DesUtils;
import cn.kevinlu98.cloud.freewindcloud.common.FWResult;
import cn.kevinlu98.cloud.freewindcloud.common.Website;
import cn.kevinlu98.cloud.freewindcloud.common.enums.DriverType;
import cn.kevinlu98.cloud.freewindcloud.common.enums.Size;
import cn.kevinlu98.cloud.freewindcloud.exception.NoDriverTypeException;
import cn.kevinlu98.cloud.freewindcloud.pojo.Breadcrumb;
import cn.kevinlu98.cloud.freewindcloud.pojo.Driver;
import cn.kevinlu98.cloud.freewindcloud.pojo.FileEntity;
import cn.kevinlu98.cloud.freewindcloud.service.CloudService;
import cn.kevinlu98.cloud.freewindcloud.service.DriverService;
import cn.kevinlu98.cloud.freewindcloud.service.cloud.LocalService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.webresources.FileResource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Author: Mr丶冷文
 * Date: 2022-01-08 22:23
 * Email: kevinlu98@qq.com
 * Description:
 */
@Slf4j
@Controller
@RequestMapping("/cloud")
public class CloudController {


    @Autowired
    private Website website;

    @Autowired
    private DriverService driverService;

    @GetMapping("/view")
    public void viewFile(@RequestParam(value = "path", defaultValue = "") String path, HttpServletResponse response) throws Exception {
        if (StringUtils.isNotEmpty(path)) {
            Driver driver = driverService.activeDriver();
            CloudService cloudService = CloudService.getInstance(DriverType.find(driver.getType()));
            path = DesUtils.decrypt(path);
            cloudService.fileStream(driver, website.loginUser(), path, response);
        }
    }

    @GetMapping("/download")
    public void download(@RequestParam(value = "path", defaultValue = "") String path, HttpServletResponse response) throws Exception {
        if (StringUtils.isNotEmpty(path)) {
            Driver driver = driverService.activeDriver();
            CloudService cloudService = CloudService.getInstance(DriverType.find(driver.getType()));
            path = DesUtils.decrypt(path);
            cloudService.download(driver, website.loginUser(), path, response);
        }
    }

    @ResponseBody
    @GetMapping("/rollback")
    public FWResult<String> rollback(@RequestParam(value = "path", defaultValue = "") String path, String target) throws Exception {
        if (StringUtils.isEmpty(target)) return FWResult.fail("回滚目标不能为空");
        if (StringUtils.isNotEmpty(path)) {
            path = DesUtils.decrypt(path);
        }
        Driver driver = driverService.activeDriver();
        CloudService cloudService = CloudService.getInstance(DriverType.find(driver.getType()));
        cloudService.delete(driver, website.loginUser(), path, target);
        return FWResult.success("回滚成功");
    }


    @ResponseBody
    @GetMapping("/mod")
    public FWResult<String> listModel(String mod, HttpSession session) {
        session.setAttribute("mod", mod);
        return FWResult.success("设置成功");
    }

    @GetMapping("/mkdir")
    @ResponseBody
    public FWResult<String> mkdir(String name, String path) throws Exception {
        if (StringUtils.isNotEmpty(path)) {
            path = DesUtils.decrypt(path);
        }
        Driver driver = driverService.activeDriver();
        CloudService cloudService = CloudService.getInstance(DriverType.find(driver.getType()));
        cloudService.mkdir(driver, name, path, website.loginUser());
        return FWResult.success("创建成功");
    }

    @ResponseBody
    @PostMapping("/upload")
    public FWResult<String> upload(MultipartFile file, String path) throws Exception {
        if (Objects.isNull(file) || file.isEmpty()) {
            return FWResult.fail("文件上传失败，请选择文件");
        }
        if (StringUtils.isNotEmpty(path)) {
            path = DesUtils.decrypt(path);
        }
        float limitSize = Size.convertBase(website.loginUser().getLimitSize());
        long fileSize = file.getSize();
        if (limitSize < fileSize) {
            return FWResult.fail("上传失败，您最大允许上传" + website.loginUser().getLimitSize() + "的文件");
        }
        Driver driver = driverService.activeDriver();
        CloudService cloudService = CloudService.getInstance(DriverType.find(driver.getType()));
        try {
            cloudService.uploadFile(driver, file, path, website.loginUser());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return FWResult.fail("上传失败");
        }
        return FWResult.success("上传成功");
    }


    @GetMapping("/")
    public String index(@RequestParam(value = "path", defaultValue = "") String path, Model model) throws Exception {
        Driver driver = driverService.activeDriver();
        CloudService cloudService = CloudService.getInstance(DriverType.find(driver.getType()));
        if (StringUtils.isNotEmpty(path)) {
            path = DesUtils.decrypt(path);
        }
        List<FileEntity> files = cloudService.listFile(driver.getPath(), path, website.loginUser());
        model.addAttribute("files", files);
        List<Breadcrumb> crumbs = new ArrayList<>();
        String[] items = path.split("/");
        for (int i = 1; i < items.length; i++) {
            Breadcrumb crumb = new Breadcrumb();
            StringBuilder cpath = new StringBuilder("/");
            for (int j = 1; j <= i; j++) {
                cpath.append(items[j]).append("/");
            }
            crumb.setShow(items[i]);
            crumb.setPath(DesUtils.encryption(cpath.toString()));
            crumbs.add(crumb);
        }
        model.addAttribute("crumbs", crumbs);
        model.addAttribute("uploadPath", DesUtils.encryption(path));
        return "cloud/index";
    }
}
