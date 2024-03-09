package hexlet.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDiffer {
    private final String filepath1 =
            "/home/g0lubenk0/Desktop/projects/java-project-71/app/src/test/resources/file1.json";
    private final String filepath2 =
            "/home/g0lubenk0/Desktop/projects/java-project-71/app/src/test/resources/file2.json";

    private final Map<String, Object> map1 = new HashMap<>();
    private final Map<String, Object> map2 = new HashMap<>();
    @BeforeEach
    public void beforeEach() {
        map1.put("a", 1);
        map1.put("b", 2);
        map1.put("c", "something");
        map1.put("d", "something");

        map2.put("b", 3);
        map2.put("c", "something");
        map2.put("d", "somethingsomething");
    }

    @Test
    public void testGetAllKeys() {
        var actual = Differ.getAllKeys(map1, map2);
        var expected = new ArrayList<>(List.of("a", "b", "c", "d"));

        assertEquals(expected, actual);
    }

    @Test
    public void testGetDifference() {
        var actual = Differ.getDifference(map1, map2);
        var expected = """
                {
                  - a: 1
                  - b: 2
                  + b: 3
                    c: something
                  - d: something
                  + d: somethingsomething
                }""";
        assertEquals(expected, actual);
    }
}
