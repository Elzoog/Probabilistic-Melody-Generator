/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package probableisticmelody;

import java.util.ArrayList;

/**
 * Would use the Java Map collection class (if I use the MidiSequence quartervalue
 * as an int and map an int to a double) except that I need to control the array of
 * doubles so that they add to 1.0.  Only way I can think of to do that is to use an 
 * internal arraylist and then normalize the doubles as soon as the map is locked.  
 * Which means that, if it's locked then no more rhythms can be added to the map.
 * This allows two things:
 * 1) The user can add various rhythms such as triplets, or sqrt(2) or whatever.
 * 2) I can make sure the probabilities the rhythms occur add up to 1.0
 * 
 */
public class RhythmMap {
    private ArrayList<Rhythm> rharlist = new ArrayList<Rhythm>();
    private ArrayList<Double> dblist = new ArrayList<Double>();
    private ProbabilityVector probvec;
    private boolean locked = false;
    
    public RhythmMap() {
        
    }
    
    public boolean addRhythm(Rhythm var, double prob) {
        if (!locked) {
            rharlist.add(var);
            dblist.add(prob);
            return true;
        }
        return false;
    }
    
    public void lock() {
        locked = true;
        // Now get the probabilities and normalize them to 1.0
        double[] dlist = new double[dblist.size()];
        for (int i=0; i<dblist.size(); i++) {
            dlist[i] = dblist.get(i);
        }
        probvec = new ProbabilityVector(dlist);
    }
    
    public boolean isLocked() {
        return locked;
    }
    
    public ProbabilityVector getVector() {
        return probvec;
    }
    
    /**
     * The MidiSequence.quartervalue is used here instead of in the Rhythm class
     * to allow for a bit more flexibility.  That way, a function called 
     * getNextRhythm() or similar sort of function could be added later.
     * @return 
     */
    
    public int getNextMidiRhythm() {
        double nextdbl = MelodiesAndRhythm.rand.nextDouble();
        int which = probvec.getProbabilityLocation(nextdbl);
        Rhythm whichrh = rharlist.get(which);
        int retval = (int) (whichrh.getValue() * MidiSequence.quartervalue);
        return retval;
    }
    
    public Rhythm getNextRhythm() {
        double nextdbl = MelodiesAndRhythm.rand.nextDouble();
        int which = probvec.getProbabilityLocation(nextdbl);
        return rharlist.get(which); 
    }
    
}
