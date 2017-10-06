/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package probableisticmelody;

/**
 *
 * @author bridean
 */
public class Note {
    // Primary data
    private final int ntValue;  // Value of the note as a MIDI int
    
    /* TODO
    Modify the class to enable note names such as C4, A2, ect.
    So as not to clutter up the validNoteNames array, treat the number at the end
    as an octave number seperate from the name of the note.
     */


    // Things such as "D-Flat" can be added later
    static final String[] validNoteNames = {"Rest", "C", "C-Sharp", "D", "D-Sharp", "E",
        "F", "F-Sharp", "G", "G-Sharp", "A", "A-Sharp", "B"};


    // Useful function if the user of this class keeps sending the wrong name
    public static String getValidNoteName(int num) {
        if (num == -1) {
            return "Rest";
        }
        int bnote = num % 12; // Base note
        // To be used if later on we start using things like C4
        int octave = num / 12;
        switch (bnote) {
            case 0:
                return "C";
            case 1:
                return "C-Sharp";
            case 2:
                return "D";
            case 3:
                return "D-Sharp";
            case 4:
                return "E";
            case 5:
                return "F";
            case 6:
                return "F-Sharp";
            case 7:
                return "G";
            case 8:
                return "G-Sharp";
            case 9:
                return "A";
            case 10:
                return "A-Sharp";
            case 11:
                return "B";
        }
        // Should never get here
        return "Invalid";
    }

    /*
    DO NOT assume that the int value is a simple function of the location of
    the string in the array (i.e. "C-Sharp" is 1 + the location of "C").  
     */
    private int NoteToInt(String ntName) {
        // Switch statement enables case "C-Sharp", "D-Flat" to be added later
        switch (ntName) {
            case "Rest":
                return -1;
            case "C":
                return 0;
            case "C-Sharp":
                return 1;
            case "D":
                return 2;
            case "D-Sharp":
                return 3;
            case "E":
                return 4;
            case "F":
                return 5;
            case "F-Sharp":
                return 6;
            case "G":
                return 7;
            case "G-Sharp":
                return 8;
            case "A":
                return 9;
            case "A-Sharp":
                return 10;
            case "B":
                return 11;
        }
        // Should never get here
        return -2;
    }

    public static boolean isValidName(String name) {
        for (int i = 0; i < validNoteNames.length; i++) {
            if (name == validNoteNames[i]) {
                return true;
            }
        }
        return false;
    }

    // For example, a quarter note middle C would be Note("C", 4)
    public Note(String NoteName, int octave) {
        if (isValidName(NoteName)) {
            int tmpnote = NoteToInt(NoteName);
            ntValue = tmpnote + (12 * (octave + 1));
        } else {
            throw new RuntimeException(NoteName + " is not a valid note name.");
        }
    }
    
    public Note(int midival) {
        ntValue = midival;
    }
    
    public int getNoteValue() {
        return ntValue;
    }
    
    
    @Override
    public String toString() {
        return getValidNoteName(ntValue);
    }

}
