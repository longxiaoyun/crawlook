package io.github.longxiaoyun.model.sources;

import io.github.longxiaoyun.Page;
import io.github.longxiaoyun.model.FieldExtractor;
import io.github.longxiaoyun.model.fields.MultipleField;
import io.github.longxiaoyun.model.fields.PageField;
import io.github.longxiaoyun.model.fields.SingleField;

public class SourceTextExtractor {
   public static PageField getText(Page page, String html, boolean isRaw, FieldExtractor fieldExtractor) {
      Source source = fieldExtractor.getSource();
      if (fieldExtractor.isMulti())
         return new MultipleField(source.getTextList(page, html, isRaw, fieldExtractor));
      else
         return new SingleField(source.getText(page, html, isRaw, fieldExtractor));
   }
}