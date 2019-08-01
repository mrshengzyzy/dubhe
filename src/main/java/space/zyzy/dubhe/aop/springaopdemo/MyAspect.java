package space.zyzy.dubhe.aop.springaopdemo;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 注解 @Aspect 只是声明了类是一个切面, 而且这个切面并不是Spring写的,如果不告诉Spring
 * 那么Spring是不会知道这个切面的存在的,所以需要使用 @Component 注解
 */
@Component
@Aspect
public class MyAspect {

    /**
     * 前置通知
     */
    @Before("execution(* space.zyzy.dubhe.aop.springaopdemo.UserDao.addUser(..))")
    public void before() {
        System.out.println("前置通知....");
    }

    /**
     * 后置通知
     * returnVal,切点方法执行后的返回值
     */
    @AfterReturning(value = "execution(* space.zyzy.dubhe.aop.springaopdemo.UserDao.addUser(..))", returning = "returnVal")
    public void AfterReturning(Object returnVal) {
        System.out.println("后置通知...." + returnVal);
    }


    /**
     * 环绕通知
     */
    @Around("execution(* space.zyzy.dubhe.aop.springaopdemo.UserDao.addUser(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("环绕通知前....");
        Object obj = joinPoint.proceed();
        System.out.println("环绕通知后....");
        return obj;
    }

    /**
     * 抛出通知
     */
    @AfterThrowing(value = "execution(* space.zyzy.dubhe.aop.springaopdemo.UserDao.addUser(..))", throwing = "e")
    public void afterThrowable(Throwable e) {
        System.out.println("出现异常:msg=" + e.getMessage());
    }

    /**
     * 无论什么情况下都会执行的方法
     */
    @After(value = "execution(* space.zyzy.dubhe.aop.springaopdemo.UserDao.addUser(..))")
    public void after() {
        System.out.println("最终通知....");
    }
}