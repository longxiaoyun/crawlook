package io.github.longxiaoyun.proxy;

import io.github.longxiaoyun.Page;
import io.github.longxiaoyun.Request;
import io.github.longxiaoyun.Task;

/**
 * Proxy provider. <br>
 *     
 * @since 0.7.0
 */
public interface ProxyProvider {

    /**
     *
     * Return proxy to Provider when complete a download.
     * @param proxy the proxy config contains host,port and identify info
     * @param page the download result
     * @param task the download task
     */
    void returnProxy(Proxy proxy, Page page, Task task);


    /**
     * Returns a proxy for the request.
     *
     * @param request the request
     * @param task the download task
     * @return proxy
     * @since 0.9.0
     */
    Proxy getProxy(Request request, Task task);

}
