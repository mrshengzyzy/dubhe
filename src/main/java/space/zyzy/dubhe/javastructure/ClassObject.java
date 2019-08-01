package space.zyzy.dubhe.javastructure;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * 类加载的过程分为：
 * 加载(Loading) --- 验证(Verification) --- 准备(Preparation) --- 解析(Resolution) --- 初始化(Initialization),其中验证、准备、解析三个阶段又叫连接(Linking)
 * 加载：类加载过程的一个阶段：通过一个类的完全限定查找此类字节码文件，并利用字节码文件创建一个Class对象
 * 链接：验证字节码的安全性和完整性，准备阶段正式为静态域分配存储空间，注意此时只是分配静态成员变量的存储空间，不包含实例成员变量，如果必要的话，解析这个类创建的对其他类的所有引用。
 * 初始化：类加载最后阶段，若该类具有超类，则对其进行初始化，执行静态初始化器和静态初始化成员变量(只执行静态化的东西)
 * 关于类加载的初始化阶段，在虚拟机规范严格规定了有且只有5种场景必须对类进行初始化：
 * 1 使用new关键字实例化对象时、读取或者设置一个类的静态字段(不包含编译期常量)以及调用静态方法的时候，必须触发类加载的初始化过程(类加载过程最终阶段)。
 * 2 使用反射包(java.lang.reflect)的方法对类进行反射调用时，如果类还没有被初始化，则需先进行初始化，这点对反射很重要。
 * 3 当初始化一个类的时候，如果其父类还没进行初始化则需先触发其父类的初始化。
 * 4 当Java虚拟机启动时，用户需要指定一个要执行的主类(包含main方法的类)，虚拟机会先初始化这个主类
 * 5 当使用JDK 1.7 的动态语言支持时，如果一个java.lang.invoke.MethodHandle 实例最后解析结果为REF_getStatic、REF_putStatic、REF_invokeStatic的方法句柄，并且这个方法句柄对应类没有初始化时，必须触发其初始化
 * (这点看不懂就算了，这是1.7的新增的动态语言支持，其关键特征是它的类型检查的主体过程是在运行期而不是编译期进行的，这是一个比较大点的话题，这里暂且打住)
 * 初始化是类加载的最后一个阶段，也就是说完成这个阶段后类也就加载到内存中(但Class对象本身在加载阶段已被创建)，此时可以对类进行各种必要的操作了（如new对象，调用静态成员等）。
 * <p>
 * 因为我们创建的是类对象,所以涉及到的静态相关都会被初始化（当然了如果本身是常量那么也无需初始化因为是确定的）,而构造函数是属于实例的所以不会执行。
 */
public class ClassObject {

    public static void main(String[] args) throws Exception {

        /*
         * 通过class字面常量加载类不会导致类的初始化
         * static代码块不执行,构造函数不执行
         */
//        Class clazz = User.class;

        /*
         * 通过forName的方式加载类将触发类的初始化,且由于Demo本身有父类，因此父类首先被初始化
         * static代码块执行,构造函数 !!!不执行!!!(没错,就是不执行)
         */
        Class clazz = Class.forName("space.zyzy.dubhe.javastructure.User");

        /*
         * 不返回父类声明的任何字段
         * [private java.lang.String space.zyzy.dubhe.javastructure.User.name, public int space.zyzy.dubhe.javastructure.User.age]
         */
        Field[] fields = clazz.getDeclaredFields();
        System.out.println(Arrays.toString(fields));

        /*
         * 返回本类与父类的 public 声明的字段
         * [public int space.zyzy.dubhe.javastructure.User.age, public int space.zyzy.dubhe.javastructure.UserParent.ageParent]
         */
        Field[] fields0 = clazz.getFields();
        System.out.println(Arrays.toString(fields0));
    }
}

/**
 * 反射相关测试
 * --------------------------------------------
 * UserParent static
 * User static
 * UserParent Construct
 * User Construct
 * User{name='null', age=0}
 * --------------------------------------------
 * UserParent Construct
 * user1:User{name='张三', age=22}
 * --------------------------------------------
 * UserParent Construct
 * user2:User{name='李四', age=25}
 * --------------------------------------------
 * 构造函数[0]:private space.zyzy.dubhe.javastructure.User(java.lang.String)
 * 参数类型[0]:(java.lang.String)
 * <p>
 * 构造函数[1]:public space.zyzy.dubhe.javastructure.User(java.lang.String,int)
 * 参数类型[1]:(java.lang.String,int)
 * <p>
 * 构造函数[2]:public space.zyzy.dubhe.javastructure.User()
 * 参数类型[2]:()
 */
class ClassObject0 {
    public static void main(String[] args) throws Exception {

        // 获取Class对象
        Class<User> userClass = User.class;
        System.out.println("--------------------------------------------");
        /*
         * 调用public修饰的无参构造器
         */
        User user = userClass.newInstance();
        System.out.println(user);
        System.out.println("--------------------------------------------");

        // 获取带参数的public构造器（注意int.class与Integer.class不相同）
        Constructor cs1 = userClass.getConstructor(String.class, int.class);
        User user1 = (User) cs1.newInstance("张三", 22);
        System.out.println("user1:" + user1.toString());
        System.out.println("--------------------------------------------");

        // 获取带参数的private构造器
        Constructor cs2 = userClass.getDeclaredConstructor(String.class);

        // 由于是private必须设置可访问
        cs2.setAccessible(true);

        // 创建user对象
        User user2 = (User) cs2.newInstance("李四");
        user2.setAge(25);
        System.out.println("user2:" + user2.toString());
        System.out.println("--------------------------------------------");

        // 获取所有声明的构造包当然也包含private
        Constructor<?> cons[] = userClass.getDeclaredConstructors();
        for (int i = 0; i < cons.length; i++) {

            // 获取构造函数参数类型
            Class<?> clazz[] = cons[i].getParameterTypes();
            System.out.println("构造函数[" + i + "]:" + cons[i].toString());
            System.out.print("参数类型[" + i + "]:(");
            for (int j = 0; j < clazz.length; j++) {
                if (j == clazz.length - 1)
                    System.out.print(clazz[j].getName());
                else
                    System.out.print(clazz[j].getName() + ",");
            }
            System.out.println(")");
            System.out.println(" ");
        }
    }
}