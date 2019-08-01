package space.zyzy.dubhe.leetcode.topk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 哈希表通常用来进行第一步的数据过滤
 * 举例来说：
 * 搜索引擎会通过日志文件把用户每次检索使用的所有检索串都记录下来,每个查询串的长度为1-255字节
 * 假设现有1千万个记录,这些查询串的重复度比较高,如果除去重复后,不超过3百万个
 * 请统计最热门的10个查询串,要求使用的内存不能超过1GB
 * ============================================================================
 * 1千万数据显然无法直接装进内存, (1KW * 255) / 1000KB / 1000M / 1000G = 2.55G
 * 但是300万数据的话大小之后 2.55/3 < 1G,可以全部装入内存
 */
class HashTableTopK {

    /**
     * 构造哈希表
     */
    static Map<Integer, Integer> map(int[] data) {
        long start = System.currentTimeMillis();
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer datum : data) {
            map.merge(datum, 1, (a, b) -> a + b);
        }
        System.out.println("哈希表构造用时" + (System.currentTimeMillis() - start) + "毫秒");
        return map;
    }

    /**
     * 直接排序寻找TopK
     */
    static void solveMap(Map<Integer, Integer> map) {

        long start = System.currentTimeMillis();

        // 按照Value排序
        List<Map.Entry<Integer, Integer>> entryList = sortMap(map);

        // 打印TopK结果
        StringBuilder sb = new StringBuilder("哈希表法结果[");
        for (int i = 0; i < 10; i++) {
            sb.append(entryList.get(i).getKey());
            if (i != 9) {
                sb.append(",");
            }
        }
        sb.append("]用时").append(System.currentTimeMillis() - start).append("毫秒");
        System.out.println(sb.toString());
    }

    private static List<Map.Entry<Integer, Integer>> sortMap(Map<Integer, Integer> map) {

        // 将所有的Entry读取到List中
        List<Map.Entry<Integer, Integer>> entries = new ArrayList<>(map.entrySet());

        // 按照value排序(降序)
        entries.sort((o1, o2) -> o2.getValue() - o1.getValue());

        return entries;
    }
}
