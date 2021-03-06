package com.com.smartjoseph.smart_journal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.Menu;

import com.com.smartjoseph.smart_journal.database.NoteBook;
import com.com.smartjoseph.smart_journal.model.Note;
import com.com.smartjoseph.smart_journal.view.NotePagerActivity;
import com.com.smartjoseph.smart_journal.view.SingleFragmentActivity;
import com.com.smartjoseph.smart_journal.view.fragment.NoteListFragment;
import com.com.smartjoseph.smart_journal.view.fragment.NoteViewFragment;

import java.util.UUID;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Main activity of the application. Host {@link NoteListFragment} for single pane
 * configuration. For two-pane configuration, this activity host both {@link NoteListFragment}
 * and {@link NoteViewFragment}. Implements Callbacks interfaces for both fragments.
 */
public class NoteListActivity extends SingleFragmentActivity
        implements NoteListFragment.Callbacks, NoteViewFragment.Callbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load default settings values
        PreferenceManager.setDefaultValues(this, R.xml.settings_general, false);
    }

    @Override
    protected Fragment createFragment() {
        return new NoteListFragment();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onNoteSelected(Note note, boolean isNewNote) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = NotePagerActivity.newIntent(this, note.getID(), isNewNote);
            startActivity(intent);
        } else {
            Fragment newNoteDetail = NoteViewFragment.newInstance(note.getID(), isNewNote);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newNoteDetail)
                    .commit();

            // Auto scroll to last item when creating a new note. A new note is placed
            // at the bottom by the Adapter.
            if (isNewNote) {
                NoteListFragment listFragment = (NoteListFragment)
                        getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                listFragment.updateUI();
                listFragment.scrollToLastItem();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onNoteDelete() {
        // Update list
        NoteListFragment listFragment = (NoteListFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        listFragment.updateUI();

        // Remove the deleted note's fragment
        NoteViewFragment fragment = (NoteViewFragment)
                getSupportFragmentManager().findFragmentById(R.id.detail_fragment_container);
        getSupportFragmentManager().beginTransaction().remove(fragment).commit();

        // Set first note in the list as the new detail pane note
        UUID noteId = listFragment.getFirstNoteFromList();
        if (noteId != null) {
            Fragment replacedNote = NoteViewFragment.newInstance(noteId, false);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, replacedNote)
                    .commit();
            // Then auto scroll to first position
            listFragment.scrollToFirstItem();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void tryToCancelSearchQueryOnNewActions() {
        NoteListFragment listFragment = (NoteListFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        listFragment.clearSearchForTwoPane();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Note getCurrentNoteDisplayedInDetailFragment() {
        NoteViewFragment viewFragment = (NoteViewFragment)
                getSupportFragmentManager().findFragmentById(R.id.detail_fragment_container);
        return (viewFragment == null) ? null : viewFragment.getCurrentNoteDisplayedForTwoPane();
    }

    /**
     * {@inheritDoc}
     * <p/>
     * Initialize the contents of the Activity's standard options menu for the
     * tablet interface instead of individual menu creation for its fragments.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (NoteBook.get(this).isTwoPane()) {
            getMenuInflater().inflate(R.menu.menu_two_pane, menu);
            return true;
        } else {
            return super.onCreateOptionsMenu(menu);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keepSearchViewExpandedIfPreviouslyExpanded() {
        NoteListFragment listFragment = (NoteListFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        listFragment.reQuerySearchViewUponClickingFilteredNote();
    }
}
