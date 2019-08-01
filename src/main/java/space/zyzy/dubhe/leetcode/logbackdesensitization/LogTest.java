package space.zyzy.dubhe.leetcode.logbackdesensitization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class LogTest {

    private static final Logger logger = LoggerFactory.getLogger(LogTest.class);

    public static void main(String[] args) {

        PrintObject object1 = new PrintObject("PrintObject1");
        PrintObject object2 = new PrintObject("PrintObject2");
        logger.info("打印对象1{},打印对象2{}", object1, object2);

        List<String> test = new ArrayList<String>() {{
            add("apple");
            add("banana");
            add("pea");
        }};
        logger.info("打印list{}", test);

        UnSensitiveObject unSensitiveObject = new UnSensitiveObject("UnSensitiveObject");
        logger.info("打印敏感对象{},打印非敏感对象{}", object1, unSensitiveObject);

    }
}
