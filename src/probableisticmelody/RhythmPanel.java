/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package probableisticmelody;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *
 * @author bridean
 */
public class RhythmPanel extends JPanel {
    ArrayList<SliderPanel> slpanels = new ArrayList<SliderPanel>();
    ArrayList<Rhythm> rhys = new ArrayList<Rhythm>();

    private JPanel getRhythmGrid() {
        JPanel retpan = new JPanel();
        retpan.setBorder(javax.swing.BorderFactory.createTitledBorder(
                null, "Get Rhythm Values",
                javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.DEFAULT_POSITION));
        retpan.setLayout(new BoxLayout(retpan, BoxLayout.PAGE_AXIS));
        ImageIcon imcon = new javax.swing.ImageIcon(
                RhythmPanel.class.getResource("NoteImages/WholeNote.png"));
        SliderPanel slpan = new SliderPanel(imcon, 0.0);
        Rhythm rhy = new Rhythm(4.0);
        slpanels.add(slpan);
        rhys.add(rhy);
        imcon = new javax.swing.ImageIcon(
                RhythmPanel.class.getResource("NoteImages/DottedHalfNote.png"));
        slpan = new SliderPanel(imcon, 0.0);
        slpanels.add(slpan);
        rhy = new Rhythm(3.0);
        rhys.add(rhy);
        imcon = new javax.swing.ImageIcon(
                RhythmPanel.class.getResource("NoteImages/HalfNote.png"));
        slpan = new SliderPanel(imcon, 0.0);
        slpanels.add(slpan);
        rhy = new Rhythm(2.0);
        rhys.add(rhy);        
        imcon = new javax.swing.ImageIcon(
                RhythmPanel.class.getResource("NoteImages/DottedQuarterNote.png"));
        slpan = new SliderPanel(imcon, 0.0);
        slpanels.add(slpan);
        rhy = new Rhythm(1.5);
        rhys.add(rhy);        
        imcon = new javax.swing.ImageIcon(
                RhythmPanel.class.getResource("NoteImages/QuarterNote.png"));
        slpan = new SliderPanel(imcon, (4.0 / 7.0));
        slpanels.add(slpan);
        rhy = new Rhythm(1.0);
        rhys.add(rhy);                
        imcon = new javax.swing.ImageIcon(
                RhythmPanel.class.getResource("NoteImages/DottedEighthNote.png"));
        slpan = new SliderPanel(imcon, 0.0);
        slpanels.add(slpan);
        rhy = new Rhythm(0.75);
        rhys.add(rhy);                
        imcon = new javax.swing.ImageIcon(
                RhythmPanel.class.getResource("NoteImages/EighthNote.png"));
        slpan = new SliderPanel(imcon, (2.0 / 7.0));
        slpanels.add(slpan);
        rhy = new Rhythm(0.5);
        rhys.add(rhy);                
        imcon = new javax.swing.ImageIcon(
                RhythmPanel.class.getResource("NoteImages/DottedSixteenthNote.png"));
        slpan = new SliderPanel(imcon, 0.0);
        slpanels.add(slpan);
        rhy = new Rhythm(0.375);
        rhys.add(rhy);                
        imcon = new javax.swing.ImageIcon(
                RhythmPanel.class.getResource("NoteImages/SixteenthNote.png"));
        slpan = new SliderPanel(imcon, (1.0 / 7.0));
        slpanels.add(slpan);
        rhy = new Rhythm(0.25);
        rhys.add(rhy);                        
        for (int i = 0; i < slpanels.size(); i++) {
            retpan.add(slpanels.get(i));
        }
        JPanel bpan = getButtonPanel();
        retpan.add(bpan);
        Dimension mindim = new Dimension(700, 500);
        retpan.setPreferredSize(mindim);
        retpan.setMinimumSize(mindim);
        return retpan;
    }
    
    private void normButtonActionPerformed(ActionEvent evt) {
        int size = slpanels.size();
        RhythmMap rhmap = new RhythmMap();
        double[] probs = new double[size];
        for (int i=0; i < size; i++) {
            Rhythm currRhy = rhys.get(i);
            double dprob = slpanels.get(i).getSliderValue();
            rhmap.addRhythm(currRhy, dprob);
        }
        rhmap.lock();
        ProbabilityVector pvec = rhmap.getVector();
        for (int i=0; i < size; i++) {
            double dprob = pvec.getValue(i);
            slpanels.get(i).setSliderValue(dprob);
        }
        MelodiesAndRhythm.setRhythmMap(rhmap);        
    }
    
    
    private JPanel getButtonPanel() {
        JButton normButton = new JButton("Normalize and Save");
        normButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normButtonActionPerformed(evt);
            }
        });
        JPanel bPanel = new JPanel();
        bPanel.setLayout(new BoxLayout(bPanel, BoxLayout.LINE_AXIS));
        bPanel.add(Box.createHorizontalGlue());
        bPanel.add(normButton);
        bPanel.add(Box.createHorizontalGlue());
        return bPanel;
    }
    

    public RhythmPanel() {
        RhythmMap rhmap = new RhythmMap();
        // Quarter note
        Rhythm tmprhy = new Rhythm(1.0);
        rhmap.addRhythm(tmprhy, (4.0 / 7.0));
        // Eighth note
        tmprhy = new Rhythm(0.5);
        rhmap.addRhythm(tmprhy, (2.0 / 7.0));
        // Sixteenth note
        tmprhy = new Rhythm(0.25);
        rhmap.addRhythm(tmprhy, (1.0 / 7.0));
        rhmap.lock();
        MelodiesAndRhythm.setRhythmMap(rhmap);
        JPanel rhyGrid = getRhythmGrid();
        add(rhyGrid, BorderLayout.CENTER);
    }

}
