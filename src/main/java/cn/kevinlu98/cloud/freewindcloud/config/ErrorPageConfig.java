package cn.kevinlu98.cloud.freewindcloud.config;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * Author: Mr丶冷文
 * Date: 2022-01-08 15:09
 * Email: kevinlu98@qq.com
 * Description:
 */
@Configuration
public class ErrorPageConfig {


    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return factory -> {
            factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"), new ErrorPage(HttpStatus.FORBIDDEN, "/403"), new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500"));
        };
    }
}
