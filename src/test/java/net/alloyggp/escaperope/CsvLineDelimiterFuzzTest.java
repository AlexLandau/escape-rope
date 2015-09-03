package net.alloyggp.escaperope;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class CsvLineDelimiterFuzzTest {
    @Test
    public void testWithFixedChars() throws Exception {
        List<Integer> charsToUseInString = Arrays.<Integer>asList(
                0,
                (int) ',',
                (int) '\\',
                (int) 'a',
                (int) 'b',
                (int) 'c',
                (int) ' ',
                (int) '"');
        Delimiter delimiter = CsvLineDelimiter.create();
        for (int seed = 0; seed < 10000; seed++) {
            Random random = new Random(seed);
            try {
                List<String> strings = FuzzTests.getRandomStrings(random, charsToUseInString);
                Assert.assertEquals(strings, delimiter.undelimit(delimiter.delimit(strings)));
            } catch (Throwable t) {
                throw new AssertionError("Seed was " + seed, t);
            }
        }
    }

}
