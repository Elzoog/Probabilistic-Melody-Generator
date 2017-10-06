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
public class ProbabilityVector {
    private final double[] vec;

    private void normalize() {
        double total = 0.0;
        for (int i=0; i<vec.length; i++) {
            total += vec[i];
        }
        if (total == 0.0) {
            throw new RuntimeException("Zero vector was passed.");
        }
        for (int i=0; i<vec.length; i++) {
            vec[i] = vec[i]/total;
        }
    }

    
    // Never call the empty constructor
    private ProbabilityVector() {
        vec = null;
    }
    
    public ProbabilityVector(double[] data) {
        int size = data.length;
        vec = new double[size];
        System.arraycopy(data, 0, vec, 0, size);
        normalize();
    }
    
    public double[] getVector() {
        double[] retdata = new double[vec.length];
        System.arraycopy(vec, 0, retdata, 0, vec.length);
        return retdata;
    }
    
    public double getValue(int loc) {
        return vec[loc];
    }
    
    public int getSize() {
        return vec.length;
    }
    
    public int getProbabilityLocation(double prob) {
        int size = vec.length;
        double dfind = 0.0;
        int loc = 0;
        while (dfind <= prob) {
            dfind += vec[loc];
            loc++;
        }
        return (loc - 1);
    }
    
}
