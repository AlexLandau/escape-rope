package net.alloyggp.escaperope;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

public class EscapeCharEscaperTest {

    @Test
    public void test() {
        Escaper escaper = EscapeCharEscaper.create('\\', new HashSet<Integer>(
                Arrays.asList(70, 80, 90)));

        String input = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        System.out.println(escaper.escape(input));
        Assert.assertEquals(input, escaper.unescape(escaper.escape(input)).get(0).getNonControlText());
    }
}
