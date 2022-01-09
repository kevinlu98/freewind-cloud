package cn.kevinlu98.cloud.freewindcloud.service;

import cn.kevinlu98.cloud.freewindcloud.common.SpringUtil;
import cn.kevinlu98.cloud.freewindcloud.common.enums.DriverType;
import cn.kevinlu98.cloud.freewindcloud.exception.NoDriverTypeException;
import cn.kevinlu98.cloud.freewindcloud.pojo.Driver;
import cn.kevinlu98.cloud.freewindcloud.pojo.FileEntity;
import cn.kevinlu98.cloud.freewindcloud.pojo.User;
import cn.kevinlu98.cloud.freewindcloud.service.cloud.LocalService;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Author: Mr丶冷文
 * Date: 2022-01-08 22:25
 * Email: kevinlu98@qq.com
 * Description:
 */
public abstract class CloudService {

    private static ApplicationContext applicationContext;

    public static CloudService getInstance(DriverType type) throws NoDriverTypeException {
        if (type == DriverType.DRIVER_LOCAL) {
            return SpringUtil.getBean(LocalService.class);
        }
        throw new NoDriverTypeException("不存在的云盘类型:" + type);
    }

    public abstract List<FileEntity> listFile(String driver, String path, User loginUser);


    public abstract String usedSize(String driver, User loginUser);

    public abstract void uploadFile(Driver driver, MultipartFile file, String path, User loginUser) throws Exception;

    public abstract void delete(Driver driver, User loginUser, String path, String target) throws Exception;

    public abstract void fileStream(Driver driver, User loginUser, String path, HttpServletResponse outputStream) throws IOException;

    public abstract void download(Driver driver, User loginUser, String path, HttpServletResponse response) throws IOException;
}
