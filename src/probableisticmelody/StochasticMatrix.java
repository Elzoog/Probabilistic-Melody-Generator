/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package probableisticmelody;

import java.awt.FileDialog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author bridean
 */
public class StochasticMatrix implements Serializable {

    private double[][] data;

    // Constructor without a size makes no sense
    private StochasticMatrix() {
        
    }
    
    public StochasticMatrix(int size) {
        data = new double[size][size];
    }

    public StochasticMatrix(ProbabilityVector vecset[]) {
        int size = vecset.length;
        int vecsize = vecset[0].getSize();
        if (size != vecsize) {
            throw new RuntimeException("Vector size doesn't match the number of vectors");
        }
        data = new double[size][size];
        for (int i = 0; i < size; i++) {
            double[] tmpvec = vecset[i].getVector();
            System.arraycopy(tmpvec, 0, data[i], 0, size);
        }
    }

    public int getSize() {
        return data.length;
    }

    public double[] getVector(int row) {
        double[] retvec = new double[data.length];
        for (int i = 0; i < data.length; i++) {
            retvec[i] = data[row][i];
        }
        return retvec;
    }
    
    public void setVector(ProbabilityVector prob, int row) {
        int size = data.length;
        for (int i=0; i<size; i++) {
            data[row][i] = prob.getValue(i);
        }
    }

    public static StochasticMatrix loadFromFile() {
        JFrame fr = new JFrame();
        FileDialog fd = new FileDialog(fr, "Load from what file?", FileDialog.LOAD);
        fd.setVisible(true);
        String fname = fd.getDirectory() + fd.getFile();
        FileInputStream fst;
        boolean isAssigned = false;
        try {
            fst = new FileInputStream(fname);
            ObjectInputStream in = new ObjectInputStream(fst);
            StochasticMatrix sto = (StochasticMatrix) in.readObject();
            return sto;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StochasticMatrix.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StochasticMatrix.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StochasticMatrix.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public void saveToFile() {
        JFrame fr = new JFrame();
        FileDialog fd = new FileDialog(fr, "Save to what file?", FileDialog.SAVE);
        fd.setVisible(true);
        String fname = fd.getDirectory() + fd.getFile();
        FileOutputStream fst;
        try {
            fst = new FileOutputStream(fname);
            ObjectOutputStream out = new ObjectOutputStream(fst);
            out.writeObject(this);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StochasticMatrix.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StochasticMatrix.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String toString() {
        String retval = "";
        int size = data.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                retval += String.format("%1.3f\t", data[i][j]);
            }
            retval += "\n";
        }
        return retval;
    }
}
