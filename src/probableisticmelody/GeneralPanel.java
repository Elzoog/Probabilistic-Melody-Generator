/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package probableisticmelody;

import java.awt.BorderLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author bridean
 */
public class GeneralPanel extends JPanel {
    private NoteGridPanel ntPanel;
    private NoteMap nm;
    
    public GeneralPanel() {
        setLayout(new BorderLayout());
        String msgStr = "Generate a melody based on the general probabilities of the notes that occur";
        JLabel mainLabel = new JLabel(msgStr);
        mainLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(mainLabel, BorderLayout.PAGE_START);
        add(Box.createVerticalStrut(10));
        ntPanel = new NoteGridPanel();
        ntPanel.revalidate();
        // ntPanel.resizeChecks();
        add(ntPanel, BorderLayout.CENTER);
        GeneralMelodyPanel genMelPanel = new GeneralMelodyPanel();
        add(genMelPanel, BorderLayout.LINE_END);
        setVisible(true);
    }
    
    public void resize() {
        ntPanel.resizeChecks();
    }
}
