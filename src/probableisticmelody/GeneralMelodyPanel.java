/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package probableisticmelody;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.*;

/**
 *
 * @author bridean
 */
public class GeneralMelodyPanel extends JPanel {

    private int howmany, lownote, highnote, whichmel = 0;
    private MidiSequence midseq = null;
    private JLabel[] infoFields = new JLabel[4];

    private void genMelodyAction(ActionEvent evt) {
        MelodyAttributesDialog meldia = new MelodyAttributesDialog();
        meldia.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        meldia.setVisible(true);
        super.update(this.getGraphics());
    }

    private JPanel infoPanel() {
        JPanel retpan = new JPanel();
        retpan.setLayout(new BoxLayout(retpan, BoxLayout.LINE_AXIS));
        JLabel howlab = new JLabel("Num: ");
        retpan.add(howlab);
        retpan.add(infoFields[0]);
        retpan.add(Box.createHorizontalStrut(10));
        JLabel lowlab = new JLabel("Low: ");
        retpan.add(lowlab);
        retpan.add(infoFields[1]);
        retpan.add(Box.createHorizontalStrut(10));
        JLabel highlab = new JLabel("High: ");
        retpan.add(highlab);
        retpan.add(infoFields[2]);
        retpan.setAlignmentX(Component.LEFT_ALIGNMENT);
        return retpan;
    }
    
    private JPanel whichMelodyPanel() {
        JPanel retpan = new JPanel();
        retpan.setLayout(new BoxLayout(retpan, BoxLayout.LINE_AXIS));
        JLabel howlab = new JLabel("Melody: ");
        retpan.add(howlab);
        retpan.add(infoFields[3]);
        retpan.setAlignmentX(Component.LEFT_ALIGNMENT);
        return retpan;
    }

    public GeneralMelodyPanel() {
        for (int i = 0; i < 4; i++) {
            infoFields[i] = new JLabel("0");
        }
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(javax.swing.BorderFactory.createTitledBorder(
                null, "Generate Melodies",
                javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.DEFAULT_POSITION));
        JButton genMelody = new JButton("Generate Melody");
        genMelody.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                genMelodyAction(evt);
            }
        });
        genMelody.setAlignmentX(Component.LEFT_ALIGNMENT);
        int maxwid = 200;
        genMelody.setPreferredSize(new Dimension(maxwid, 30));
        genMelody.setMinimumSize(new Dimension(maxwid, 30));
        add(genMelody);
        JPanel infPan = infoPanel();
        add(infPan);
        JPanel wMelPan = whichMelodyPanel();
        add(wMelPan);
        JButton playMelBut = new JButton("Play Melody");
        playMelBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (midseq == null) {
                    JFrame tmpFrame = new JFrame();
                    JOptionPane.showMessageDialog(tmpFrame, 
                            "You need to generate a melody first.");
                    return;
                }
                midseq.playSequence();
            }
        });
        add(playMelBut);
        JButton saveMelBut = new JButton("Save Melody");
        saveMelBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (midseq == null) {
                    JFrame tmpFrame = new JFrame();
                    JOptionPane.showMessageDialog(tmpFrame, 
                            "You need to generate a melody first.");
                    return;
                }                
                midseq.saveSequence();
            }
        });
        add(saveMelBut);
        add(Box.createVerticalGlue());
        Dimension maxdim = new Dimension(maxwid, 500);
        setPreferredSize(maxdim);
        setMaximumSize(maxdim);
    }

    private class MelodyAttributesDialog extends JDialog {

        private String[] noteNames = new String[12];
        private JComboBox lowCombo, highCombo;
        private JTextField numtxt, lowtxt, hightxt;
        private JLabel messageLab;

        private JPanel getGridInfoPanel() {
            Dimension bigsize = new Dimension(80, 20);
            JPanel retpan = new JPanel();
            retpan.setLayout(new GridLayout(2, 4, 10, 10));
            JLabel lowntlab = new JLabel("Lowest note:");
            retpan.add(lowntlab);
            lowCombo = new JComboBox(noteNames);
            lowCombo.setMaximumSize(bigsize);
            retpan.add(lowCombo);
            JLabel octlab1 = new JLabel("Octave:");
            retpan.add(octlab1);
            lowtxt = new JTextField("3");
            lowtxt.setMaximumSize(bigsize);
            retpan.add(lowtxt);
            JLabel highntlab = new JLabel("Highest note:");
            retpan.add(highntlab);
            highCombo = new JComboBox(noteNames);
            highCombo.setMaximumSize(bigsize);
            retpan.add(highCombo);
            JLabel octlab2 = new JLabel("Octave:");
            retpan.add(octlab2);
            hightxt = new JTextField("4");
            hightxt.setMaximumSize(bigsize);
            retpan.add(hightxt);
            return retpan;
        }

        public JPanel getNumNotesPanel() {
            JPanel retpan = new JPanel();
            retpan.setLayout(new BoxLayout(retpan, BoxLayout.LINE_AXIS));
            JLabel numlab = new JLabel("How many notes are in the melody?  (5 - 1000)");
            retpan.add(numlab);
            numtxt = new JTextField("20");
            retpan.add(Box.createHorizontalStrut(10));
            retpan.add(numtxt);
            return retpan;
        }

        public JPanel getButtonPanel() {
            JPanel retpan = new JPanel();
            retpan.setLayout(new BoxLayout(retpan, BoxLayout.LINE_AXIS));
            retpan.add(Box.createHorizontalGlue());
            JButton okButton = new JButton("Ok");
            okButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    okButtonActionPerformed(evt);
                    repaint();
                    infoFields[0].setText("" + howmany);
                    infoFields[1].setText("" + lownote);
                    infoFields[2].setText("" + highnote);
                    midseq = null;
                    NoteMap ntMap = MelodiesAndRhythm.getNoteMap();
                    if (!ntMap.islocked()) {
                        System.out.println("You need to set the note map.");
                        return;
                    }
                    RhythmMap rhyMap = MelodiesAndRhythm.getRhythmMap();
                    if (!rhyMap.isLocked()) {
                        System.out.println("You need to set the rhythm map.");
                        return;
                    }
                    midseq = new MidiSequence(howmany, lownote, highnote, ntMap, rhyMap);
                    MelodiesAndRhythm.addMelody(midseq);
                    whichmel = MelodiesAndRhythm.numMelodies();                    
                    infoFields[3].setText("" + whichmel);
                }
            });
            retpan.add(okButton);
            retpan.add(Box.createHorizontalGlue());
            return retpan;
        }

        private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {
            boolean testvalid = true;
            String msgStr = "";
            try {
                int tmpint = Integer.parseInt(numtxt.getText());
                if ((tmpint < 5) || (tmpint > 1000)) {
                    testvalid = false;
                    msgStr = "Number of notes needs to be between 5 and 1000";
                } else {
                    howmany = tmpint;
                }
                tmpint = Integer.parseInt(lowtxt.getText());
                if ((tmpint < 0) || (tmpint > 8)) {
                    testvalid = false;
                    msgStr = "Octave value needs to be between 0 and 8";
                } else {
                    String ntName = (String) lowCombo.getSelectedItem();
                    Note lowNote = new Note(ntName, tmpint);
                    lownote = lowNote.getNoteValue();
                }
                tmpint = Integer.parseInt(hightxt.getText());
                if ((tmpint < 0) || (tmpint > 8)) {
                    testvalid = false;
                    msgStr = "Octave value needs to be between 0 and 8";
                } else {
                    String ntName = (String) highCombo.getSelectedItem();
                    Note highNote = new Note(ntName, tmpint);
                    highnote = highNote.getNoteValue();
                }
            } catch (NumberFormatException ex) {

            }
            if (!testvalid) {
                messageLab.setText("Message: " + msgStr);
            } else {
                this.setVisible(false);
            }
        }

        public MelodyAttributesDialog() {
            setTitle("Enter in the melody attributes");
            setMinimumSize(new Dimension(400, 200));
            for (int i = 0; i < 12; i++) {
                noteNames[i] = Note.getValidNoteName(i);
            }
            setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
            JPanel numPanel = getNumNotesPanel();
            add(numPanel);
            JPanel grdPanel = getGridInfoPanel();
            add(grdPanel);
            JPanel btnPanel = getButtonPanel();
            add(Box.createVerticalStrut(10));
            add(btnPanel);
            add(Box.createVerticalStrut(10));
            JPanel messagePan = new JPanel();
            messagePan.setLayout(new BoxLayout(messagePan, BoxLayout.LINE_AXIS));
            messageLab = new JLabel("Message:");
            messageLab.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
            messageLab.setAlignmentX(Component.RIGHT_ALIGNMENT);
            messagePan.add(Box.createHorizontalStrut(10));
            messagePan.add(messageLab);
            messagePan.add(Box.createHorizontalGlue());
            add(messagePan);
            add(Box.createVerticalStrut(10));
            setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        }
    }

}
