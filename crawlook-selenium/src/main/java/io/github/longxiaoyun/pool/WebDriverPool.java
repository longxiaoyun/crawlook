package io.github.longxiaoyun.pool;


import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.openqa.selenium.WebDriver;

/**
 * WebDriver对象池
 * @date 2024-08-16
 * @author longxiaoyun
 */
public class WebDriverPool extends GenericObjectPool<WebDriver> {

    public WebDriverPool(PooledObjectFactory<WebDriver> factory) {
        super(factory);
    }

    public WebDriverPool(PooledObjectFactory<WebDriver> factory, GenericObjectPoolConfig<WebDriver> config) {
        super(factory, config);
    }

    public WebDriverPool(PooledObjectFactory<WebDriver> factory, GenericObjectPoolConfig<WebDriver> config, AbandonedConfig abandonedConfig) {
        super(factory, config, abandonedConfig);
    }

}
