package io.github.longxiaoyun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 * @author longxiaoyun
 * @date 2024-08-16
 */
@SpringBootApplication(scanBasePackages = {"io.github.longxiaoyun"})
public class SeleniumApplication {
    public static void main(String[] args) {
        SpringApplication.run(SeleniumApplication.class);
    }
}
