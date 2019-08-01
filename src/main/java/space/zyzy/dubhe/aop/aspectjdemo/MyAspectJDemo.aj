package space.zyzy.dubhe.aop.aspectjdemo;

/**
 * 定义一个切面
 * this file was declared 'public aspect' but not 'public class'
 * 参考地址：https://blog.csdn.net/javazejian/article/details/56267036
 * 1、使用前请先安装AspectJ
 * 2、添加aspectjrt.jar到库路径
 * 3、向Facets中加AspectJ模块
 * 3、添加acj编译器并设置编译器路径为aspectjtools.jar
 */
public aspect MyAspectJDemo {

    /**
     * 定义切点,日志记录切点
     */
    pointcut recordLog():call(* space.zyzy.dubhe.aop.aspectjdemo.RunThis.sayHello(..));

    /**
     * 定义切点,权限验证(实际开发中日志和权限一般会放在不同的切面中,这里仅为方便演示)
     */
    pointcut authCheck():call(* space.zyzy.dubhe.aop.aspectjdemo.RunThis.sayHello(..));

    /**
     * 定义前置通知!
     */
    before():authCheck(){
        System.out.println("sayHello方法执行前验证权限");
    }

    /**
     * 定义后置通知
     */
    after():recordLog(){
        System.out.println("sayHello方法执行后记录日志");
    }
}
