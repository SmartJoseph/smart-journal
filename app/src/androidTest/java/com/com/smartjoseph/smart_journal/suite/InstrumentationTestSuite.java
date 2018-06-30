package com.com.smartjoseph.smart_journal.suite;

import com.com.smartjoseph.smart_journal.ui.NewNoteEspressoTest;
import com.com.smartjoseph.smart_journal.ui.NoteListFragmentEspressoTest;
import com.com.smartjoseph.smart_journal.ui.OldNoteEspressoTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Runs all Instrumentation tests.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        NewNoteEspressoTest.class,
        OldNoteEspressoTest.class,
        NoteListFragmentEspressoTest.class})
public class InstrumentationTestSuite {
}
