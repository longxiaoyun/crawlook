package io.github.longxiaoyun;

import io.github.longxiaoyun.pool.WebDriverPool;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.longxiaoyun.downloader.AbstractDownloader;
import io.github.longxiaoyun.selector.Html;
import io.github.longxiaoyun.selector.PlainText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

/**
 * 使用Selenium调用浏览器进行渲染。目前仅支持chrome。<br>
 * 需要下载Selenium driver支持。<br>
 *
 * @author code4crafter@gmail.com <br>
 * Date: 13-7-26 <br>
 * Time: 下午1:37 <br>
 */
@Component
public class SeleniumDownloader extends AbstractDownloader implements Closeable {

    @Autowired
    private WebDriverPool webDriverPool;

    private static  final Logger logger = LoggerFactory.getLogger(SeleniumDownloader.class);

    private int sleepTime = 0;

    private int threadNum;

    /**
     * Constructor without any filed. Construct PhantomJS browser
     *
     * @author bob.li.0718@gmail.com
     */
    public SeleniumDownloader() {}

    /**
     * set sleep time to wait until load success
     *
     * @param sleepTime sleepTime
     * @return this
     */
    public SeleniumDownloader setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
        return this;
    }

    @Override
    public Page download(Request request, Task task) {
        WebDriver webDriver = null;
        Page page = Page.fail(request);
        try {
            webDriver = webDriverPool.borrowObject();

            logger.info("downloading page {}", request.getUrl());
            webDriver.get(request.getUrl());
            try {
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

            /*
             * TODO You can add mouse event or other processes
             *
             * @author: bob.li.0718@gmail.com
             */

            WebElement webElement = webDriver.findElement(By.xpath("/html"));
            String content = webElement.getAttribute("outerHTML");
            page.setDownloadSuccess(true);
            page.setRawText(content);
            page.setHtml(new Html(content, request.getUrl()));
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

    @Override
    public void setThread(int threadNum) {
        this.threadNum = threadNum;
    }

    @Override
    public void close() throws IOException {
        webDriverPool.close();
    }
}
