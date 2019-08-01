package space.zyzy.dubhe.leetcode.logbackdesensitization;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE, ElementType.FIELD})
@Documented
public @interface Desensitization {
}
