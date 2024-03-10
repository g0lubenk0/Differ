package hexlet.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDiffer {
    private final String jsonFilepath1 =
            "src/test/resources/file1.json";
    private final String jsonFilepath2 =
            "src/test/resources/file2.json";

    private final String yamlFilepath1 =
            "src/test/resources/file1.yaml";
    private final String yamlFilepath2 =
            "src/test/resources/file2.yaml";

    private final String htmlFilepath1 =
            "src/test/resources/file1.html";
    private final String htmlFilepath2 =
            "src/test/resources/file2.html";

    private final Map<String, Object> map1 = new HashMap<>();
    private final Map<String, Object> map2 = new HashMap<>();

    private final String expectedDiff = """
            {
                chars1: [a, b, c]
              - chars2: [d, e, f]
              + chars2: false
              - checked: false
              + checked: true
              - default: null
              + default: [value1, value2]
              - id: 45
              + id: null
              - key1: value1
              + key2: value2
                numbers1: [1, 2, 3, 4]
              - numbers2: [2, 3, 4, 5]
              + numbers2: [22, 33, 44, 55]
              - numbers3: [3, 4, 5]
              + numbers4: [4, 5, 6]
              + obj1: {nestedKey=value, isNested=true}
              - setting1: Some value
              + setting1: Another value
              - setting2: 200
              + setting2: 300
              - setting3: true
              + setting3: none
            }""";
    @BeforeEach
    public void beforeEach() {
        map1.put("setting1", "Some value");
        map1.put("setting2", 200);
        map1.put("setting3", true);
        map1.put("key1", "value1");
        map1.put("numbers1", Arrays.asList(1, 2, 3, 4));
        map1.put("numbers2", Arrays.asList(2, 3, 4, 5));
        map1.put("id", 45);
        map1.put("default", null);
        map1.put("checked", false);
        map1.put("numbers3", Arrays.asList(3, 4, 5));
        map1.put("chars1", Arrays.asList("a", "b", "c"));
        map1.put("chars2", Arrays.asList("d", "e", "f"));

        map2.put("setting1", "Another value");
        map2.put("setting2", 300);
        map2.put("setting3", "none");
        map2.put("key2", "value2");
        map2.put("numbers1", new Integer[]{1, 2, 3, 4});
        map2.put("numbers2", new Integer[]{22, 33, 44, 55});
        map2.put("id", null);
        map2.put("default", new String[]{"value1", "value2"});
        map2.put("checked", true);
        map2.put("numbers4", new Integer[]{4, 5, 6});
        map2.put("chars1", new String[]{"a", "b", "c"});
        map2.put("chars2", false);

        // Nested object
        Map<String, Object> obj1 = new HashMap<>();
        obj1.put("nestedKey", "value");
        obj1.put("isNested", true);
        map2.put("obj1", obj1);
    }

    @Test
    public void testGetAllKeys() {
        var actual = Differ.getAllKeys(map1, map2);
        List<String> keys = new ArrayList<>();
        keys.add("chars1");
        keys.add("chars2");
        keys.add("checked");
        keys.add("default");
        keys.add("id");
        keys.add("key1");
        keys.add("key2");
        keys.add("numbers1");
        keys.add("numbers2");
        keys.add("numbers3");
        keys.add("numbers4");
        keys.add("obj1");
        keys.add("setting1");
        keys.add("setting2");
        keys.add("setting3");
        var expected = new ArrayList<>(keys);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetDifference() {
        var actual = Differ.getDifference(map1, map2);
        assertEquals(expectedDiff, actual);
    }

    @Test
    public void testGetFileExtension() {
        var actual = Differ.getFileExtension(yamlFilepath1);
        var expected = "yaml";
        assertEquals(expected, actual);

        actual = Differ.getFileExtension(jsonFilepath1);
        expected = "json";
        assertEquals(expected, actual);
    }

    @Test
    public void testGenerateJSON() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Differ.generate(jsonFilepath1, jsonFilepath2);
        String printedMessage = outputStream.toString().trim();

        assertEquals(expectedDiff, printedMessage);
    }

    @Test
    public void testGenerateYAML() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Differ.generate(yamlFilepath1, yamlFilepath2);
        String printedMessage = outputStream.toString().trim();

        assertEquals(expectedDiff, printedMessage);
    }

    @Test
    public void testGenerateUnsupportedExtensions() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Differ.generate(htmlFilepath1, htmlFilepath2);
        String printedMessage = outputStream.toString().trim();

        assertEquals("Unsupported file extension: html", printedMessage);
    }

    @Test
    public void testGenerateDifferentExtensions() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        Differ.generate(jsonFilepath1, yamlFilepath1);
        String printedMessage = outputStream.toString().trim();

        assertEquals("File extensions are different: json and yaml", printedMessage);
    }
}
