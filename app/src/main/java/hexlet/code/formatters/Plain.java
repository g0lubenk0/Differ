package hexlet.code.formatters;

import hexlet.code.Formatter;

import java.util.List;
import java.util.Map;

public class Plain implements Formatter {
    public StringBuilder appendDifference(
            List<String> keyList,
            Map<String, Object> map1,
            Map<String, Object> map2) {
        var builder = new StringBuilder();
        for (var key: keyList) {
            if (map1.containsKey(key) && map2.containsKey(key)) {
                Object value1 = Formatter.valueToString(map1.get(key));
                Object value2 = Formatter.valueToString(map2.get(key));
                if (value1 == null && value2 == null) {
                    break;
                } else {
                    builder
                            .append("Property '")
                            .append(key)
                            .append("' was updated. From ")
                            .append(value1)
                            .append(" to ")
                            .append(value2)
                            .append("\n");
                }
            } else if (map1.containsKey(key) && !map2.containsKey(key)) {
                builder
                        .append("Property '")
                        .append(key)
                        .append("' was removed.")
                        .append("\n");
            } else {
                builder
                        .append("Property '")
                        .append(key)
                        .append("' was added wtih value: ")
                        .append(Formatter.valueToString(map2.get(key)))
                        .append("\n");
            }
        }
        return builder;
    }
}
