package space.zyzy.dubhe.datastructure.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class WeakHashMapDemo {

    /**
     * size的大小，会由小变大，突然变小，然后再由小变大
     * 这个说明垃圾回收期在器作用，这个和jdk的说明文档是一致的。
     * 当GC开始回收的时候会发现，map的key已经不被使用了，即使map中key和value的映射已然存在，也不能阻止map的key被GC回收掉。
     * 当map的key被回收了之后，value对象也会被顺利地回收。
     */
    public static void test1() {
        byte[][] key;
        WeakHashMap<byte[][], byte[][]> maps = new WeakHashMap<>();
        for (int i = 0; i < 10000; i++) {
            key = new byte[1000][1000];
            maps.put(key, new byte[1000][1000]);
            System.err.println(" size" + maps.size());

        }
    }

    /**
     * size会越来越大直至OOM
     * map的key放到一个list中，这样能保证方法体中的所有key都是被引用的，所以在启动垃圾回收的时候，
     * weak中的key一个都不会被自动回收，所以OOM就在所难免了。
     */
    public static void test2() {
        List<byte[][]> keys = new ArrayList<>();
        byte[][] key;
        WeakHashMap<byte[][], byte[][]> maps = new WeakHashMap<>();
        for (int i = 0; i < 10000; i++) {
            key = new byte[1000][1000];
            keys.add(key);
            maps.put(key, new byte[1000][1000]);
            System.err.println(" size" + maps.size());
        }
    }


    public static void main(String[] args) {
//        test1();
        test2();
    }
}
