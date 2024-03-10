package hexlet.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDiffer {
    private final String jsonFilepath1 =
            "/home/g0lubenk0/Desktop/projects/java-project-71/app/src/test/resources/file1.json";
    private final String jsonFilepath2 =
            "/home/g0lubenk0/Desktop/projects/java-project-71/app/src/test/resources/file2.json";

    private final String yamlFilepath1 =
            "/home/g0lubenk0/Desktop/projects/java-project-71/app/src/test/resources/file1.yaml";
    private final String yamlFilepath2 =
            "/home/g0lubenk0/Desktop/projects/java-project-71/app/src/test/resources/file2.yaml";

    private final String htmlFilepath1 =
            "/home/g0lubenk0/Desktop/projects/java-project-71/app/src/test/resources/file1.html";
    private final String htmlFilepath2 =
            "/home/g0lubenk0/Desktop/projects/java-project-71/app/src/test/resources/file2.html";

    private final Map<String, Object> map1 = new HashMap<>();
    private final Map<String, Object> map2 = new HashMap<>();

    private final String expectedDiff = """
                {
                  - a: 1
                  - b: 2
                  + b: 3
                    c: something
                  - d: something
                  + d: somethingsomething
                  + e: 10
                }""";
    @BeforeEach
    public void beforeEach() {
        map1.put("a", 1);
        map1.put("b", 2);
        map1.put("c", "something");
        map1.put("d", "something");

        map2.put("b", 3);
        map2.put("c", "something");
        map2.put("d", "somethingsomething");
        map2.put("e", 10);
    }

    @Test
    public void testGetAllKeys() {
        var actual = Differ.getAllKeys(map1, map2);
        var expected = new ArrayList<>(List.of("a", "b", "c", "d", "e"));

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
