package hexlet.code;


import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Differ {
    public static void generate(String filepath1, String filepath2) throws IOException {
        var extension1 = getFileExtension(filepath1);
        var extension2 = getFileExtension(filepath2);

        Map<String, Object> file1;
        Map<String, Object> file2;

        if (extension1.equals(extension2)) {
            switch (extension1) {
                case "json":
                    file1 = Parser.jsonToMap(filepath1);
                    file2 = Parser.jsonToMap(filepath2);
                    break;
                case "yaml":
                    file1 = Parser.yamlToMap(filepath1);
                    file2 = Parser.yamlToMap(filepath2);
                    break;
                default:
                    System.out.println("Unsupported file extension: " + extension1);
                    return;
            }

            var difference = getDifference(file1, file2);
            System.out.println(difference);
        } else {
            System.out.println("File extensions are different: " + extension1 + " and " + extension2);
        }
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

        for (var key : keyList) {
            appendDifference(builder, key, map1, map2);
        }

        builder.append("}");
        return builder.toString();
    }

    public static void appendDifference(
            StringBuilder builder,
            String key,
            Map<String, Object> map1,
            Map<String, Object> map2
    ) {
        if (map1.containsKey(key) && map2.containsKey(key)) {
            Object value1 = valueToString(map1.get(key));
            Object value2 = valueToString(map2.get(key));
            if (value1 == null && value2 == null) {
                return;
            } else if (value1 != null && value1.equals(value2)) {
                builder.append("    ").append(key).append(": ").append(value1).append("\n");
            } else {
                builder.append("  - ").append(key).append(": ").append(value1).append("\n");
                builder.append("  + ").append(key).append(": ").append(value2).append("\n");
            }
        } else if (map1.containsKey(key) && !map2.containsKey(key)) {
            builder.append("  - ").append(key).append(": ").append(valueToString(map1.get(key))).append("\n");
        } else {
            builder.append("  + ").append(key).append(": ").append(valueToString(map2.get(key))).append("\n");
        }
    }

    public static String valueToString(Object value) {
        if (value == null) {
            return "null";
        } else if (value.getClass().isArray()) {
            // Handle arrays
            return Arrays.deepToString((Object[]) value);
        } else {
            // For non-array types, use toString directly
            return value.toString();
        }
    }

    public static String getFileExtension(String filepath) {
        Path path = Path.of(filepath);
        String fileName = path.getFileName().toString();

        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
