package space.zyzy.dubhe.javastructure;

/**
 * intern()方法问题
 */
public class StringObject {

    public static void main(String[] args) {

        String s0 = "apple";
        String s1 = new String("apple");
        String s2 = new String("apple");

        // 显然s0指向的是常量池对象,s1指向的是堆中的String对象,两者不可能相等
        System.out.println(s0 == s1); // false

        // 将S1的value值放入常量池并返回其引用
        // 虽然返回了引用但是并没有赋给s1
        s1.intern();
        System.out.println(s0 == s1); // flase

        // 将引用返回给s2
        s2 = s2.intern();
        System.out.println(s0 == s2); // true

        // 很显然此时是相等的
        System.out.println(s2 == s1.intern()); // true

    }
}
