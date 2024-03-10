package hexlet.code.formatters;

import hexlet.code.Formatter;

import java.util.Map;
import java.util.List;

public class Stylish implements Formatter {
    public StringBuilder appendDifference(List<String> keyList, Map<String, Object> map1, Map<String, Object> map2) {
        var builder = new StringBuilder("{\n");

        for (var key : keyList) {
            Object value1 = map1.get(key);
            Object value2 = map2.get(key);

            if (value1 != null && value1.equals(value2)) {
                builder.append("    ").append(key).append(": ").append(value1).append("\n");
            } else {
                if (value1 != null) {
                    builder.append("  - ").append(key).append(": ").append(value1).append("\n");
                }
                if (value2 != null) {
                    builder.append("  + ").append(key).append(": ").append(value2).append("\n");
                }
            }
        }
        builder.append("}");
        return builder;
    }
}
