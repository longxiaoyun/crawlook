package io.github.longxiaoyun.model;

import io.github.longxiaoyun.model.formatter.ObjectFormatter;
import io.github.longxiaoyun.model.sources.Source;
import io.github.longxiaoyun.selector.Selector;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import lombok.Getter;
import lombok.Setter;

/**
 * Wrapper of field and extractor.
 * @author code4crafter@gmail.com <br>
 * @since 0.2.0
 */
public class FieldExtractor extends Extractor {

    @Getter
    private final Field field;

    @Getter @Setter
    private Method setterMethod;

    @Getter @Setter
    private ObjectFormatter objectFormatter;

    public FieldExtractor(Field field, Selector selector, Source source, boolean notNull, boolean multi) {
        super(selector, source, notNull, multi);
        this.field = field;
    }
}
