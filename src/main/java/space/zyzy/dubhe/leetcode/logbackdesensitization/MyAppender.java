package space.zyzy.dubhe.leetcode.logbackdesensitization;

import ch.qos.logback.core.AppenderBase;

/**
 * 自定义Appender
 */
public class MyAppender extends AppenderBase {

    @Override
    protected void append(Object eventObject) {
        System.out.println("[MyAppender]" + eventObject);
    }
}
