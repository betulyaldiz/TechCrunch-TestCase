package helper;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

public class UrlHelper {
    private static String VALID_URL_REGEX = "^https?:\\/\\/(?:www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b(?:[-a-zA-Z0-9()@:%_\\+.~#?&\\/=]*)$";

    public static boolean isValidURL(String url) {
        return Pattern.compile(VALID_URL_REGEX).matcher(url).find();
    }

    public static int getRandomIndex(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
