package io.github.longxiaoyun;

import io.github.longxiaoyun.pool.WebDriverPool;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.longxiaoyun.downloader.AbstractDownloader;
import io.github.longxiaoyun.selector.Html;
import io.github.longxiaoyun.selector.PlainText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;

/**
 * 使用Selenium调用浏览器进行渲染
 * @author longxiaoyun
 * @date 2024-08-29
 */
@Component
public class SeleniumDownloader extends AbstractDownloader{

    private static  final Logger logger = LoggerFactory.getLogger(SeleniumDownloader.class);

    @Autowired
    private WebDriverPool webDriverPool;

    private String javascript;

    private String proxy;


    private int poolSize;

    /**
     * Constructor without any filed. Construct PhantomJS browser
     *
     * @author bob.li.0718@gmail.com
     */
    public SeleniumDownloader() {}

    @Override
    public Page download(Request request, Task task) {
        WebDriver webDriver = null;
        Page page = Page.fail(request);
        try {
            // 设置池子大小
            webDriverPool.setMaxTotal(poolSize);
            webDriver = webDriverPool.borrowObject();

            logger.info("downloading page {}", request.getUrl());
            webDriver.get(request.getUrl());

            WebDriver.Options manage = webDriver.manage();
            Site site = task.getSite();
            if (site.getCookies() != null) {
                for (Map.Entry<String, String> cookieEntry : site.getCookies()
                        .entrySet()) {
                    Cookie cookie = new Cookie(cookieEntry.getKey(),
                            cookieEntry.getValue());
                    manage.addCookie(cookie);
                }
            }

            if (StringUtils.isNotBlank(javascript)) {
                JavascriptExecutor js = (JavascriptExecutor) webDriver;
                js.executeScript(javascript);
            }

            String content = webDriver.getPageSource();
            page.setDownloadSuccess(true);
            page.setRawText(content);
            page.setUrl(new PlainText(request.getUrl()));
            page.setRequest(request);
            onSuccess(page, task);
        } catch (Exception e) {
            logger.warn("download page {} error", request.getUrl(), e);
            onError(page, task, e);
        } finally {
            if (webDriver != null) {
                webDriverPool.returnObject(webDriver);
            }
        }
        return page;
    }

//    @Override
//    public void setThread(int threadNum) {
//        this.threadNum = threadNum;
//    }

    @Override
    public void connectPoolSize(int poolSize) {
        this.poolSize = poolSize;
    }


    public static class Builder {
        private String javascript;
        private String proxy;
        private int poolSize;

        public Builder() {
        }

        public Builder javascript(String javascript) {
            this.javascript = javascript;
            return this;
        }

        public Builder proxy(String proxy) {
            this.proxy = proxy;
            return this;
        }

        public Builder poolSize(int poolSize) {
            this.poolSize = poolSize;
            return this;
        }

        public SeleniumDownloader build() {
            SeleniumDownloader downloader = new SeleniumDownloader();
            downloader.javascript = this.javascript;
            downloader.proxy = this.proxy;
            downloader.poolSize = this.poolSize;
            return downloader;
        }
    }

}
