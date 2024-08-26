//package io.github.longxiaoyun.downloader;
//
//import io.github.longxiaoyun.Page;
//import io.github.longxiaoyun.Request;
//import io.github.longxiaoyun.Task;
//import io.github.longxiaoyun.selector.PlainText;
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.Response;
//import org.jetbrains.annotations.NotNull;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import java.io.IOException;
//
///**
// * @author longxiaoyun
// * @date 2024-08-19
// */
//@Component
//public class OkHttpClient extends AbstractDownloader{
//    @Autowired
//    private okhttp3.OkHttpClient okHttp3Client;
//
//    private static final Logger logger = LoggerFactory.getLogger(OkHttpClient.class);
//
//    @Override
//    public Page download(Request request, Task task) {
//        if (logger.isInfoEnabled()) {
//            logger.info("downloading page: {}", request.getUrl());
//        }
//        Page page = Page.fail(request);
//
//        okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
//        okhttp3.Request req = builder.url(request.getUrl()).build();
//        Call call = okHttp3Client.newCall(req);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, IOException e) {
//                onError(page, task, e);
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                if (response.isSuccessful() && response.body() != null) {
//                    page.setStatusCode(response.code());
//                    page.setUrl(new PlainText(request.getUrl()));
//                    page.setRawText(response.body().string());
//                    page.setDownloadSuccess(true);
//                    page.setRequest(request);
//                    page.setHeaders(response.headers().toMultimap());
//                    onSuccess(page, task);
//                } else {
//                    page.setStatusCode(response.code());
//                    page.setDownloadSuccess(false);
//                    onError(page, task, new IOException("StatusCode: " + response.code()));
//                }
//            }
//        });
//        return null;
//    }
//
//    @Override
//    public void setThread(int threadNum) {
//
//    }
//}
