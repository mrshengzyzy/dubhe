package space.zyzy.dubhe.aop.dynamicproxy;

/**
 * iPhone实现类
 */
public class IPhoneImpl implements IPhone {

    private String name = "iPhone";

    @Override
    public void printName() {
        System.out.println("名字是" + name);
    }
}
