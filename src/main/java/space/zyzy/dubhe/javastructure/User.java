package space.zyzy.dubhe.javastructure;

public class User extends UserParent{

    private String name;

    public int age;

    static {
        System.out.println("User static");
    }

    /**
     * 默认无参构造方法
     */
    public User() {
        System.out.println("User Construct");
    }

    /**
     * 公有带参数构造方法
     */
    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
     * 私有构造方法
     */
    private User(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" + "name='" + name + '\'' + ", age=" + age + '}';
    }
}
