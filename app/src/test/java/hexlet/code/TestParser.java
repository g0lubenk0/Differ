package hexlet.code;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestParser {
    private final String filepath1 =
            "/home/g0lubenk0/Desktop/projects/java-project-71/app/src/test/resources/file1.json";
    private final String filepath2 =
            "/home/g0lubenk0/Desktop/projects/java-project-71/app/src/test/resources/file2.json";
    private final String unexistedPath = "/home/something";
    @Test
    public void testJsonToMap() throws IOException {
        var actual = Parser.jsonToMap(filepath1);
        var expected = new HashMap<String, Object>();

        expected.put("a", 1);
        expected.put("b", 2);
        expected.put("c", "something");
        expected.put("d", "something");

        assertEquals(expected, actual);
    }

    @Test
    public void testJsonToMapException() {
        assertThrows(IOException.class, () -> {
            Parser.jsonToMap(unexistedPath);
        }, "There is no file on provided path or it is not json.");
    }

    @Test
    public void testParser() {

    }
}
