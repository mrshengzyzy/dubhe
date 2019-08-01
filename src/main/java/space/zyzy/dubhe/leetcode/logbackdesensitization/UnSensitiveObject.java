package space.zyzy.dubhe.leetcode.logbackdesensitization;

@Desensitization
public class UnSensitiveObject {

    private String name;

    public UnSensitiveObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
