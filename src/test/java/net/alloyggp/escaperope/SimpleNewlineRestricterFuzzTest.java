package net.alloyggp.escaperope;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import net.alloyggp.escaperope.restrict.Restricter;
import net.alloyggp.escaperope.restrict.SimpleNewlineRestricter;

public class SimpleNewlineRestricterFuzzTest {
    @Test
    public void testRoundTripAndRestricted() throws Exception {
        //TODO: Add additional tricky Unicode characters
        List<Integer> charsToUseInString = Arrays.<Integer>asList(
                0,
                (int) ',',
                (int) '\\',
                (int) 'a',
                (int) 'n',
                (int) 'r',
                (int) '\n',
                (int) '\r',
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
        Restricter restricter = SimpleNewlineRestricter.createDefault();
        for (int seed = 0; seed < 100000; seed++) {
            Random random = new Random(seed);
            try {
                String string = FuzzTests.getRandomString(random, charsToUseInString);
                String restricted = restricter.restrict(string);
                if (restricted != null) {
                    Assert.assertFalse(restricted.contains("\n"));
                    Assert.assertFalse(restricted.contains("\r"));
                }
                Assert.assertEquals(string, restricter.unrestrict(restricted));
            } catch (Throwable t) {
                throw new AssertionError("Seed was " + seed, t);
            }
        }
    }

    @Test
    public void testRoundTripAndRestrictedWithNondefaultEscapeChar() throws Exception {
        //TODO: Add additional tricky Unicode characters
        List<Integer> charsToUseInString = Arrays.<Integer>asList(
                0,
                (int) ',',
                (int) '\\',
                (int) 'a',
                (int) 'A',
                (int) 'n',
                (int) 'r',
                (int) '\n',
                (int) '\r',
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
        Restricter restricter = SimpleNewlineRestricter.createWithEscapeChar('A');
        for (int seed = 0; seed < 100000; seed++) {
            Random random = new Random(seed);
            try {
                String string = FuzzTests.getRandomString(random, charsToUseInString);
                String restricted = restricter.restrict(string);
                if (restricted != null) {
                    Assert.assertFalse(restricted.contains("\n"));
                    Assert.assertFalse(restricted.contains("\r"));
                }
                Assert.assertEquals(string, restricter.unrestrict(restricted));
            } catch (Throwable t) {
                throw new AssertionError("Seed was " + seed, t);
            }
        }
    }

}
