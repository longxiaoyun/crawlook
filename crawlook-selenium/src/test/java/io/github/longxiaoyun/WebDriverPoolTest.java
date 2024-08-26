package io.github.longxiaoyun;

import io.github.longxiaoyun.pool.WebDriverPool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeleniumApplication.class)
public class WebDriverPoolTest {
    @Autowired
    private WebDriverPool webDriverPool;

    @Test
    public void testGetWebDriver() throws Exception {
        WebDriver driver = webDriverPool.borrowObject();
        try {
            driver.get("https://www.baidu.com");
            String html = driver.getPageSource();
            String title = driver.getTitle();
            System.out.println("title: " + title);
        }catch (Exception e) {
            System.out.println("error " + e);
        } finally {
            webDriverPool.returnObject(driver);
        }

    }
}
