package io.github.longxiaoyun;

import io.github.longxiaoyun.pool.WebDriverPool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CompletableFuture;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeleniumApplication.class)
public class WebDriverPoolTest {
    @Autowired
    private WebDriverPool webDriverPool;

    @Test
    public void testGetWebDriver() throws Exception {
        CompletableFuture[] futures = new CompletableFuture[20];
        for (int i=0;i<20;i++) {
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    gotoBaidu();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            futures[i] = future;
        }
        CompletableFuture.allOf(futures).join();

        try {
            Thread.sleep(60_000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void gotoBaidu() throws Exception {
        WebDriver driver = webDriverPool.borrowObject();
        try {
            driver.get("https://www.baidu.com");
            String html = driver.getPageSource();
            String title = driver.getTitle();
            System.out.println("thread: "+Thread.currentThread().getName()+", id: "+ System.identityHashCode(driver)+", title: " + title);
        } catch (Exception e) {
            System.out.println("error " + e);
        } finally {
            webDriverPool.returnObject(driver);
        }
    }

    @Test
    public void testUr() throws Exception {
        WebDriver driver = webDriverPool.borrowObject();
        try {
            driver.get("https://fr.ulike.com/blogs/news/surf-olympique-jo-paris-2024");
            String html = driver.getPageSource();
            String title = driver.getTitle();
            System.out.println("thread: "+Thread.currentThread().getName()+", id: "+ System.identityHashCode(driver)+", title: " + title);
        } catch (Exception e) {
            System.out.println("error " + e);
        } finally {
            webDriverPool.returnObject(driver);
        }
    }
}
