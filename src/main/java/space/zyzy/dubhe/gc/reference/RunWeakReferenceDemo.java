package space.zyzy.dubhe.gc.reference;

import java.util.WeakHashMap;

/**
 * 弱引用的例子
 */
public class RunWeakReferenceDemo {

    /**
     * 这里拿WeakHashMap作为例子说明：
     * WeakHashMap中的key是 WeakReference,一旦key引用的对象被GC,那么key-value对应的Entry也会被移除
     * 更直白的说就是 —— 即使没有显示的添加或删除任何元素，也可能发生如下情况：
     * 1.调用两次size()方法返回不同的值；
     * 2.两次调用isEmpty()方法，第一次返回false，第二次返回true；
     * 3.两次调用containsKey()方法，第一次返回true，第二次返回false，尽管两次使用的是同一个key；
     * 4.两次调用get()方法，第一次返回一个value，第二次返回null，尽管两次使用的是同一个对象。
     * 多运行下面的例子可以看到如下结果：
     * ================
     * Map Size=3
     * ================
     * Map Size=2
     * key1不是被显示移除而是被回收了
     */
    public static void main(String[] args) {

        WeakHashMap<Key, String> weakHashMap = new WeakHashMap<>();

        // 定义几组key和value
        Key key1 = new Key();
        String value1 = "value1";
        Key key2 = new Key();
        String value2 = "value2";
        Key key3 = new Key();
        String value3 = "value3";

        // 添加到 weakHashMap 中
        weakHashMap.put(key1, value1);
        weakHashMap.put(key2, value2);
        weakHashMap.put(key3, value3);

        // 此时map的大小是3
        System.out.println("================");
        System.out.println("Map Size=" + weakHashMap.size());
        System.out.println("================");

        /*
         * 1.释放key1的引用
         * 2.强制GC回收key1引用的对象
         * 3.再次查看map的大小,由于GC是JVM决定是否执行,所以可能多试几次
         */
        key1 = null;
        System.gc();
        System.out.println("Map Size=" + weakHashMap.size());
    }

    static class Key {
    }
}