package xd.utils;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class c {
    private String x;

    public c(Path path) {
        this.x = reader(path);
    }


    public String valueOf(String field) {
        int fieldIndex = this.x.indexOf("\"" + field + "\":");
        if (fieldIndex != -1) {
            int valueStartIndex = this.x.indexOf("\"", fieldIndex + field.length() + 2) + 1;
            int valueEndIndex = this.x.indexOf("\"", valueStartIndex);
            if (valueEndIndex != -1)
                return this.x.substring(valueStartIndex, valueEndIndex);
        }
        return null;
    }

    private String reader(Path path) {
        StringBuilder data = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null)
                data.append(line);
        } catch (Exception ignored) {
        }
        return data.toString();
    }
}
