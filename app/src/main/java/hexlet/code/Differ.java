package hexlet.code;


import java.io.IOException;
import java.util.*;

public class Differ {
    public static void generate(String filepath1, String filepath2) throws IOException {
        var file1 = Parser.jsonToMap(filepath1);
        var file2 = Parser.jsonToMap(filepath2);

        var difference = getDifference(file1, file2);
        System.out.println(difference);
    }

    public static List<String> getAllKeys(Map<String, Object> map1, Map<String, Object> map2) {
        var keySet1 = map1.keySet();
        var keySet2 = map2.keySet();
        var allKeys = new ArrayList<>(keySet1);
        allKeys.addAll(keySet2);
        var keyList = new ArrayList<>(new HashSet<>(allKeys));
        Collections.sort(keyList);
        return  keyList;
    }

    public static String getDifference(Map<String, Object> map1, Map<String, Object> map2) {
        var keyList = getAllKeys(map1, map2);

        var builder = new StringBuilder("{\n");

        for (var key: keyList) {
            if (map1.containsKey(key) && map2.containsKey(key)) {
                if (map1.get(key).equals(map2.get(key))) {
                    builder.append("    ").append(key).append(": ").append(map1.get(key)).append("\n");
                } else {
                    builder.append("  - ").append(key).append(": ").append(map1.get(key)).append("\n");
                    builder.append("  + ").append(key).append(": ").append(map2.get(key)).append("\n");
                }
            } else if (map1.containsKey(key) && !map2.containsKey(key)) {
                builder.append("  - ").append(key).append(": ").append(map1.get(key)).append("\n");
            } else {
                builder.append("  + ").append(key).append(": ").append(map2.get(key)).append("\n");
            }
        }
        builder.append("}");

        return builder.toString();
    }
}
