/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package probableisticmelody;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class of variables that need to be sort of global so that various tabs 
 * can access them.
 * 
 * @author bridean
 */
public class MelodiesAndRhythm {
    private static ArrayList<MidiSequence> melodies = new ArrayList<MidiSequence> ();
    private static RhythmMap mainRhy = new RhythmMap();
    private static NoteMap nmap = new NoteMap();
    private static StochasticMatrix mainMatrix = new StochasticMatrix(12);
    /* Declare random globally because creating various instances of the Random
    class destroys "true randomness"
    */
    public static Random rand = new Random();
    
    public static int numMelodies() {
        return melodies.size();
    }
    
    public static RhythmMap getRhythmMap() {
        return mainRhy;
    }
    
    public static void setRhythmMap(RhythmMap rm) {
        mainRhy = rm;
    }
    
    public static void addMelody(MidiSequence mid) {
        melodies.add(mid);
    }
    
    public static MidiSequence getMelody(int var) {
        return melodies.get(var);
    }
    
    public static void setNoteMap(NoteMap nm) {
        nmap = nm;
    }
    
    public static NoteMap getNoteMap() {
        return nmap;
    }
    
    public static StochasticMatrix getMatrix() {
        return mainMatrix;
    }
    
    public static StochasticMatrix getNewMatrix() {
        mainMatrix = StochasticMatrix.loadFromFile();
        return mainMatrix;
    }
    
    public static void saveMatrix() {
        mainMatrix.saveToFile();
    }
}
