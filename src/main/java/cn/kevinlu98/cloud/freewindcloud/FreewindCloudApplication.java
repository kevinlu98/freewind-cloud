package cn.kevinlu98.cloud.freewindcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class FreewindCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreewindCloudApplication.class, args);
    }

}
