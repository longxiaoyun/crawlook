package io.github.longxiaoyun;

/**
 * Listener of Spider on page processing. Used for monitor and such on.
 *
 * @author code4crafer@gmail.com
 * @since 0.5.0
 */
public interface SpiderListener {

    default void onStart(Spider spider) {};

    default void onSuccess(Request request) {};

    void onError(Request request, Exception e);

    default void onClose(Spider spider) {};
}
