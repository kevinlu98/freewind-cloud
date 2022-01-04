package cn.kevinlu98.cloud.freewindcloud;

import cn.kevinlu98.cloud.freewindcloud.common.enums.SizeConverter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FreewindCloudApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(SizeConverter.BTrim.convert(1073742828f));
    }

}
