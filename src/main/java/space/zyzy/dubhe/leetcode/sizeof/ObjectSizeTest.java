package space.zyzy.dubhe.leetcode.sizeof;

/**
 * 普通对象的结构
 * 1. 对象头(_mark), 8个字节
 * 2. Oop指针,如果是32G内存以下的,默认开启对象指针压缩,4个字节
 * 3. 数据区
 * 4.Padding(内存对齐),按照8的倍数对齐
 * 数组对象结构
 * 1. 对象头(_mark), 8个字节
 * 2. Oop指针,如果是32G内存以下的,默认开启对象指针压缩,4个字节
 * 3. 数组长度,4个字节
 * 4. 数据区
 * 5. Padding(内存对齐),按照8的倍数对齐
 * 可以看到数组币普通对象多了数组长度这一项
 */
public class ObjectSizeTest {

    public static void main(String[] args) throws IllegalAccessException {
        final ClassIntrospector ci = new ClassIntrospector();

        ObjectInfo res = ci.introspect(new EmptyObject());
        System.out.println(res.getDeepSize());
    }
}

/**
 * EmptyObject 大小为 0
 * 也可以看出 static 对象是不占用对象本身大小的
 */
class EmptyObject {
    static int i = 3;
}

/**
 * 8(_mark) + 4(oop指针) + 1(boolean大小) = 13 =====> 16
 */
class OneObject {
    boolean b;
}

/**
 * 8(_mark) + 4(oop指针)  + 4(OneObject引用) = 16
 */
class TwoObject {
    OneObject one;
}

/**
 * ThreeObject: 8(_mark) + 4(oop指针)  + 4(OneObject[]引用) = 16
 * new OneObject[2]数组: 8(_mark) + 4(oop指针) + 4(数组长度占4个字节) + 4(oneObjects[0]引用) + 4(oneObjects[1]引用) = 24
 * OneObject： 8(_mark) + 4(oop指针) + 1(boolean大小) = 13 =====> 16
 * 因此整个对象大小为：
 * 16 + 24 + 24 = 72
 */
class ThreeObject {

    // 仅用来声明对象
    OneObject[] oneObjects = new OneObject[2];

    // 真正分配内存
    public ThreeObject() {
        this.oneObjects[0] = new OneObject();
        this.oneObjects[1] = new OneObject();
    }
}


/**
 * FourObject: 8(_mark) + 4(oop指针)  + 4(数组引用) = 16
 * new int[10]数组: 8(_mark) + 4(oop指针) + 4(数组长度占4个字节) + 4 * 10(Integer大小) = 56
 * 因此整个对象大小为：
 * 16 + 56  = 72
 */
class FourObject {

    // 仅用来声明对象
    int[] oneObjects = new int[10];
}