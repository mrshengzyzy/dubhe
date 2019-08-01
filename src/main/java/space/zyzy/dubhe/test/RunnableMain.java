package space.zyzy.dubhe.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class RunnableMain {

    public static void main(String[] args) throws Exception {

        // {0,1,2,3,4}
        List<Integer> personList = new ArrayList<Integer>() {{
            for (int i = 0; i < 5; i++) {
                add(i);
            }
        }};

        List<Future> futureList = new ArrayList<>();

        // create a service with 5 threads
        ExecutorService service = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 5; i++) {
            try {
                futureList.add(service.submit(() -> {

                    // what condition is ok as long as invoke method remove
                    personList.removeIf(each -> {
                        try {
                            TimeUnit.MILLISECONDS.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return each == 2;
                    });
                    System.out.println(Thread.currentThread().getName() + personList);

                }));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Future fs : futureList) {
            try {
                System.out.println(fs.get());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                service.shutdown();
            }
        }
    }
}
