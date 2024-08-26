package io.github.longxiaoyun.config;

import io.github.longxiaoyun.pool.WebDriverFactory;
import io.github.longxiaoyun.pool.WebDriverPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.time.Duration;

/**
 * WebDriver对象池配置
 * @author longxiaoyun
 * @date 2024-08-16
 */
@Configuration
@EnableConfigurationProperties({BrowserPoolProperties.class})
public class WebDriverPoolConfig {

    private final BrowserPoolProperties poolProperties;
    private WebDriverPool pool;

    @Autowired
    public WebDriverPoolConfig(BrowserPoolProperties poolProperties) {
        this.poolProperties = poolProperties;
    }

    @ConditionalOnClass({WebDriverFactory.class})
    @Bean
    protected WebDriverPool webDriverPool() {
        WebDriverFactory webDriverFactory = new WebDriverFactory(poolProperties.getDriver(), poolProperties.getRemoteUrl());
        //设置对象池的相关参数
        GenericObjectPoolConfig<WebDriver> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxIdle(poolProperties.getMaxIdle());
        poolConfig.setMaxTotal(poolProperties.getMaxTotal());
        poolConfig.setMinIdle(poolProperties.getMinIdle());
        poolConfig.setBlockWhenExhausted(true);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        // 定期检查并清除对象池中的空闲对象
        poolConfig.setTimeBetweenEvictionRuns(Duration.ofMinutes(30L));
        // 一定要关闭jmx，不然springboot启动会报已经注册了某个jmx的错误
        poolConfig.setJmxEnabled(false);

        // 新建一个对象池,传入对象工厂和配置
        pool = new WebDriverPool(webDriverFactory, poolConfig);
        // 初始化时，预先创建核心数量的对象
        initPool(poolProperties.getCorePoolSize(), poolProperties.getMaxIdle());
        return pool;
    }

    /**
     * 预先加载testObject对象到对象池中
     * @param initialSize 初始化连接数
     * @param maxIdle     最大空闲连接数
     */
    private void initPool(int initialSize, int maxIdle) {
        if (initialSize <= 0) {
            return;
        }

        int size = Math.min(initialSize, maxIdle);
        for (int i = 0; i < size; i++) {
            try {
                pool.addObject();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @PreDestroy
    public void destroy() {
        if (pool != null) {
            pool.close();
        }
    }

}
