package hexlet.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestParser {
    private final String filepath1 =
            "src/test/resources/file1.json";
    private final String filepath2 =
            "src/test/resources/file1.json";
    private final String unexistedPath = "/home/something";

    Map<String, Object> expected = new LinkedHashMap<>();

    @BeforeEach
    public void beforeEach() {
        expected.put("setting1", "Some value");
        expected.put("setting2", 200);
        expected.put("setting3", true);
        expected.put("key1", "value1");
        expected.put("numbers1", Arrays.asList(1, 2, 3, 4));
        expected.put("numbers2", Arrays.asList(2, 3, 4, 5));
        expected.put("id", 45);
        expected.put("default", null);
        expected.put("checked", false);
        expected.put("numbers3", Arrays.asList(3, 4, 5));
        expected.put("chars1", Arrays.asList('a', 'b', 'c'));
        expected.put("chars2", Arrays.asList('d', 'e', 'f'));
    }


    @Test
    public void testJsonToMap() throws IOException {
        var actual = Parser.jsonToMap(filepath1);

        assertEquals(expected.keySet(), actual.keySet());
    }

    @Test
    public void testYamlToMap() throws IOException {
        var actual = Parser.yamlToMap(filepath1);

        assertEquals(expected.keySet(), actual.keySet());
    }

    @Test
    public void testJsonToMapException() {
        assertThrows(IOException.class, () -> {
            Parser.jsonToMap(unexistedPath);
        }, "There is no file on provided path or it is not json.");
    }

    @Test
    public void testYamlToMapException() {
        assertThrows(IOException.class, () -> {
            Parser.yamlToMap(unexistedPath);
        }, "There is no file on provided path or it is not yaml.");
    }
}
