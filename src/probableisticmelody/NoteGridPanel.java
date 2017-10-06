/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package probableisticmelody;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author bridean
 */
public class NoteGridPanel extends JPanel {
    private SliderPanel[] spanels = new SliderPanel[12];
    private JButton normButton;

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
    
    private void normButtonActionPerformed(ActionEvent evt) {
        double[] probs = new double[12];
        for (int i=0; i < 12; i++) {
            probs[i] = spanels[i].getSliderValue();
        }
        ProbabilityVector probvec = new ProbabilityVector(probs);
        for (int i=0; i < 12; i++) {
            double tmpval = probvec.getValue(i);
            spanels[i].setSliderValue(tmpval);
        }
        NoteMap ntmap = new NoteMap();
        for (int i=0; i<12; i++) {
            Note tmpNote = new Note(i);
            ntmap.addProbability(tmpNote, spanels[i].getSliderValue());
        }
        ntmap.lock();
        MelodiesAndRhythm.setNoteMap(ntmap);
        
    }
    
    private JPanel getButtonPanel() {
        normButton = new JButton("Normalize and Save");
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
    
    public NoteGridPanel() {
        NoteMap ntmap = new NoteMap();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(javax.swing.BorderFactory.createTitledBorder(
                null, "Enter the note probabilities", 
                javax.swing.border.TitledBorder.CENTER, 
                javax.swing.border.TitledBorder.DEFAULT_POSITION));
        for (int i = 0; i < 12; i++) {
            spanels[i] = new SliderPanel(Note.getValidNoteName(i), MajorProbabilities[i]);
            Note tmpnt = new Note(i);
            ntmap.addProbability(tmpnt, MajorProbabilities[i]);
            add(spanels[i]);
        }
        ntmap.lock();
        MelodiesAndRhythm.setNoteMap(ntmap);
        JPanel btPanel = getButtonPanel();
        add(btPanel);
    }
    
    public void resizeChecks() {
        Dimension maxdim = new Dimension(0, 0);
        double hei = maxdim.getHeight();
        double wid = maxdim.getWidth();
        double maxabs = Math.sqrt((hei * hei) + (wid * wid));
        for (int i = 0; i < 12; i++) {
            Dimension currdim = spanels[i].getCheckSize();
            hei = currdim.getHeight();
            wid = currdim.getWidth();
            double absval = Math.sqrt((hei * hei) + (wid * wid));
            if (absval > maxabs) {
                maxdim = currdim;
                maxabs = absval;
            }
        }
        for (int i = 0; i<12; i++) {
            spanels[i].setCheckSize(maxdim);
        }        
    }
    
    public NoteMap getNoteMap() {
        NoteMap nm = new NoteMap();
        for (int i=0; i<12; i++) {
            Note currNt = new Note(i);
            nm.addProbability(currNt, spanels[i].getSliderValue());
        }
        nm.lock();
        return nm;
    }
        
}
