package space.zyzy.dubhe.test;

public class Outer {

    private class Inner {

        private int y = 100;

        public int innerAdd() {
            return x + y;
        }
    }

    private int x = 100;
}