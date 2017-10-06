/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package probableisticmelody;


/**
 * NoteMap differs from RhythmMap in that the number of elements is fixed (12).
 * So using an arraylist isn't necessary
 */
public class NoteMap {

    private ProbabilityVector prob;
    private double[] probvec = new double[12];
    private boolean islocked = false;

    public NoteMap() {
    }

    public boolean addProbability(Note nt, double prob) {
        if (!islocked) {
            int val = (nt.getNoteValue() % 12);
            probvec[val] = prob;
            return true;
        }
        return false;
    }

    public void lock() {
        islocked = true;
        prob = new ProbabilityVector(probvec);
    }
    
    public boolean islocked() {
        return islocked;
    }

    private int getNextNoteMod(int iloc, int min, int max) {
        int loc = iloc;
        int minans, maxans = 0;
        while (loc < min) {
            loc += 12;
        }
        minans = loc;
        while (loc < max) {
            loc += 12;
        }
        if (loc > max) {
            maxans = loc - 12;
        } else {
            maxans = loc;
        }
        int howmany = ((maxans - minans) / 12) + 1;
        if (howmany > 1) {
            int oct = MelodiesAndRhythm.rand.nextInt(howmany);
            return minans + (12 * oct);
        }
        return minans;
    }

    public Note getNextNote(int min, int max) {
        if ((max - min) > 12) {
            double dfind = MelodiesAndRhythm.rand.nextDouble();
            int loc = prob.getProbabilityLocation(dfind);
            int next = getNextNoteMod(loc, min, max);
            return new Note(next);
        }
        // Don't want to simply pick a random integer because of the probabilities
        int minmod = min % 12;
        int maxmod = max % 12;
        boolean found = false;
        int loc = 0;
        while (!found) {
            // Keep picking notes according to the probability table until you find 
            // one in range.
            double dfind = MelodiesAndRhythm.rand.nextDouble();
            loc = prob.getProbabilityLocation(dfind);
            // make sure loc is between min and max
            while (loc < min) {
                loc += 12;
            }
            // loc is definitely equal to or greater than min
            if (loc <= max) {
                found = true;
            }
        }
        return new Note(loc);
    }

}
