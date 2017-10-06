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

public class OldProbFrame extends JFrame {

    public static JTextField[] probFields = new JTextField[12];
    public static JTextField manyNotes;
    public static JButton genMelodyBtn, playMelodyBtn, saveMelodyBtn;
    public static JButton normalizeProbBtn, randomizeBtn;
    public static JLabel melGenerated = new JLabel("Melody generated     ");
    // MIDI sequence for the main melody
    private static Sequence seq = null;
    private static int numMelodies = 0;

    public static final double[] MajorProbabilities
            = {0.383155948031232, // C
                0.001925358156326, // C-Sharp 
                0.090450849718747, // D
                0.003115294937453, // D-Sharp or E-Flat
                0.146352549156242, // E
                0.021352549156242, // F
                0.013196601125011, // F-Sharp
                0.236803398874989, // G
                0.005040653093779, // G-Sharp
                0.055901699437495, // A
                0.008155948031232, // A-Sharp
                0.034549150281253}; // B

    public OldProbFrame() {
        super("Generate Probabilistic Melody");
        // setResizable(false);
    }

    public static JPanel createNoteEntryPanel() {
        JPanel ntEntry = new JPanel();
        String borderTitle = "Enter in the probability for each note";
        // ntEntry.setBorder(javax.swing.BorderFactory.createTitledBorder(null, borderTitle, 
        //        javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));
        ntEntry.setLayout(new BoxLayout(ntEntry, BoxLayout.LINE_AXIS));
        JPanel gridPanel = new JPanel();
        gridPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, borderTitle,
                javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP));
        gridPanel.setLayout(new GridLayout(12, 12, 10, 10));
        for (int i = 0; i < 12; i++) {
            JLabel tmplbl = new JLabel(Note.getValidNoteName(i + 48));
            probFields[i] = new JTextField("" + MajorProbabilities[i]);
            gridPanel.add(tmplbl);
            gridPanel.add(probFields[i]);
        }
        ntEntry.add(Box.createHorizontalGlue());
        ntEntry.add(Box.createRigidArea(new Dimension(10, 0)));
        ntEntry.add(gridPanel);
        ntEntry.add(Box.createRigidArea(new Dimension(10, 0)));
        ntEntry.add(Box.createHorizontalGlue());

        return ntEntry;
    }
    
    private static JPanel createAdjBtnPanel() {
        JPanel aPanel = new JPanel();
        aPanel.setLayout(new BoxLayout(aPanel, BoxLayout.LINE_AXIS));
        normalizeProbBtn = new JButton("Normalize");
        normalizeProbBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalizeProbabilities(evt, probFields);
            }
        });
        randomizeBtn = new JButton("Randomize");
        randomizeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                randomizeProbabilities(evt, probFields);
            }
        });
        
        aPanel.add(Box.createHorizontalGlue());
        aPanel.add(randomizeBtn);
        aPanel.add(Box.createHorizontalStrut(10));
        aPanel.add(normalizeProbBtn);
        aPanel.add(Box.createHorizontalGlue());
        return aPanel;
    }

    private static JPanel createMelodyPanel() {
        JPanel melPanel = new JPanel();
        melPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null,
                "Create your melody", javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.TOP));
        melPanel.setLayout(new BoxLayout(melPanel, BoxLayout.PAGE_AXIS));
        JPanel firstPanel = new JPanel();
        firstPanel.setLayout(new BoxLayout(firstPanel, javax.swing.BoxLayout.LINE_AXIS));
        JLabel howm = new JLabel("How many notes?");
        firstPanel.add(howm);
        firstPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        manyNotes = new JTextField("20");
        firstPanel.add(manyNotes);
        melPanel.add(firstPanel);
        JPanel secPanel = new JPanel();
        secPanel.setLayout(new BoxLayout(secPanel, javax.swing.BoxLayout.LINE_AXIS));
        genMelodyBtn = new JButton("Generate a melody");
        genMelodyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateMelody(evt);
            }
        });
        secPanel.add(genMelodyBtn);
        secPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        melGenerated.setEnabled(false);
        secPanel.add(melGenerated);
        melPanel.add(secPanel);
        JPanel thirdPanel = new JPanel();
        thirdPanel.setLayout(new BoxLayout(thirdPanel, javax.swing.BoxLayout.LINE_AXIS));
        playMelodyBtn = new JButton("Play the melody");
        saveMelodyBtn = new JButton("Save the melody");
        playMelodyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playMelody(evt);
            }
        });
        saveMelodyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMelody(evt);
            }
        });
        thirdPanel.add(playMelodyBtn);
        thirdPanel.add(Box.createRigidArea(new Dimension(10,0)));
        thirdPanel.add(saveMelodyBtn);
        melPanel.add(thirdPanel);

        return melPanel;
    }
    
    public static void normalizeProbabilities(ActionEvent evt, JTextField[] fields) {
        int len = fields.length;
        double[] fdbl = new double[len];
        double total = 0.0;
        for (int i=0; i<len; i++) {
            double tmpdbl = Double.parseDouble(fields[i].getText());
            fdbl[i] = tmpdbl;
            total += tmpdbl;
        }
        for (int i=0; i<len; i++) {
            String tmpStr = String.format("%1.12f", fdbl[i]/total);
            fields[i].setText(tmpStr);
        }
    }

    public static void randomizeProbabilities(ActionEvent evt, JTextField[] fields) {
        Random rnd = new Random();
        int len = fields.length;
        double[] fdbl = new double[len];
        double total = 0.0;
        for (int i=0; i<len; i++) {
            double tmpdbl = rnd.nextDouble();
            fdbl[i] = tmpdbl;
            total += tmpdbl;
        }
        for (int i=0; i<len; i++) {
            String tmpStr = String.format("%1.12f", fdbl[i]/total);
            fields[i].setText(tmpStr);
        }
    }
    
    public static void saveMelody(ActionEvent evt) {
        try {
            File file = new File("test.midi");
            JFileChooser fc = new JFileChooser(file);
            if (!(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)) {
                System.out.println("Some kind of problem");
                return;
            }
            file = fc.getSelectedFile();
            
            int[] fileTypes = MidiSystem.getMidiFileTypes(seq);
            if (fileTypes.length == 0) {
                System.out.println("Can't save sequence");
            } else {
                if (MidiSystem.write(seq, fileTypes[0], file) == -1) {
                    throw new IOException("Problems writing to file");
                }
            }
        } catch (SecurityException ex) {
            //JavaSound.showInfoDialog();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void playMelody(ActionEvent evt) {
        try {
            Sequencer player = MidiSystem.getSequencer();
            player.open();
            player.setSequence(seq);
            player.setTempoInBPM(120);
            player.start();
        } catch (SecurityException ex) {
            //JavaSound.showInfoDialog();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
    private static void generateMelody(ActionEvent evt) {
        // For convenience, let C = C below middle C  midi = 48
        Random rand = new Random();
        double[] probvector = new double[12];
        double total = 0.0;
        for (int i = 0; i < 12; i++) {
            double tmpdbl = Double.parseDouble(probFields[i].getText());
            probvector[i] = tmpdbl;
            total += tmpdbl;
        }
        // Need to get the probabilities to add to 1.0
        // Unfortunately no way to combine this into the above loop
        for (int i = 0; i < 12; i++) {
            probvector[i] = probvector[i] / total;
        }
        try {
            int tquarter = 26880;
            Sequencer player = MidiSystem.getSequencer();
            player.open();
            //节拍器
            seq = new Sequence(Sequence.PPQ, tquarter);
            int res = seq.getResolution();
            Track track = seq.createTrack();

            int r = 0;
            int noteint = 0;
            int volume = 120;
            int duration = tquarter;
            int numnotes = Integer.parseInt(manyNotes.getText());
            int ticks = 0;
            for (int i = 0; i < numnotes; i++) {
                r = getNextNoteVal(rand.nextDouble(), probvector);
                duration = getNextDuration(tquarter, rand.nextDouble());
                // Note on
                track.add(makeEvent(144, 1, r, volume, ticks));
                // Note off
                track.add(makeEvent(128, 1, r, volume, ticks + (duration / 2)));
                ticks += duration;
            }
        } catch (Exception e) {

        }
        
        if (!(seq == null)) {
            numMelodies++;
            melGenerated.setEnabled(true);
            melGenerated.setText("Melody generated " + numMelodies);
        }

    }    

    
    
    private static int getNextNoteVal(double nex, double[] probs) {
        // Get a note between C below middle C (48) and B below middle C (59)
        int retval = 0;
        double currval = 0.0;
        int i = 0;
        while (currval < nex) {
            currval += probs[i];
            i++;
        }
        return (i - 1) + 48;
    }

    private static int getNextDuration(int quarval, double val) {
        // Probability of a quarter note = 4/7
        // Probability of an eighth note = 2/7
        // Probability of a sixteenth note = 1/7
        if (val > (4.0 / 7.0)) {
            // Quarter note
            return quarval;
        }
        if (val > (1.0 / 7.0)) {
            // Eighth note
            return quarval / 2;
        }
        return quarval / 4;
    }

    public static MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
        MidiEvent event = null;
        try {
            ShortMessage a = new ShortMessage();
            a.setMessage(comd, chan, one, two);
            //表示在tick拍启动a这个Message
            event = new MidiEvent(a, tick);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return event;
    }

    private static void addFrameComponents(Container cont) {
        cont.setLayout(new BoxLayout(cont, BoxLayout.PAGE_AXIS));
        JPanel ntpan = createNoteEntryPanel();
        cont.add(ntpan);
        JPanel adjPanel = createAdjBtnPanel();
        cont.add(adjPanel);
        JPanel melpan = createMelodyPanel();
        cont.add(melpan);
    }

    /**
     * Create the GUI and show it. For thread safety, this method is invoked
     * from the event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        OldProbFrame frame = new OldProbFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addFrameComponents(frame.getContentPane());
        frame.pack();
        frame.setVisible(true);
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
