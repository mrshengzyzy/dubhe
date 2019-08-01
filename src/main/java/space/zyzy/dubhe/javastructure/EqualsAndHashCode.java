package space.zyzy.dubhe.javastructure;

import java.util.HashMap;
import java.util.Map;

/**
 * equals 与 hashCode 两个方法的联系
 * 重写equals方法的原则：
 * 自反性。对于任何非null的引用值x，x.equals(x)应返回true。
 * 对称性。对于任何非null的引用值x与y，当且仅当：y.equals(x)返回true时，x.equals(y)才返回true。
 * 传递性。对于任何非null的引用值x、y与z，如果y.equals(x)返回true，y.equals(z)返回true，那么x.equals(z)也应返回true。
 * 一致性。对于任何非null的引用值x与y，假设对象上equals比较中的信息没有被修改，则多次调用x.equals(y)始终返回true或者始终返回false。
 * 对于任何非空引用值x，x.equal(null)应返回false。
 * <p>
 * 与hashCode的关系：
 * （1）在java应用程序执行期间，如果在equals方法比较中所用的信息没有被修改，那么在同一个对象上多次调用hashCode方法时必须一致地返回相同的整数。
 * 如果多次执行同一个应用时，不要求该整数必须相同。
 * （2）如果两个对象通过调用equals方法是相等的，那么这两个对象调用hashCode方法必须返回相同的整数。
 * （3）如果两个对象通过调用equals方法是不相等的，不要求这两个对象调用hashCode方法必须返回不同的整数。
 * 但是程序员应该意识到对不同的对象产生不同的hash值可以提供哈希表的性能。
 */
public class EqualsAndHashCode {
}

/**
 * 只覆盖equals不重写hashCode的例子
 */
class OnlyEquals {

    /**
     * 执行结果：
     * 重写key的hashCode方法：
     * s1.equals(s2):true
     * map1.get(s1):类Value的值－－>2
     * map1.get(s2):类Value的值－－>2
     * k1.equals(k2):true
     * map2.get(k1):类Value的值－－>2
     * map2.get(k2):类Value的值－－>2
     * =============================
     * 不重写key的hashCode方法：
     * s1.equals(s2):true
     * map1.get(s1):类Value的值－－>2
     * map1.get(s2):类Value的值－－>2
     * k1.equals(k2):true
     * map2.get(k1):类Value的值－－>2
     * map2.get(k2):null
     */
    public static void main(String[] args) {

        Map<String, Value> map1 = new HashMap<>();

        // s1与s2是不同的引用,但他们指向同一个常量池中的字符串key
        String s1 = new String("key");
        String s2 = new String("key");
        Value value = new Value(2);

        // 首先map计算s1的hashCode,然后按照hashCode存储
        map1.put(s1, value);

        // String类重写了equals,结果为true
        System.out.println("s1.equals(s2):" + s1.equals(s2));

        // 显然这里能得到value2
        System.out.println("map1.get(s1):" + map1.get(s1));

        // String类同时重写了hashCode,满足s1.equals(s2)时 s1.hashCode = s2.hashCode
        // 因此get(s2)也可以得到值
        System.out.println("map1.get(s2):" + map1.get(s2));

        /*
         * 这里我们应用自己定义的key
         * 注意我们的key只重写了equals但是并没有重写hashCode
         */
        Map<Key, Value> map2 = new HashMap<>();
        Key k1 = new Key("A");
        Key k2 = new Key("A");
        map2.put(k1, value);
        System.out.println("k1.equals(k2):" + s1.equals(s2));
        System.out.println("map2.get(k1):" + map2.get(k1));

        // 有 k1.hashCode != k2.hashCode 因此get(k2)的值就是null
        System.out.println("map2.get(k2):" + map2.get(k2));
    }

    static class Key {
        private String k;

        public Key(String key) {
            this.k = key;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Key) {
                Key key = (Key) obj;
                return k.equals(key.k);
            }
            return false;
        }

        /**
         * 重写了hashCode之后
         * 才能保证equals相等的对象放置到Map中是同一个位置
         */
//        @Override
//        public int hashCode() {
//            return k.hashCode();
//        }
    }

    static class Value {
        private int v;

        public Value(int v) {
            this.v = v;
        }

        @Override
        public String toString() {
            return "类Value的值－－>" + v;
        }
    }
}

class GetClassOrInstanceOf {

    /**
     * 执行结果：
     * true
     * true
     * false
     */
    public static void main(String[] args) {
        Employee e1 = new Employee("chenssy", 23);
        Employee e2 = new Employee("chenssy", 24);
        Person p1 = new Person("chenssy");
        System.out.println(p1.equals(e1));
        System.out.println(p1.equals(e2));
        System.out.println(e1.equals(e2));
    }

    static class Person {

        protected String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Person(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object object) {
            // 首先判断入参是否为Person的子类
            if (object instanceof Person) {

                // 如果是子类的话强转为Person类
                Person p = (Person) object;

                // 两个对象中间有一个为null就认为是不相等的对象
                if (p.getName() == null || name == null) {
                    return false;
                } else {
                    return name.equalsIgnoreCase(p.getName());
                }
            }
            return false;
        }
    }

    /**
     * Employee 继承自 Person 但多了一个 id 属性
     */
    static class Employee extends Person {

        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Employee(String name, int id) {
            super(name);
            this.id = id;
        }

        @Override
        public boolean equals(Object object) {
            if (object instanceof Employee) {
                Employee e = (Employee) object;
                // 调用父类的equals并且同时比较id
                return super.equals(object) && e.getId() == id;
            }
            return false;
        }
    }

}