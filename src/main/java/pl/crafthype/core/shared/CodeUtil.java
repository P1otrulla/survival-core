package pl.crafthype.core.shared;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public final class CodeUtil {

    private static final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Random RANDOM = ThreadLocalRandom.current();

    private CodeUtil() {
    }

    public static String generateCode(int length) {
        StringBuilder codeBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(ALLOWED_CHARACTERS.length());
            char randomChar = ALLOWED_CHARACTERS.charAt(randomIndex);

            codeBuilder.append(randomChar);
        }

        return codeBuilder.toString();
    }
}
