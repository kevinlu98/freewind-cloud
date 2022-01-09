package cn.kevinlu98.cloud.freewindcloud.service.cloud;

import cn.kevinlu98.cloud.freewindcloud.common.enums.FileType;
import cn.kevinlu98.cloud.freewindcloud.common.enums.Size;
import cn.kevinlu98.cloud.freewindcloud.pojo.Driver;
import cn.kevinlu98.cloud.freewindcloud.pojo.FileEntity;
import cn.kevinlu98.cloud.freewindcloud.pojo.User;
import cn.kevinlu98.cloud.freewindcloud.service.CloudService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Author: Mr丶冷文
 * Date: 2022-01-08 22:26
 * Email: kevinlu98@qq.com
 * Description:
 */
@Slf4j
@Service
public class LocalService extends CloudService {

    @Override
    public List<FileEntity> listFile(String driver, String path, User loginUser) {
        String home = driver + File.separator + loginUser.getUsername();
        String dirpath = home + File.separator + path;
        File filedir = new File(dirpath);
        if (!filedir.exists()) {
            filedir.mkdirs();
        }
        return Arrays.stream(Objects.requireNonNull(filedir.listFiles((dir, name) -> !StringUtils.equals(".DS_Store", name)))).map(x -> FileEntity.fromFile(x, home)).collect(Collectors.toList());
    }

    @Override
    public String usedSize(String driver, User loginUser) {
        String homePath = driver + File.separator + loginUser.getUsername();
        File home = new File(homePath);
        if (!home.exists()) {
            home.mkdirs();
        }
        return Size.BTrim.convert(FileUtils.sizeOf(home));
    }

    @Override
    public void uploadFile(Driver driver, MultipartFile file, String path, User loginUser) throws Exception {
        String filename = driver.getPath() + File.separator + loginUser.getUsername() + File.separator + path + File.separator + file.getOriginalFilename();
        File targetFile = new File(filename);
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        file.transferTo(targetFile);
    }

    @Override
    public void delete(Driver driver, User loginUser, String path, String target) throws Exception {
        String filename = driver.getPath() + File.separator + loginUser.getUsername() + File.separator + path + File.separator + target;
        File targetFile = new File(filename);
        if (targetFile.exists()) {
            if (targetFile.isDirectory()) {
                FileUtils.deleteDirectory(targetFile);
            } else {
                FileUtils.delete(targetFile);
            }
        }
    }

    @Override
    public void fileStream(Driver driver, User loginUser, String path, HttpServletResponse response) throws IOException {
        String filename = driver.getPath() + File.separator + loginUser.getUsername() + File.separator + path;
        File file = new File(filename);
        FileInputStream in = new FileInputStream(file);
        response.setContentType(FileType.typeByFile(file).getContentType());
        response.addHeader("Cache-Control","no-cache, no-store, must-revalidate");
        response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.addHeader("Content-Disposition", String.format("filename=\"%s\"", URLEncoder.encode(file.getName(),"UTF-8")));
        response.addHeader("Pragma", "no-cache");
        response.addHeader("Expires", "0");
        ServletOutputStream out = response.getOutputStream();
        int len;
        byte[] buffer = new byte[1024 * 10];
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        out.flush();

        try {
            out.close();
            in.close();
        } catch (NullPointerException e) {
            log.error("responseFileStream stream close() error:NullPointerException" + e.toString());
        } catch (Exception e) {
            log.error("responseFileStream stream close() error:" + e.toString());
        }
    }

    @Override
    public void download(Driver driver, User loginUser, String path, HttpServletResponse response) throws IOException {
        String filename = driver.getPath() + File.separator + loginUser.getUsername() + File.separator + path;
        File file = new File(filename);
        FileInputStream in = new FileInputStream(file);
        response.addHeader("Cache-Control","no-cache, no-store, must-revalidate");
        response.addHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.addHeader("Content-Disposition", String.format("attachment;filename=\"%s\"", URLEncoder.encode(file.getName(),"UTF-8")));
        ServletOutputStream out = response.getOutputStream();
        int len;
        byte[] buffer = new byte[1024 * 10];
        while ((len = in.read(buffer)) != -1) {
            out.write(buffer, 0, len);
        }
        out.flush();

        try {
            out.close();
            in.close();
        } catch (NullPointerException e) {
            log.error("responseFileStream stream close() error:NullPointerException" + e.toString());
        } catch (Exception e) {
            log.error("responseFileStream stream close() error:" + e.toString());
        }
    }


}
