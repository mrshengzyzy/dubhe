package space.zyzy.dubhe.leetcode.logbackdesensitization;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import com.alibaba.fastjson.JSON;
import org.slf4j.helpers.MessageFormatter;

import java.lang.annotation.Annotation;

/**
 * 自定义的Converter
 * converter最终用来将LoggingEvent转换为日志字符串
 * PatternLayout 默认使用的是 MessageConverter
 */
public class MyConverter extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent event) {

        String message = event.getMessage();
        Object[] argumentArray = event.getArgumentArray();

        Object[] copy = new Object[argumentArray.length];
        for (int i = 0; i < argumentArray.length; i++) {
            Object target = argumentArray[i];
            Class clazz = target.getClass();
            Annotation annotation = clazz.getDeclaredAnnotation(Desensitization.class);
            if (annotation == null) {
                copy[i] = target;
            } else {



                copy[i] = JSON.toJSONString(target);
            }
        }

        return MessageFormatter.arrayFormat(message, copy).getMessage();
    }
}
