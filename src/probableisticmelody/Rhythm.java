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
public class Rhythm {
    private final double rhyval;
    
    // Don't call the default constructor
    private Rhythm() {
        rhyval = 0;
    }
    
    public Rhythm(double val) {
        rhyval = val;
    }
    
    public double getValue() {
        return rhyval;
    }
    
}
