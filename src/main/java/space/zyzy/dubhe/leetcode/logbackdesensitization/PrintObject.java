package space.zyzy.dubhe.leetcode.logbackdesensitization;

@Desensitization
public class PrintObject {

    @Desensitization
    private String name;

    public PrintObject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
