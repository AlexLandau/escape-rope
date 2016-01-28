package net.alloyggp.escaperope;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.alloyggp.escaperope.rope.ListRope;
import net.alloyggp.escaperope.rope.Rope;
import net.alloyggp.escaperope.rope.StringRope;

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
        List<String> result = new ArrayList<>(numStrings);
        for (int i = 0; i < numStrings; i++) {
            result.add(getRandomString(random, charsToUseInString));
        }
        return result;
    }

    public static String getRandomString(Random random, List<Integer> charsToUseInString) {
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

    private static List<Rope> getRandomRopes(Random random, List<Integer> charsToUseInString, int depth) {
        int numRopes;
        double roll = random.nextDouble();
        if (roll < 0.01) {
            numRopes = 0;
        } else if (roll < 0.5) {
            numRopes = random.nextInt(5);
        } else if (roll < 0.95) {
            numRopes = random.nextInt(10);
        } else {
            numRopes = random.nextInt(100);
        }
        List<Rope> result = new ArrayList<>(numRopes);
        for (int i = 0; i < numRopes; i++) {
            result.add(getRandomRope(random, charsToUseInString, depth + 1));
        }
        return result;
    }

    public static Rope getRandomRope(Random random, List<Integer> charsToUseInString) {
        return getRandomRope(random, charsToUseInString, 0);
    }
    private static Rope getRandomRope(Random random, List<Integer> charsToUseInString, int depth) {
        if (random.nextInt(depth + 2) == 0) {
            return ListRope.create(getRandomRopes(random, charsToUseInString, depth));
        } else {
            return StringRope.create(getRandomString(random, charsToUseInString));
        }
    }
}
