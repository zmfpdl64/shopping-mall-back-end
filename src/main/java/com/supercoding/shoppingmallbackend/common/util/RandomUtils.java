package com.supercoding.shoppingmallbackend.common.util;

import java.security.SecureRandom;
import java.util.Random;

public class RandomUtils {
    private static final SecureRandom random = new SecureRandom();
    private static final String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
    private static final String digits = "0123456789";
    public static String generateAuthCode() {
        StringBuilder numStr = new StringBuilder();

        Random rand = new Random();
        for(int i=0; i<6; i++) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr.append(ran);
        }
        return numStr.toString();
    }

    public static String generateRandomPassword() {

        String allowedCharacters = upperCaseLetters + lowerCaseLetters + digits;


        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(allowedCharacters.length());
            password.append(allowedCharacters.charAt(index));
        }

        return password.toString();
    }

    public static String generateRandomPhone() {
        return generateNum(3) + "-" + generateNum(4) + "-" + generateNum(4);
    }

    private static String generateNum(int size) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            int index = random.nextInt(digits.length());
            sb.append(digits.charAt(index));
        }
        return sb.toString();
    }
}
