package net.alloyggp.escaperope;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import net.alloyggp.escaperope.restrict.Restricter;
import net.alloyggp.escaperope.restrict.SimpleNewlineRestricter;

public class SimpleNewlineRestricterTest {
    @Test
    public void testStringsWithoutNewlines() {
        Restricter restricter = SimpleNewlineRestricter.createDefault();

        String string = "abc123";
        assertEquals(string, restricter.restrict(string));
        assertEquals(string, restricter.unrestrict(string));
    }

    @Test
    public void testRoundTripWithUnixNewline() {
        Restricter restricter = SimpleNewlineRestricter.createDefault();

        String string = "abc\n123";
        assertEquals(string, restricter.unrestrict(restricter.restrict(string)));
    }

    @Test
    public void testNoNewlinesInRestrictionWithUnixNewline() {
        Restricter restricter = SimpleNewlineRestricter.createDefault();

        String string = "abc\n123";
        String restricted = restricter.restrict(string);
        assertFalse(restricted.contains("\n"));
        assertFalse(restricted.contains("\r"));
    }

    @Test
    public void testRoundTripWithWindowsNewline() {
        Restricter restricter = SimpleNewlineRestricter.createDefault();

        String string = "abc\r\n123";
        assertEquals(string, restricter.unrestrict(restricter.restrict(string)));
    }

    @Test
    public void testNoNewlinesInRestrictionWithWindowsNewline() {
        Restricter restricter = SimpleNewlineRestricter.createDefault();

        String string = "abc\r\n123";
        String restricted = restricter.restrict(string);
        assertFalse(restricted.contains("\n"));
        assertFalse(restricted.contains("\r"));
    }

    @Test
    public void testRoundTripWithBackslash() {
        Restricter restricter = SimpleNewlineRestricter.createDefault();

        String string = "abc\\123";
        assertEquals(string, restricter.unrestrict(restricter.restrict(string)));
    }

    @Test
    public void testNoNewlinesInRestrictionWithBackslash() {
        Restricter restricter = SimpleNewlineRestricter.createDefault();

        String string = "abc\\123";
        String restricted = restricter.restrict(string);
        assertFalse(restricted.contains("\n"));
        assertFalse(restricted.contains("\r"));
    }

    @Test
    public void testRoundTripWithTrickyString() {
        Restricter restricter = SimpleNewlineRestricter.createDefault();

        String string = "abc\\n\n\r\\\\r\\\rr123";
        assertEquals(string, restricter.unrestrict(restricter.restrict(string)));
    }

    @Test
    public void testNoNewlinesInRestrictionWithTrickyString() {
        Restricter restricter = SimpleNewlineRestricter.createDefault();

        String string = "abc\\n\n\r\\\\r\\\rr123";
        String restricted = restricter.restrict(string);
        assertFalse(restricted.contains("\n"));
        assertFalse(restricted.contains("\r"));
    }

}
