package space.zyzy.dubhe.javastructure;

public class UserParent {

    private int nameParent;

    public int ageParent;

    static {
        System.out.println("UserParent static");
    }

    public UserParent() {
        System.out.println("UserParent Construct");
    }
}