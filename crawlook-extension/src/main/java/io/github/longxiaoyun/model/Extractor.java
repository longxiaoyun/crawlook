package io.github.longxiaoyun.model;

import io.github.longxiaoyun.model.sources.Source;
import lombok.Getter;
import lombok.Setter;

import io.github.longxiaoyun.selector.Selector;

/**
 * The object contains 'ExtractBy' information.
 * @author code4crafter@gmail.com <br>
 * @since 0.2.0
 */
public class Extractor {

    @Getter @Setter
    protected Selector selector;

    @Getter
    protected final Source source;

    protected final boolean notNull;

    protected final boolean multi;
  
    public Extractor(Selector selector, Source source, boolean notNull, boolean multi) {
        this.selector = selector;
        this.source = source;
        this.notNull = notNull;
        this.multi = multi;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public boolean isMulti() {
        return multi;
    }
}
