package io.github.longxiaoyun.downloader;

import io.github.longxiaoyun.Page;
import io.github.longxiaoyun.Request;
import io.github.longxiaoyun.Site;
import io.github.longxiaoyun.Task;
import io.github.longxiaoyun.selector.Html;

/**
 * Base class of downloader with some common methods.
 *
 * @author code4crafter@gmail.com
 * @since 0.5.0
 */
public abstract class AbstractDownloader implements Downloader {

    /**
     * A simple method to download a url.
     *
     * @param url url
     * @return html
     */
    public Html download(String url) {
        return download(url, null);
    }

    /**
     * A simple method to download a url.
     *
     * @param url     url
     * @param charset charset
     * @return html
     */
    public Html download(String url, String charset) {
        Page page = download(new Request(url), Site.me().setCharset(charset).toTask());
        return page.getHtml();
    }


    /**
     * @param page the {@link Page}.
     * @param task the {@link Task}.
     * @since 0.10.0
     */
    protected void onSuccess(Page page, Task task) {

    }

    /**
     * @param page the {@link Page}.
     * @param task the {@link Task}.
     * @param e the exception.
     * @since 0.10.0
     */
    protected void onError(Page page, Task task, Throwable e) {

    }

}
