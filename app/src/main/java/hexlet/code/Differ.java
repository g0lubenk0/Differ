package hexlet.code;


import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashSet;
import java.util.Collections;

public class Differ {
    public static void generate(String filepath1, String filepath2, String formatName) throws Exception {
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

            var difference = getDifference(file1, file2, formatName);
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

    public static String getDifference(
            Map<String, Object> map1,
            Map<String, Object> map2,
            String formatName
    ) throws Exception {
        var keyList = getAllKeys(map1, map2);
        var builder = Formatter.applyFormatter(keyList, map1, map2, formatName);
        return builder.toString();
    }



    public static String getFileExtension(String filepath) {
        Path path = Path.of(filepath);
        String fileName = path.getFileName().toString();

        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
