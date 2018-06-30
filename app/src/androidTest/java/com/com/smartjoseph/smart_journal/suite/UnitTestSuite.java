package com.com.smartjoseph.smart_journal.suite;

import com.com.smartjoseph.smart_journal.unit.NoteTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Runs all unit tests.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        NoteTest.class})
public class UnitTestSuite {
}
