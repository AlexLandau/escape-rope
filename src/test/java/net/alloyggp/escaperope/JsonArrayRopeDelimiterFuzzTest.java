package net.alloyggp.escaperope;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import net.alloyggp.escaperope.rope.Rope;
import net.alloyggp.escaperope.rope.StringRope;

public class JsonArrayRopeDelimiterFuzzTest {
    @Test
    public void testConvertingRandomStrings() {
        List<Integer> charsToUseInString = Arrays.<Integer>asList(
                0,
                (int) ',',
                (int) '\\',
                (int) 'a',
                (int) 'b',
                (int) 'c',
                (int) '[',
                (int) ']',
                (int) ' ',
                (int) '"',
                0xb0, 0xb1, 0xb2, 0xb3,
                0x2c5c,
                0x5c2c,
                0x1005c,
                0x1002c,
                0x12c5c,
                0x15c2c,
                0x15c5c,
                0x12c2c);
        JsonArrayRopeDelimiter delimiter = JsonArrayRopeDelimiter.create();

        for (int i = 0; i < 10000; i++) {
            Random random = new Random(i);
            Rope input = StringRope.create(FuzzTests.getRandomString(random, charsToUseInString));
            String output = delimiter.delimit(input);
            Rope retrieved = delimiter.undelimit(output);
            Assert.assertEquals(input, retrieved);
        }
    }

    @Test
    public void testConvertingRandomRopes() {
        List<Integer> charsToUseInString = Arrays.<Integer>asList(
                0,
                (int) ',',
                (int) '\\',
                (int) 'a',
                (int) 'b',
                (int) 'c',
                (int) '[',
                (int) ']',
                (int) ' ',
                (int) '"',
                0xb0, 0xb1, 0xb2, 0xb3,
                0x2c5c,
                0x5c2c,
                0x1005c,
                0x1002c,
                0x12c5c,
                0x15c2c,
                0x15c5c,
                0x12c2c);
        JsonArrayRopeDelimiter delimiter = JsonArrayRopeDelimiter.create();

        for (int i = 0; i < 10000; i++) {
            Random random = new Random(i);
            Rope input = FuzzTests.getRandomRope(random, charsToUseInString);
            String output = delimiter.delimit(input);
            Rope retrieved = delimiter.undelimit(output);
            Assert.assertEquals(input, retrieved);
        }
    }
}
