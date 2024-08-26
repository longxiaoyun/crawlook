package io.github.longxiaoyun.samples;

import com.alibaba.fastjson2.JSON;
import io.github.longxiaoyun.pipeline.ConsolePipeline;
import io.github.longxiaoyun.processor.PageProcessor;
import io.github.longxiaoyun.*;

import java.util.ArrayList;
import java.util.List;

public class BaiduPageProcessor implements PageProcessor {

    private Site site;

    @Override
    public void process(Page page) {
        List<String> title = page.getHtml().css("title").all();
        System.out.println("title: " + title);
    }

    @Override
    public Site getSite() {
//        Map<String,Object> meta = new ConcurrentHashMap<>();
        Meta meta = new Meta();
        meta.put("params1", "value1");
        meta.put("params2", "value2");
        meta.put("params3", "value3");
        if (site == null) {
            site = Site.me();
        }
        site.setDomain("baidu.com")
                .setUseGzip(false)
                .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31")
                .setMeta(meta);
        return site;
    }

    public static void main(String[] args) {
        List<SpiderListener> spiderListenerList = new ArrayList<>();
        spiderListenerList.add(new SpiderListener() {

            @Override
            public void onStart(Spider spider) {
                System.out.println("爬取开始了>" + JSON.toJSONString(spider));
            }

            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onClose(Spider spider) {
                System.out.println("爬取结束了>" + JSON.toJSONString(spider));
            }
        });
        String[] urls = {"http://www.baidu.com/", "https://baijiahao.baidu.com/s?id=1807427184714176620", "https://baijiahao.baidu.com/s?id=1807426774377036702",
        "https://baijiahao.baidu.com/s?id=1807425162557096683", "https://baijiahao.baidu.com/s?id=1807424572822957411",
        "https://baijiahao.baidu.com/s?id=1807423881151826759"};

        Spider.create(new BaiduPageProcessor())
                .thread(5)
                .setSpiderListeners(spiderListenerList)
                .addPipeline(new ConsolePipeline())
                .addUrl(urls)
                .run();
    }

}
