package io.github.longxiaoyun.model;

import io.github.longxiaoyun.ResultItems;
import io.github.longxiaoyun.Task;
import io.github.longxiaoyun.model.annotation.ExtractBy;
import io.github.longxiaoyun.pipeline.CollectorPageModelPipeline;
import io.github.longxiaoyun.pipeline.CollectorPipeline;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author code4crafter@gmail.com
 * @since 0.4.0
 */
class PageModelCollectorPipeline<T> implements CollectorPipeline<T> {

    private final CollectorPageModelPipeline<T> classPipeline = new CollectorPageModelPipeline<T>();

    private final Class<?> clazz;

    PageModelCollectorPipeline(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public List<T> getCollected() {
        return classPipeline.getCollected();
    }

    @Override
    public synchronized void process(ResultItems resultItems, Task task) {
        Object o = resultItems.get(clazz.getCanonicalName());
        if (o != null) {
            Annotation annotation = clazz.getAnnotation(ExtractBy.class);
            if (annotation == null || !((ExtractBy) annotation).multi()) {
                classPipeline.process((T) o, task);
            } else {
                List<Object> list = (List<Object>) o;
                for (Object o1 : list) {
                   classPipeline.process((T) o1, task);
                }
            }
        }
    }
}
