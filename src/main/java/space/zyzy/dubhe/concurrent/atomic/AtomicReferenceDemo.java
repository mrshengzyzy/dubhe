package space.zyzy.dubhe.concurrent.atomic;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceDemo {

    public static void main(String[] args) {

        // 初始化一个head节点
        final AtomicReference<Node> tail = new AtomicReference<>(new Node("head"));
        System.out.println(tail.get());

        // 生成一个first节点
        Node first = new Node("first");

        // 调用getAndSet方法后,将返回旧值head,然后设置为新值first
        Node old = tail.getAndSet(first);

        // 此时的旧值就是head
        System.out.println(old);

        // 此时节点中保存的是新值first
        System.out.println(tail.get());
    }

    /**
     * 测试的Node节点
     */
    private static class Node {
        private String name;

        Node(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Node[name=" + name + "]";
        }
    }
}
