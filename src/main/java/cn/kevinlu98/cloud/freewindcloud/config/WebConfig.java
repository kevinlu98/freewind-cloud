package cn.kevinlu98.cloud.freewindcloud.config;


import cn.kevinlu98.cloud.freewindcloud.mapper.UserMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.io.File;
import java.io.FileNotFoundException;

/**
 * Author: 鲁恺文
 * Date: 2022-01-04 16:45
 * Email: lukaiwen@xiaomi.com
 * Description:
 */
@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        try {
            registry.addResourceHandler("/uploads/**")
                    .addResourceLocations("file:" + ResourceUtils.getURL("uploads").getPath() + File.separator);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }


    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.addBasenames("classpath:org/springframework/security/messages");
        return messageSource;
    }

}
