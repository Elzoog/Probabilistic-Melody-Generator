/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package probableisticmelody;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Hashtable;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author bridean
 */
public class SliderPanel extends JPanel {
    private static final int numTicks = 1000;
    private static final int compgap = 5;
    private final JSlider slider;
    private JCheckBox chBox;
    private JLabel infolab;
    
    // Do not call the default constructor
    private SliderPanel() {
        slider = null;
    }

    
    private void loadLeftPanel(String chStr, ImageIcon ico) {
        add(Box.createHorizontalStrut(compgap));
        // JCheckBox chBox;
        if (chStr != null) {
            chBox = new JCheckBox(chStr);
        } else {
            chBox = new JCheckBox("");
        }
        chBox.setSelected(true);
        chBox.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        chBox.addItemListener(new iListen(slider));
        add(chBox);
        JLabel imLabel = new JLabel();
        if (ico != null) {
            Image im = ico.getImage();
            int imhei = im.getHeight(this);
            int imwid = im.getWidth(null);
            Image newim = im.getScaledInstance(imwid / 11, imhei / 11, java.awt.Image.SCALE_SMOOTH);
            ImageIcon imcon = new ImageIcon(newim);

            imLabel.setIcon(imcon);
            imLabel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
            add(imLabel);
        }
        add(Box.createHorizontalStrut(compgap));

    }
    
    public SliderPanel(String ChLabelStr, double initper) {
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        int init = (int) (initper * numTicks);
        slider = new JSlider(JSlider.HORIZONTAL,
                0, numTicks, init);
        slider.setMajorTickSpacing(numTicks/10);
        slider.setMinorTickSpacing(numTicks/50);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        Hashtable hTable = new Hashtable();
        for (int i=0; i<=10; i++) {
            int perc = (numTicks/10) * i;
            String lStr = String.format("%d", (i * 10));
            hTable.put(new Integer(perc), new JLabel(lStr));
        }
        slider.setLabelTable(hTable);
        loadLeftPanel(ChLabelStr, null);
        add(slider);
        add(Box.createHorizontalStrut(compgap));
        String labStr = String.format("%1.1f%%", initper * 100);
        infolab = new JLabel(labStr);
        Dimension lastLabelSize = new Dimension(50,10);
        infolab.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        infolab.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        infolab.setMinimumSize(lastLabelSize);
        infolab.setPreferredSize(lastLabelSize);
        add(infolab);
        slChangeListener slListen = new slChangeListener(slider, infolab);
        slider.addChangeListener(slListen);
        add(Box.createHorizontalStrut(compgap));
    }

    public SliderPanel(ImageIcon ChImage, double initper) {
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        int init = (int) (initper * numTicks);
        slider = new JSlider(JSlider.HORIZONTAL,
                0, numTicks, init);
        slider.setMajorTickSpacing(numTicks/10);
        slider.setMinorTickSpacing(numTicks/50);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        Hashtable hTable = new Hashtable();
        for (int i=0; i<=10; i++) {
            int perc = (numTicks/10) * i;
            String lStr = String.format("%d", (i * 10));
            hTable.put(new Integer(perc), new JLabel(lStr));
        }
        slider.setLabelTable(hTable);
        loadLeftPanel(null, ChImage);
        add(slider);
        add(Box.createHorizontalStrut(compgap));
        String labStr = String.format("%1.1f%%", initper * 100);
        infolab = new JLabel(labStr);
        infolab.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        add(infolab);
        slChangeListener slListen = new slChangeListener(slider, infolab);
        slider.addChangeListener(slListen);
        add(Box.createHorizontalStrut(compgap));
    }

    
    public double getSliderValue() {
        int val = (int) slider.getValue();
        return ((double) val)/numTicks;
    }
    
    public void setSliderValue(double val) {
        int ival = (int) (val * numTicks);
        if (slider.isEnabled()) {
            slider.setValue(ival);
        }
    }
    
    public Dimension getCheckSize() {
        return chBox.getSize();
    }
    
    public void setCheckSize(Dimension dim) {
        chBox.setSize(dim);
        chBox.setMinimumSize(dim);
        chBox.setPreferredSize(dim);
        this.revalidate();
    }

    private static class slChangeListener implements ChangeListener {
        private final JSlider sl;
        private final JLabel lab;

        public slChangeListener(JSlider slvar, JLabel labvar) {
            sl = slvar;
            lab = labvar;
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            if (!sl.getValueIsAdjusting()) {
                int val = (int) sl.getValue();
                double dval = ((double) val/numTicks) * 100;
                String tmpStr = String.format("%1.1f%%", dval);
                lab.setText(tmpStr);
            }
        }
    }

    private static class iListen implements ItemListener {

        private final JSlider sl;
        private int oldValue;

        public iListen(JSlider var) {
            sl = var;
            oldValue = var.getValue();
        }

        public void itemStateChanged(ItemEvent e) {
            // static int oldValue = 0;
            if (e.getStateChange() == ItemEvent.SELECTED) {
                sl.setValue(oldValue);
                sl.setEnabled(true);
            } else {
                oldValue = sl.getValue();
                sl.setValue(0);
                sl.setEnabled(false);
            }
        }
    }
    
    
}
