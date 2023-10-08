package com.example.libraryapi.utils;

import java.util.Random;

public class ISBNGenerator {
    public static String generateISBN() {
        String prefix = "978";
        String randomPart = generateRandomNumber(9);
        String isbnWithoutCheckDigit = prefix + randomPart;
        int checkDigit = calculateCheckDigit(isbnWithoutCheckDigit);
        return prefix + "-" + randomPart + "-" + checkDigit;
    }

    private static String generateRandomNumber(int length) {
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            result.append(random.nextInt(10));
        }
        return result.toString();
    }

    private static int calculateCheckDigit(String isbnWithoutCheckDigit) {
        int sum = 0;
        for (int i = 0; i < isbnWithoutCheckDigit.length(); i++) {
            int digit = Integer.parseInt(String.valueOf(isbnWithoutCheckDigit.charAt(i)));
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        int checkDigit = (10 - (sum % 10)) % 10;
        return checkDigit;
    }
}
