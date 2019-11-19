package space.zyzy.dubhe.misc;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DeepClone {

    public static void main(String[] args) throws Exception {

        // 我们在一个list中添加了两个对象
        List<Deep> list = new ArrayList<Deep>() {{

            DeepObject object1 = new DeepObject("object1");
            Deep deep1 = new Deep("deep1", object1);
            add(deep1);

            DeepObject object2 = new DeepObject("object2");
            Deep deep2 = new Deep("deep2", object2);
            add(deep2);

            DeepObject object3 = new DeepObject("object3");
            Deep deep3 = new Deep("deep3", object3);
            add(deep3);

        }};

        // 起5个线程并发的修改list,肯定抛出 ConcurrentModificationException
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            try {
                service.execute(() -> {

                    // 注意这里我们重新深拷贝一份List,再次并发修改就不会引发异常
                    List<Deep> list1 = JSONObject.parseArray(JSONObject.toJSONString(list), Deep.class);
                    list1.removeIf(each -> {
                        try {
                            TimeUnit.MILLISECONDS.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return each.getName().equalsIgnoreCase("deep1");
                    });
                    System.out.println("============================================");
                    System.out.println(list);
                    System.out.println(list1);
                    System.out.println("============================================");
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 2秒后关闭服务
        TimeUnit.SECONDS.sleep(2);
        service.shutdown();


    }

    /**
     * 序列化方式深拷贝对象
     * 对象必须实现序列化接口
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        return (List<T>) in.readObject();
    }

    private static class DeepObject implements Serializable {

        public DeepObject() {
        }

        public DeepObject(String name) {
            this.name = name;
        }

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "DeepObject{" + "name='" + name + '\'' + '}';
        }
    }

    private static class Deep implements Serializable {

        public Deep() {
        }

        public Deep(String name, DeepObject deepObject) {
            this.name = name;
            this.deepObject = deepObject;
        }

        private String name;

        private DeepObject deepObject;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public DeepObject getDeepObject() {
            return deepObject;
        }

        public void setDeepObject(DeepObject deepObject) {
            this.deepObject = deepObject;
        }

        @Override
        public String toString() {
            return "Deep{" + "name='" + name + '\'' + ", deepObject=" + deepObject + '}';
        }
    }

}

