package io.github.longxiaoyun;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.github.longxiaoyun.downloader.Downloader;
import io.github.longxiaoyun.model.HttpRequestBody;
import io.github.longxiaoyun.utils.Experimental;
import io.github.longxiaoyun.scheduler.PriorityScheduler;
import io.github.longxiaoyun.utils.HttpConstant;

/**
 * Object contains url to crawl.<br>
 * It contains some additional information.<br>
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.1.0
 */
public class Request implements Serializable {

    private static final long serialVersionUID = 2062192774891352043L;

    public static final String CYCLE_TRIED_TIMES = "_cycle_tried_times";

    private String url;

    private String method;

    private HttpRequestBody requestBody;

    /**
     * this req use this downloader
     */
    private Downloader downloader;

    /**
     * Store additional information in extras.
     */
    private Map<String, Object> extras = new HashMap<>();

    /**
     * cookies for current url, if not set use Site's cookies
     */
    private Map<String, String> cookies = new HashMap<String, String>();

    private Map<String, String> headers = new HashMap<String, String>();

    /**
     * Priority of the request.<br>
     * The bigger will be processed earlier. <br>
     * @see PriorityScheduler
     */
    private long priority;

    /**
     * When it is set to TRUE, the downloader will not try to parse response body to text.
     *
     */
    private boolean binaryContent = false;

    private String charset;

    public Request() {
    }

    public Request(String url) {
        this.url = url;
    }

    public long getPriority() {
        return priority;
    }

    /**
     * Set the priority of request for sorting.<br>
     * Need a scheduler supporting priority.<br>
     * @see PriorityScheduler
     *
     * @param priority priority
     * @return this
     */
    @Experimental
    public Request setPriority(long priority) {
        this.priority = priority;
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T> T getExtra(String key) {
        if (extras == null) {
            return null;
        }
        return (T) extras.get(key);
    }

    @SuppressWarnings("unchecked")
    public <T> T getExtra(String key, T defaultValue) {
        if (extras == null) {
            return defaultValue;
        }
        return extras.get(key) != null ? (T) extras.get(key) : defaultValue;
    }

    public <T> Request putExtra(String key, T value) {
        extras.put(key, value);
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, Object> getExtras() {
        return Collections.unmodifiableMap(extras);
    }

    public Request setExtras(Map<String, Object> extras) {
        this.extras.putAll(extras);
        return this;
    }

    public Request setUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * The http method of the request. Get for default.
     * @return httpMethod
     * @see HttpConstant.Method
     * @since 0.5.0
     */
    public String getMethod() {
        return method;
    }

    public Request setMethod(String method) {
        this.method = method;
        return this;
    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (method != null ? method.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Request request = (Request) o;

        if (!Objects.equals(url, request.url)) {
            return false;
        }
        return Objects.equals(method, request.method);
    }

    public Request addCookie(String name, String value) {
        cookies.put(name, value);
        return this;
    }

    public Request addHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public HttpRequestBody getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(HttpRequestBody requestBody) {
        this.requestBody = requestBody;
    }

    public boolean isBinaryContent() {
        return binaryContent;
    }

    public Downloader getDownloader() {
        return downloader;
    }

    public void setDownloader(Downloader downloader) {
        this.downloader = downloader;
    }

    public Request setBinaryContent(boolean binaryContent) {
        this.binaryContent = binaryContent;
        return this;
    }

    public String getCharset() {
        return charset;
    }

    public Request setCharset(String charset) {
        this.charset = charset;
        return this;
    }

    @Override
    public String toString() {
        return "Request{" +
                "url='" + url + '\'' +
                ", method='" + method + '\'' +
                ", extras=" + extras +
                ", priority=" + priority +
                ", headers=" + headers +
                ", cookies="+ cookies+
                '}';
    }

}
