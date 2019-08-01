package space.zyzy.dubhe.aop.springaopdemo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Spring使用注解的配置文件,等同于beans.xml
 */
@Configuration // 表明这是一个Spring配置文件
@ComponentScan(basePackages = "space.zyzy.dubhe.aop.springaopdemo") // 扫描spring组件路径
@EnableAspectJAutoProxy // 开启AOP
public class BeanConfiguration {
}
