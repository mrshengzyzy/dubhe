package space.zyzy.dubhe.leetcode;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 敏感词过滤
 * DFA(Deterministic Finite Automaton 确定有限自动机)算法
 * 其特征为：有一个有限状态集合和一些从一个状态通向另一个状态的边,每条边上标记有一个符号,其中一个状态是初态,某些状态是终态。
 * 不同于 不确定的有限自动机,DFA中不会有从同一状态出发的两条边标志有相同的符号。
 * 简单点说：通过event和当前的state得到下一个state,即event + state = next_state。
 * 理解为系统中有多个节点,通过传递进入的event,来确定走哪个路由至另一个节点,而节点是有限的。
 */
public class TabooWord {

    /**
     * 敏感词集合
     * HashMap在理想情况下可以以O(1)的时间复杂度进行查询。
     * Map结构如下：
     * {
     * "傻": {
     * "子": {"E":"E"},
     * "大": {
     * "个": {"E":"E"}
     * }
     * },
     * "疯": {
     * "子": {"E":"E"}
     * }
     * }
     * 上面的Map构建了["傻子","傻大个","疯子"]三个敏感词
     */
    private static Map<Character, Object> tabooWordMap;

    /**
     * 一个敏感词结束标志
     * 当读到一个词的value是这个Map{"E":"E"}时表示敏感字已结束
     */
    private static final char E = 'E';
    private static final Map E_MAP = new HashMap<Character, Character>() {
        {
            put(E, E);
        }
    };

    /**
     * 初始化敏感词库
     */
    @SuppressWarnings("unchecked")
    private static synchronized void init(Set<String> tabooWordSet) {

        // 初始化HashSet
        tabooWordMap = new HashMap<>(tabooWordSet.size());

        // 遍历当前敏感词添加到Map中
        tabooWordSet.forEach(word -> {

            // 每个敏感词的处理都是从根Map开始的
            Map<Character, Object> current = tabooWordMap;

            // 挨个字符处理
            for (int i = 0, len = word.length(); i < len; i++) {

                // 当前待处理字符c
                char key = word.charAt(i);

                // 检查c开头的Entity是否已存在
                Map wordMap = (Map) current.get(key);

                // 如果已经存在,那么从这个节点开始匹配下一个字符
                if (wordMap != null) {
                    current = wordMap;
                    continue;
                }

                // 如果不存在的话添加到当前map中去
                if (i != len - 1) {

                    // 新增key值,value的值由下一个字符决定,因此这里new一个HashMap
                    current.put(key, new HashMap<>());

                    // 将current指向刚添加的节点,然后读取下一个字符
                    current = (Map) current.get(key);
                } else {
                    // 到敏感词结尾
                    current.put(key, E_MAP);
                }
            }
        });
    }

    /**
     * 判断一段文本是否包含敏感字符
     */
    @SuppressWarnings("unchecked")
    private static boolean contains(String text) {

        Map<Character, Object> current = tabooWordMap;

        // 一个字符一个字符比较
        for (int i = 0, len = text.length(); i < len; i++) {

            // 待比较的字符
            char c = text.charAt(i);

            // 检查C是否已存在
            Map wordMap = (Map) current.get(c);

            // 不存在说明不存在C
            if (wordMap == null) {

                // 到达字符串结尾仍没有匹配那就是没有敏感词了
                if (i == len - 1) {
                    return false;
                }

                // 继续匹配下一个字符,敏感词库复原
                current = tabooWordMap;
                continue;
            }

            // 否则就是查到了C,看C是不是结尾

            // 是结尾,结束匹配
            if (wordMap.equals(E_MAP)) {
                return true;
            }

            // 不是结尾继续查询下一个字符
            current = wordMap;
        }

        return false;
    }

    public static void main(String[] args) {

        Set<String> tabooWordSet = new HashSet<String>() {{
            add("傻子");
            add("傻大个子");
            add("疯狗");
        }};
        init(tabooWordSet);
        System.out.println(JSONObject.toJSON(tabooWordMap));

        Set<String> textSet = new HashSet<String>() {{
            add("一个小傻子"); // true
            add("两个大个子"); // false
            add("我是中国人"); // false
            add("疯牛病不是疯狗"); // true
            add("傻孩子"); // false
            add("你是不是傻"); // false
        }};
        textSet.forEach(text -> System.out.println(text + ":" + contains(text)));
    }
}
