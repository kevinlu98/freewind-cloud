package cn.kevinlu98.cloud.freewindcloud;

import cn.kevinlu98.cloud.freewindcloud.common.Passwd;
import cn.kevinlu98.cloud.freewindcloud.common.Website;
import cn.kevinlu98.cloud.freewindcloud.common.enums.Role;
import cn.kevinlu98.cloud.freewindcloud.common.enums.Size;
import cn.kevinlu98.cloud.freewindcloud.mapper.UserMapper;
import cn.kevinlu98.cloud.freewindcloud.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class FreewindCloudApplicationTests {


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private Website website;

    @Test
    void contextLoads() {
        for (int i = 0; i < 100; i++) {
            String username = Passwd.rdmPass(8);
            String password = Passwd.rdmPass(8);
            User user = User.builder()
                    .nickname(username)
                    .username(username)
                    .password(Passwd.md5(password))
                    .email(Passwd.rdmPass(8)+"@qq.com")
                    .avatar(website.userDefaultAvatar())
                    .role(Role.ROLE_USER.getValue())
                    .size(website.defaultMaxSize())
                    .limitSize(website.uploadLimit())
                    .build();
            userMapper.save(user);
        }
    }

}
