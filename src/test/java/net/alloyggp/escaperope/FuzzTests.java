package net.alloyggp.escaperope;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FuzzTests {
    private FuzzTests() {
        //not instantiable
    }

    public static List<String> getRandomStrings(Random random, List<Integer> charsToUseInString) {
        int numStrings;
        double roll = random.nextDouble();
        if (roll < 0.01) {
            numStrings = 0;
        } else if (roll < 0.25) {
            numStrings = random.nextInt(10);
        } else if (roll < 0.9) {
            numStrings = random.nextInt(50);
        } else {
            numStrings = random.nextInt(1000);
        }
        List<String> result = new ArrayList<String>(numStrings);
        for (int i = 0; i < numStrings; i++) {
            result.add(getRandomString(random, charsToUseInString));
        }
        return result;
    }

    private static String getRandomString(Random random, List<Integer> charsToUseInString) {
        if (charsToUseInString.isEmpty()) {
            throw new IllegalArgumentException();
        }
        double roll = random.nextDouble();
        int stringLength;
        if (roll < 0.1) {
            return null;
        } else if (roll < 0.2) {
            return "";
        } else if (roll < 0.3) {
            stringLength = 1;
        } else if (roll < 0.4) {
            stringLength = random.nextInt(5);
        } else if (roll < 0.75) {
            stringLength = random.nextInt(20);
        } else if (roll < 0.95) {
            stringLength = random.nextInt(100);
        } else {
            stringLength = random.nextInt(1000);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stringLength; i++) {
            int chosenIndex = random.nextInt(charsToUseInString.size());
            int codePoint = charsToUseInString.get(chosenIndex);
            sb.appendCodePoint(codePoint);
        }
        return sb.toString();
    }
}
