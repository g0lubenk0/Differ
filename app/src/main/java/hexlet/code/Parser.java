package hexlet.code;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;


public class Parser {
    public static Map<String, Object> jsonToMap(String path) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(path), new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            throw new IOException("There is no file on provided path or it is not json.");
        }
    }

    public static Map<String, Object> yamlToMap(String path) throws IOException {
        ObjectMapper mapper = new YAMLMapper();
        try {
            return mapper.readValue(new File(path), new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            throw new IOException("There is no file on provided path or it is not yaml.");
        }
    }
}
