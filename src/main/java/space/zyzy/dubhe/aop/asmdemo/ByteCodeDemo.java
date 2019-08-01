package space.zyzy.dubhe.aop.asmdemo;

/**
 * 1、Spring自身带了一个asm包(spring-core包中),因此无需额外引用包
 * 2、需要安装 ASM Bytecode Outline 插件
 * 3、右键 Show Bytecode outline
 */
public class ByteCodeDemo {

    private static final String name = "xiaoming";

    private int age;

    public ByteCodeDemo(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge() {
        this.age = age;
    }

    public static void main(String[] args) {
        ByteCodeDemo byteCodeDemo = new ByteCodeDemo(12);
        System.out.println("name:" + name + "age:" + byteCodeDemo.getAge());
    }
}
