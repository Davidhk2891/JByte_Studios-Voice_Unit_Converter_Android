package superunitconverter.nexmii.com.superunitconverter.util;

import java.security.SecureRandom;

/**
 * Singleton class for generating a random string. For now it will be used as an unique ID
 */
public class RandomStringGenerator {

    private static RandomStringGenerator instance;

    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";

    private static final String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
    private static final SecureRandom random = new SecureRandom();

    private RandomStringGenerator(){}

    public static synchronized RandomStringGenerator getInstance(){
        if (instance == null)
            instance = new RandomStringGenerator();
        return instance;
    }

    public String generateRandomString(int stringLength) {
        if (stringLength < 1) throw new IllegalArgumentException();

        StringBuilder sb = new StringBuilder(stringLength);
        for (int i = 0; i < stringLength; i++) {

            // 0-62 (exclusive), random returns 0-61
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

            // debug
            System.out.format("%d\t:\t%c%n", rndCharAt, rndChar);

            sb.append(rndChar);
        }
        return sb.toString();
    }

}
