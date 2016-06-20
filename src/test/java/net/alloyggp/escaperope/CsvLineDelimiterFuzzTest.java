package net.alloyggp.escaperope;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

public class CsvLineDelimiterFuzzTest {
    @Test
    public void testKeepingNulls() throws Exception {
        //TODO: Add tricky Unicode characters
        List<Integer> charsToUseInString = Arrays.<Integer>asList(
                0,
                (int) ',',
                (int) '\\',
                (int) 'a',
                (int) 'b',
                (int) 'c',
                (int) ' ',
                (int) '"',
                0x2c5c,
                0x5c2c,
                0x1005c,
                0x1002c,
                0x12c5c,
                0x15c2c,
                0x15c5c,
                0x12c2c);
        Delimiter delimiter = //CsvLineDelimiter.create(NullBehavior.KEEP);
                Delimiters.getCsvLineDelimiter(NullBehavior.KEEP);
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
