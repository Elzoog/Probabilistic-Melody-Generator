/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package probableisticmelody;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.swing.*;
import javax.sound.midi.*;

public class ProbFrame {

    private static void createAndShowGUI() {
        JFrame mainFrame = new JFrame("Generate Probabilistic Melody");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTabbedPane tabbedPane = new JTabbedPane();
        GeneralPanel gPanel = new GeneralPanel();
        tabbedPane.addTab("General", null, gPanel,
                "Does nothing");     
        RhythmPanel rhPanel = new RhythmPanel();
        tabbedPane.addTab("Rhythms", null, rhPanel,
                "Does nothing");             
        mainFrame.getContentPane().add(tabbedPane);
        mainFrame.pack();
        gPanel.resize();
        Dimension dim = new Dimension(800, 600);
        mainFrame.setMinimumSize(dim);
        mainFrame.setVisible(true);
        // ntPanel.resizeChecks();
    }


    public ProbFrame() {
    }
    
    public static void main(String[] args) {
        /* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);

        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    
}
