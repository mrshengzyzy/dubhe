package space.zyzy.dubhe.concurrent.lock.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Unsafe示例Demo
 * 参考文章:http://xiaobaoqiu.github.io/blog/2014/11/08/jie-mi-sun-dot-misc-dot-unsafe/
 */
public class UnsafeTest {

    public static void main(String[] args) throws Exception {

        // 通过反射获取UnSafe实例
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);

        // 拿到Person类中连个字段的内存偏移量
        final long nameOffset = unsafe.objectFieldOffset(Person.class.getDeclaredField("name"));
        final long ageOffset = unsafe.objectFieldOffset(Person.class.getDeclaredField("age"));
        System.out.println("[name offset]: " + nameOffset);
        System.out.println("[age offset]: " + ageOffset);

        // 新建一个Person实例
        Person person = new Person("John", 20);
        System.out.println("[new person] " + person);

        // 通过unsafe类拿到响应属性值
        String name = (String) unsafe.getObject(person, nameOffset);
        Integer age = unsafe.getInt(person, ageOffset);
        System.out.println("[Unsafe get][" + name + ":" + age + "]");

        // 更改属性值
        unsafe.putObject(person, nameOffset, "Jane");
        unsafe.putInt(person, ageOffset, 21);
        System.out.println("[Unsafe set] " + person);
    }
}

