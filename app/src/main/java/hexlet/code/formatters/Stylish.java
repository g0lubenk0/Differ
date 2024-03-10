package hexlet.code.formatters;

import hexlet.code.Formatter;

import java.util.Map;
import java.util.List;

public class Stylish implements Formatter {
    public StringBuilder appendDifference(List<String> keyList, Map<String, Object> map1, Map<String, Object> map2) {
        var builder = new StringBuilder("{\n");

        for (var key: keyList) {
            if (map1.containsKey(key) && map2.containsKey(key)) {
                Object value1 = Formatter.valueToString(map1.get(key));
                Object value2 = Formatter.valueToString(map2.get(key));
                if (value1 == null && value2 == null) {
                    break;
                } else if (value1 != null && value1.equals(value2)) {
                    builder.append("    ").append(key).append(": ").append(value1).append("\n");
                } else {
                    builder.append("  - ").append(key).append(": ").append(value1).append("\n");
                    builder.append("  + ").append(key).append(": ").append(value2).append("\n");
                }
            } else if (map1.containsKey(key) && !map2.containsKey(key)) {
                builder.append("  - ").append(key).append(": ").append(Formatter.valueToString(map1.get(key)))
                        .append("\n");
            } else {
                builder.append("  + ").append(key).append(": ").append(Formatter.valueToString(map2.get(key)))
                        .append("\n");
            }
        }
        builder.append("}");
        return builder;
    }
}
