package space.zyzy.dubhe.aop.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 注意实现了 InvocationHandler 接口
 */
public class RunDynamicProxyDemo implements InvocationHandler {

    /**
     * 被代理的对象,真实的对象
     */
    private IPhoneImpl iPhone;

    private RunDynamicProxyDemo(IPhoneImpl iPhone) {
        this.iPhone = iPhone;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        System.out.println("准备打印手机名称");
        iPhone.printName();
        System.out.println("打印手机名称完毕");
        return null;
    }

    public static void main(String[] args) {

        IPhoneImpl myRealGoal = new IPhoneImpl();
        Class<?> clazz = myRealGoal.getClass();

        /*
         * 代理对象
         * 既然是代理,那么可以执行跟原对象相同的方法
         */
        IPhone iPhone = (IPhone) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new RunDynamicProxyDemo(myRealGoal));
        iPhone.printName();
    }
}