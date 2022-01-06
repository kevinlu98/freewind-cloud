package cn.kevinlu98.cloud.freewindcloud;

import cn.kevinlu98.cloud.freewindcloud.common.enums.Size;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class FreewindCloudApplicationTests {

    @Test
    void contextLoads() {

        float v = Size.convertBase("100mb");
        System.out.println(v);
        System.out.println(Size.BTrim.convert(v));
    }

}
