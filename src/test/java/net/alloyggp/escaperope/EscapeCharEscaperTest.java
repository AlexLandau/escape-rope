package net.alloyggp.escaperope;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

public class EscapeCharEscaperTest {

    @Test
    public void test() {
        Escaper escaper = EscapeCharEscaper.createConvertingNulls('\\', new HashSet<Integer>(
                Arrays.asList(70, 80, 90)));

        String input = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Assert.assertNotEquals(input, escaper.escape(input));
        Assert.assertEquals(input, escaper.unescape(escaper.escape(input)).get(0).getNonControlText());

        String input2 = "\\abcdefghijklmnopqrstuvwxyz\\ABCDEFGHIJKLMNOPQRSTUVWXYZ\\";
        Assert.assertNotEquals(input2, escaper.escape(input2));
        Assert.assertEquals(input2, escaper.unescape(escaper.escape(input2)).get(0).getNonControlText());
    }
}
