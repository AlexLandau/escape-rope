package net.alloyggp.escaperope;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import net.alloyggp.escaperope.rope.Rope;
import net.alloyggp.escaperope.rope.StringRope;

public class PrototypeRopeDelimiterFuzzTest {
    @Test
    public void testConvertingRandomStrings() {
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
                0xb0, 0xb1, 0xb2, 0xb3);
        PrototypeRopeDelimiter delimiter = PrototypeRopeDelimiter.create();

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
                0xb0, 0xb1, 0xb2, 0xb3);
        PrototypeRopeDelimiter delimiter = PrototypeRopeDelimiter.create();

        for (int i = 0; i < 10000; i++) {
            Random random = new Random(i);
            Rope input = FuzzTests.getRandomRope(random, charsToUseInString);
            String output = delimiter.delimit(input);
            Rope retrieved = delimiter.undelimit(output);
            Assert.assertEquals(input, retrieved);
        }
    }
}
