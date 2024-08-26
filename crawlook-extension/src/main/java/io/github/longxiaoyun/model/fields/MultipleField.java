package io.github.longxiaoyun.model.fields;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import io.github.longxiaoyun.model.formatter.ObjectFormatter;
import org.slf4j.Logger;

import lombok.Getter;
import io.github.longxiaoyun.model.FieldExtractor;

public class MultipleField extends PageField {
   @Getter
   private List<String> fieldNames; 

   public MultipleField(List<String> fieldNames) {
      this.fieldNames = fieldNames;
   }
   
   public boolean operation(Object o, FieldExtractor fieldExtractor, Logger logger) throws IllegalAccessException, InvocationTargetException {
      if ((this.fieldNames == null || this.fieldNames.size() == 0) && fieldExtractor.isNotNull())
         return false;
      if (fieldExtractor.getObjectFormatter() != null) {
         List<Object> converted = this.convert(this.fieldNames, fieldExtractor.getObjectFormatter(), logger);
         setField(o, fieldExtractor, converted);
      }
      else
         setField(o, fieldExtractor, this.fieldNames);
      return true;
   }

   private List<Object> convert(List<String> values, ObjectFormatter objectFormatter, Logger logger) {
      List<Object> objects = new ArrayList<>();
      for (String value : values) {
          Object converted = this.convert(value, objectFormatter, logger);
          if (converted != null)
              objects.add(converted);
      }
      return objects;
  }
}