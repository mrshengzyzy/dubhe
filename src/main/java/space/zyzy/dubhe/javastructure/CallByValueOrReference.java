package space.zyzy.dubhe.javastructure;

/**
 * 按值传递与按引用传递
 */
public class CallByValueOrReference {

    private static User user = null;
    private static User stu = null;

    private static void swap(User x, User y) {
        User temp = x;
        x = y;
        y = temp;
    }

    /**
     * 执行结果：
     * 调用前user的值：User{name='user', age=26}
     * 调用前stu的值：User{name='stu', age=18}
     * 调用后user的值：User{name='user', age=26}
     * 调用后stu的值：User{name='stu', age=18}
     * <p>
     * 原因分析：
     * 尝试交换两个值，如果是按照值传递的话，两个对象应该完成交换
     * 而实际上swap方法的参数x和y被初始化为两个对象引用的拷贝，这个方法交换的是这两个拷贝的值而已
     * 在方法结束后x，y将被丢弃，而原来的变量user和stu仍然引用这个方法调用之前所引用的对象。
     */
    public static void main(String[] args) {
        user = new User("user", 26);
        stu = new User("stu", 18);
        System.out.println("调用前user的值：" + user.toString());
        System.out.println("调用前stu的值：" + stu.toString());
        swap(user, stu);
        System.out.println("调用后user的值：" + user.toString());
        System.out.println("调用后stu的值：" + stu.toString());
    }
}

/**
 * 按值传递
 * 当传递方法参数类型为基本数据类型（数字以及布尔值）时，一个方法是不可能修改一个基本数据类型的参数。
 */
class CallByValue {

    private static int x = 10;

    public static void updateValue(int value) {
        // 在这里改变入参value的值
        value = 5;
    }

    /**
     * 执行结果：
     * 调用前x的值：10
     * 调用后x的值：10
     * 原因分析：
     * 直接在方法中改变value的值并不影响其真实值,因为入参拿到的是一个拷贝
     */
    public static void main(String[] args) {
        System.out.println("调用前x的值：" + x);
        updateValue(x);
        System.out.println("调用后x的值：" + x);
    }
}

/**
 * 按引用传递
 * 当传递方法参数类型为引用数据类型时，一个方法将修改一个引用数据类型的参数所指向对象的值。
 */
class CallByReference {

    private static User user = null;

    // 这里通过引用修改了对象的值
    private static void updateUser(User student) {
        student.setName("LiSi");
        student.setAge(18);
    }

    /**
     * 执行结果：
     * 调用前user的值：User{name='ZhangSan', age=26}
     * 调用后user的值：User{name='LiSi', age=18}
     * 原因分析：
     * 很好理解，传递的是对象的引用，通过引用直接改了对象的值
     */
    public static void main(String[] args) {
        user = new User("ZhangSan", 26);
        System.out.println("调用前user的值：" + user.toString());
        updateUser(user);
        System.out.println("调用后user的值：" + user.toString());
    }
}