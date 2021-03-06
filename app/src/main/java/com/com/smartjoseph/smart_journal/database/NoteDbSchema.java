package com.com.smartjoseph.smart_journal.database;

/**
 * Class holding database meta information.
 */
class NoteDbSchema {

    public static final class NoteTable {
        public static final String NAME = "notes";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String BODY = "body";
            public static final String DATE_CREATED = "dateCreated";
            public static final String DATE_EDITED = "dateUpdated";
        }
    }
}
