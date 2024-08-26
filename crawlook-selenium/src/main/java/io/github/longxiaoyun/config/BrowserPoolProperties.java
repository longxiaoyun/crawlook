package io.github.longxiaoyun.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 浏览器池配置
 * @author longxiaoyun
 * @date 2024-08-16
 */
@ConfigurationProperties(prefix = "browser.pool")
@Setter
@Getter
public class BrowserPoolProperties {
    private int corePoolSize;
    private int maxIdle;
    private int maxTotal;
    private int minIdle;
    private String remoteUrl;
    /**
     * chromium
     * chrome
     * firefox
     * edge
     */
    private String driver;
}
