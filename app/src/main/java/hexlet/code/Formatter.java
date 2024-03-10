package hexlet.code;

import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public interface Formatter {
    StringBuilder appendDifference(
                          List<String> keyList,
                          Map<String, Object> map1,
                          Map<String, Object> map2);

    static String valueToString(Object value) {
        if (value == null) {
            return "null";
        } else if (value.getClass().isArray()) {
            return Arrays.deepToString((Object[]) value);
        } else {
            return value.toString();
        }
    }

    static StringBuilder applyFormatter(
                                        List<String> keyList,
                                        Map<String, Object> map1,
                                        Map<String, Object> map2,
                                        String formatName) throws Exception {
        return switch (formatName) {
            case "stylish" -> {
                var stylishFormatter = new Stylish();
                yield stylishFormatter.appendDifference(keyList, map1, map2);
            }
            case "plain" -> {
                var plainFormatter = new Plain();
                yield plainFormatter.appendDifference(keyList, map1, map2);
            }
            default -> throw new Exception("Unexpected value: " + formatName);
        };
    }
}
