package io.github.longxiaoyun;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author longjiang
 * @date 2024-08-29
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeleniumApplication.class)
public class SeleniumDownloaderTest {

    public void testChrome() {
        SeleniumDownloader downloader = new SeleniumDownloader.Builder().javascript("alert('你好')").build();
    }
}
