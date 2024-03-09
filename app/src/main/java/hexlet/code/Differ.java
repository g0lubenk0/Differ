package hexlet.code;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Differ {
    public static void generate(String filepath1, String filepath2) throws IOException {
        var file1 = Parser.jsonToMap(filepath1);
        var file2 = Parser.jsonToMap(filepath2);

        var keySet1 = file1.keySet();
        var keySet2 = file2.keySet();
        var allKeys = new ArrayList<>(keySet1);
        allKeys.addAll(keySet2);
        var keyList = new ArrayList<>(new HashSet<>(allKeys));
        Collections.sort(keyList);

        var builder = new StringBuilder("{\n");

        for (var key: keyList) {
            if (file1.containsKey(key) && file2.containsKey(key)) {
                if (file1.get(key).equals(file2.get(key))) {
                    builder.append("    ").append(key).append(": ").append(file1.get(key)).append("\n");
                } else {
                    builder.append("  - ").append(key).append(": ").append(file1.get(key)).append("\n");
                    builder.append("  + ").append(key).append(": ").append(file2.get(key)).append("\n");
                }
            } else if (file1.containsKey(key) && !file2.containsKey(key)) {
                builder.append("  - ").append(key).append(": ").append(file1.get(key)).append("\n");
            } else if (!file1.containsKey(key) && file2.containsKey(key)) {
                builder.append("  + ").append(key).append(": ").append(file2.get(key)).append("\n");
            }
        }
        builder.append("}");
        System.out.println(builder);
    }
}
