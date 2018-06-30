package com.com.smartjoseph.smart_journal.data;

/**
 * Class for providing global data at runtime where Android xml resources are not applicable.
 */
public class GlobalValues {

    private GlobalValues() {
    }

    public static final int FONT_SIZE_DEFAULT = 18;
    public static final float FONT_SIZE_DIFFERENCE = 4.0F;
    public static final String WELCOME_TITLE = "Hello! This is your Smart Journal";
    public static final String WELCOME_BODY = "Thinking of a smart and easy-to-use journal where you could type, safe, and secure your personal notes?\n" +
            "Smart Journal is all you need to serve that purpose efficiently.\n" +
            "It's easy to use, and has a simply unique user  interface and experience.\n" +
            "\n" +
            "Key Features\n" +
            "--------\n" +
            "- Google and Firebase Authentication\n" +
            "- Beautifully designed User Interface\n" +
            "- Search and filter notes easily\n" +
            "- Edit notes with just one touch\n" +
            "- Support for both small and large screen devices\n" +
            "- Directly share your notes to your friends and loved ones\n" +
            "Create journals the Smart way!";

    public static final String ChangeLogTitle = "Change Log";
    public static final String ChangeLogMsg = "\nSmartJournal 1.0 (June 30 2018)";
}
