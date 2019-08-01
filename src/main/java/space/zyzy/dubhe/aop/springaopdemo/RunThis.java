package space.zyzy.dubhe.aop.springaopdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Spring AOP 采用动态代理技术运行时生效,而AspectJ使用acj编译器(aspectjtools.jar提供,代替javac编译器)编译期织入源文件
 * 如果使用xml配置方式,那么不需要任何aspectj相关的jar包
 * 如果使用注解的方式配置,那么就依赖于aspectjweaver.jar包,因为要复用注解
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BeanConfiguration.class})
public class RunThis {

    @Autowired
    private UserDao userDao;

    @Test
    public void aspectJTest() {
        userDao.addUser();
    }
}