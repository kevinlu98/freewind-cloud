package cn.kevinlu98.cloud.freewindcloud.runner;

import cn.kevinlu98.cloud.freewindcloud.common.Passwd;
import cn.kevinlu98.cloud.freewindcloud.common.Website;
import cn.kevinlu98.cloud.freewindcloud.common.enums.DriverType;
import cn.kevinlu98.cloud.freewindcloud.common.enums.Role;
import cn.kevinlu98.cloud.freewindcloud.mapper.UserMapper;
import cn.kevinlu98.cloud.freewindcloud.pojo.Driver;
import cn.kevinlu98.cloud.freewindcloud.pojo.User;
import cn.kevinlu98.cloud.freewindcloud.service.DriverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Author: 鲁恺文
 * Date: 2022-01-04 16:47
 * Email: lukaiwen@xiaomi.com
 * Description:
 */
@Slf4j
@Component
public class DataInitRunner implements ApplicationRunner {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DriverService driverService;

    @Autowired
    private Website website;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        Integer count = userMapper.countByRole(Role.ROLE_ADMIN.getValue());
        if (count == 0) {
            String username = Passwd.rdmPass(8);
            String password = Passwd.rdmPass(8);
            User user = User.builder()
                    .nickname(username)
                    .username(username)
                    .password(Passwd.md5(password))
                    .avatar(website.userDefaultAvatar())
                    .role(Role.ROLE_ADMIN.getValue())
                    .size(website.defaultMaxSize())
                    .limitSize(website.uploadLimit())
                    .build();
            userMapper.save(user);

            log.info("以下是管理员信息：");
            log.info("用户名:{}", username);
            log.info("密码:{}", password);
        }

        Driver driver = driverService.activeDriver();
        if (driver==null){
            driverService.save(Driver.builder().name("默认空间").maxSize("50G").type(DriverType.DRIVER_LOCAL.getValue()).path("./driver").build());
        }
    }
}
