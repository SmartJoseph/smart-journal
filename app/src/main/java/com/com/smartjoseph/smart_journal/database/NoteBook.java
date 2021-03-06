package com.com.smartjoseph.smart_journal.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.com.smartjoseph.smart_journal.database.NoteDbSchema.NoteTable;
import com.com.smartjoseph.smart_journal.model.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A singleton class as the gateway medium for handling {@link Note} in the database.
 */
public class NoteBook {

    private static NoteBook sNoteBook = null;

    private final Context mContext;
    private final SQLiteDatabase mDatabase;
    private final NotesDatabaseManager notesDbManager;
    private boolean isTwoPane;

    public static NoteBook get(Context appContext) {
        if (sNoteBook == null) {
            sNoteBook = new NoteBook(appContext);
        }

        return sNoteBook;
    }

    private NoteBook(Context context) {
        mContext = context.getApplicationContext();
        notesDbManager = new NotesDatabaseManager(mContext);
        mDatabase = notesDbManager.getDatabase();
        isTwoPane = false;
    }

    /**
     * Retrieve all notes from the database.
     *
     * @return list of notes as {@link List} object.
     */
    public List<Note> getNotes() {
        List<Note> notes = new ArrayList<>();
        NoteCursorWrapper cursor = queryAllNotes(null, null);

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                notes.add(cursor.getNote());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return notes;
    }

    public Note getFirstNote() {
        String orderBy = NoteTable.Cols.UUID + " ASC";
        String limit = "1";
        NoteCursorWrapper wrapper = queryNoteByOrderLimit(orderBy, limit);

        try {
            if (wrapper.getCount() == 0) {
                return null;
            }

            wrapper.moveToFirst();
            return wrapper.getNote();
        } finally {
            wrapper.close();
        }
    }

    /**
     * Helper method build the custom cursor wrapper for query that
     * requires order by and limit parameters.
     *
     * @param orderBy SQLite parameter ORDER BY in String
     * @param limit   SQLite parameter LIMIT in String
     * @return NoteCursorWrapper object
     */
    private NoteCursorWrapper queryNoteByOrderLimit(String orderBy, String limit) {
        Cursor cursor = mDatabase.query(
                NoteTable.NAME,
                null,
                null,
                null,
                null,
                null,
                orderBy,
                limit
        );

        return new NoteCursorWrapper(cursor);
    }

    /**
     * Helper method build the custom cursor wrapper for query that
     * requires order by and limit parameters.
     *
     * @param whereClause SQLite parameter WHERE in String
     * @param whereArgs   SQLite parameter arguments in String[]
     * @return NoteCursorWrapper object
     */
    private NoteCursorWrapper queryAllNotes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                NoteTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new NoteCursorWrapper(cursor);
    }

    public Note getNote(UUID uuid) {
        String whereClause = NoteTable.Cols.UUID + " = ?";
        NoteCursorWrapper cursor = queryAllNotes(
                whereClause, new String[]{uuid.toString()}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getNote();
        } finally {
            cursor.close();
        }
    }

    private static ContentValues getContentValues(Note note) {
        ContentValues values = new ContentValues();
        values.put(NoteTable.Cols.UUID, note.getID().toString());
        values.put(NoteTable.Cols.TITLE, note.getTitle());
        values.put(NoteTable.Cols.BODY, note.getBody());
        values.put(NoteTable.Cols.DATE_CREATED, note.getDateCreated().getTime());
        values.put(NoteTable.Cols.DATE_EDITED, note.getDateEdited().getTime());

        return values;
    }

    public void addNote(Note note) {
        ContentValues values = getContentValues(note);

        mDatabase.insert(NoteTable.NAME, null, values);
    }

    public void updateNote(Note note) {
        String uuidString = note.getID().toString();
        ContentValues values = getContentValues(note);

        mDatabase.update(NoteTable.NAME, values,
                NoteTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    public void deleteNote(Note note) {
        String uuidString = note.getID().toString();

        mDatabase.delete(NoteTable.NAME,
                NoteTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    public boolean isTwoPane() {
        return isTwoPane;
    }

    public void setIsTwoPane(boolean isTwoPane) {
        this.isTwoPane = isTwoPane;
    }

    public boolean closeDatabase() {
        return notesDbManager.close();
    }
}
