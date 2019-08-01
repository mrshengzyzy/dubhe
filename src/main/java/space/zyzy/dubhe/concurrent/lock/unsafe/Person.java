package space.zyzy.dubhe.concurrent.lock.unsafe;

/**
 * Person中的两个字段被设置为private
 * 通过Unsafe的实例来修改他们的值
 */
public class Person {

    private String name;

    private int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "[name:" + name + ",age:" + age + "]";
    }
}
