package space.zyzy.dubhe.test;

import java.util.ArrayList;
import java.util.List;

public class MainTest {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {

            try {

                list.add(i);

                if (i == 3) {
                    throw new RuntimeException("-----------");
                }

            } catch (Exception e) {

            }

        }

        System.out.println(list);

    }
}
