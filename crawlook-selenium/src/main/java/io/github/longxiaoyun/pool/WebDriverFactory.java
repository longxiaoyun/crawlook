package io.github.longxiaoyun.pool;

import com.beust.jcommander.internal.Lists;
import io.github.longxiaoyun.enums.WebDriverEnum;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.util.Set;

/**
 * WebDriver工厂类
 * @date 2024-08-16
 * @author longxiaoyun
 */
public class WebDriverFactory implements PooledObjectFactory<WebDriver> {

    private final String driverType;
    private final String remoteUrl;

    public WebDriverFactory(String driverType, String remoteUrl) {
        this.driverType = driverType;
        this.remoteUrl = remoteUrl;
    }

    /**
     * 构造一个对象
     * @return 浏览器对象
     * @throws Exception 异常
     */
    @Override
    public PooledObject<WebDriver> makeObject() throws Exception {
        if (StringUtils.isNotBlank(remoteUrl) && StringUtils.isNotBlank(driverType)) {
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setBrowserName(driverType);
            return new DefaultPooledObject<>(new RemoteWebDriver(new URL(remoteUrl), desiredCapabilities));
        }
        if (driverType.equalsIgnoreCase(WebDriverEnum.CHROME.getDriver())) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setHeadless(false);
            chromeOptions.setAcceptInsecureCerts(true);
            chromeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
            chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
            chromeOptions.setExperimentalOption("excludeSwitches", Lists.newArrayList("enable-automation"));
            chromeOptions.addArguments("--disable-blink-features");
            chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
            chromeOptions.addArguments("--remote-allow-origins=*");
            WebDriver chromeDriver = WebDriverManager.chromedriver().capabilities(chromeOptions).create();
            return new DefaultPooledObject<>(chromeDriver);
        } else if (driverType.equalsIgnoreCase(WebDriverEnum.FIREFOX.getDriver())) {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setHeadless(true);
            firefoxOptions.setAcceptInsecureCerts(true);
            firefoxOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
            firefoxOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
            WebDriver firefoxDriver = WebDriverManager.firefoxdriver().capabilities(firefoxOptions).create();
            return new DefaultPooledObject<>(firefoxDriver);
        } else if (driverType.equalsIgnoreCase(WebDriverEnum.EDGE.getDriver())) {
            EdgeOptions edgeOptions = new EdgeOptions();
            edgeOptions.setHeadless(true);
            edgeOptions.setAcceptInsecureCerts(true);
            edgeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
            edgeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
            WebDriver edgeDriver = WebDriverManager.edgedriver().capabilities(edgeOptions).create();
            return new DefaultPooledObject<>(edgeDriver);
        } else if (driverType.equalsIgnoreCase(WebDriverEnum.CHROMIUM.getDriver())) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setHeadless(true);
            chromeOptions.setAcceptInsecureCerts(true);
            chromeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
            chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
            chromeOptions.setExperimentalOption("excludeSwitches", Lists.newArrayList("enable-automation"));
            chromeOptions.addArguments("--disable-blink-features");
            chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
            chromeOptions.addArguments("--remote-allow-origins=*");
            WebDriver chromiumDriver = WebDriverManager.chromiumdriver().capabilities(chromeOptions).create();
            return new DefaultPooledObject<>(chromiumDriver);
        } else {
            throw new IllegalArgumentException("不支持的浏览器类型");
        }
    }
    /**
     * 销毁对象
     * @param p 对象
     * @throws Exception 异常
     */
    @Override
    public void destroyObject(PooledObject<WebDriver> p) throws Exception {
        WebDriver driver = p.getObject();
        if (driver != null) {
            driver.quit();
        }
    }
    /**
     * 验证对象是否可用
     * @param p 对象
     * @return 是否可用
     */
    @Override
    public boolean validateObject(PooledObject<WebDriver> p) {
        WebDriver driver = p.getObject();
        return driver != null && ((RemoteWebDriver) driver).getSessionId() != null;
    }
    /**
     * 激活一个浏览器对象，使其可用
     * @param p 对象
     * @throws Exception 异常
     */
    @Override
    public void activateObject(PooledObject<WebDriver> p) throws Exception {
        p.getObject().navigate().refresh();
    }
    /**
     * 钝化一个对象,也可以理解为反初始化
     * @param p 对象
     * @throws Exception 异常
     */
    @Override
    public void passivateObject(PooledObject<WebDriver> p) throws Exception {
        WebDriver driver = p.getObject();
        driver.manage().deleteAllCookies();
        Set<String> activeWindow = driver.getWindowHandles();
        if (activeWindow.size() > 1) {
            // just keep one window
            String base = driver.getWindowHandle();
            activeWindow.remove(base);
            // switch and close all other windows
            driver.switchTo().window(p.getObject().getWindowHandles().iterator().next());
            driver.close();
            // switch back to base window
            driver.switchTo().window(base);
        }
        driver.navigate().to("about:blank");
    }
}
