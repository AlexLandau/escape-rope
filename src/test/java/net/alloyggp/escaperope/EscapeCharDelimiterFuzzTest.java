package net.alloyggp.escaperope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class EscapeCharDelimiterFuzzTest {
    @Test
    public void testConvertingNulls() throws Exception {
        int delimiterChar = ',';
        int escapeChar = '\\';
        //TODO: Add tricky Unicode characters
        List<Integer> charsToUseInString = Arrays.<Integer>asList(
                0,
                (int) ',',
                (int) '\\',
                (int) 'a',
                (int) 'b',
                (int) 'c',
                (int) ' ');
        Delimiter delimiter = //EscapeCharDelimiter.createConvertingNulls(delimiterChar, escapeChar);
                Delimiters.getEscapeCharDelimiterConvertingNulls(delimiterChar, escapeChar);
        for (int seed = 0; seed < 10000; seed++) {
            Random random = new Random(seed);
            try {
                List<String> strings = FuzzTests.getRandomStrings(random, charsToUseInString);
                List<String> convertedStrings = replaceNulls(strings);
                Assert.assertEquals(convertedStrings, delimiter.undelimit(delimiter.delimit(strings)));
            } catch (Throwable t) {
                throw new AssertionError("Seed was " + seed, t);
            }
        }
    }

    private List<String> replaceNulls(List<String> strings) {
        List<String> results = new ArrayList<>(strings.size());
        for (String string : strings) {
            if (string != null) {
                results.add(string);
            } else {
                results.add("");
            }
        }
        return results;
    }

}
