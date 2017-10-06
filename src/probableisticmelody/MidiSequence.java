/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package probableisticmelody;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.sound.midi.*;
import javax.swing.JFileChooser;

/**
 *
 * @author bridean
 */
public class MidiSequence {

    // Range for midi notes
    private final int min;
    private final int max;
    // Number of notes in the sequence
    private final int numnotes;
    public static final int quartervalue = 26880;
    private Sequence seq = null;
    private String name;

    // Never call the empty constructor
    private MidiSequence() {
        min = 0;
        max = 0;
        numnotes = 0;
    }

    public MidiSequence(int num, int minval, int maxval,
            NoteMap nmap, RhythmMap rmap) {
        numnotes = num;
        min = minval;
        max = maxval;
        generateSequence(nmap, rmap);
    }

    private void generateSequence(NoteMap nmap, RhythmMap rmap) {
        // For convenience, let C = C below middle C  midi = 48
        Random rand = new Random();
        double total = 0.0;
        try {
            Sequencer player = MidiSystem.getSequencer();
            player.open();
            //节拍器
            seq = new Sequence(Sequence.PPQ, quartervalue);
            int res = seq.getResolution();
            Track track = seq.createTrack();

            int noteint = 0;
            int volume = 120;
            int duration = quartervalue;
            int ticks = 0;
            for (int i = 0; i < numnotes; i++) {
                noteint = nmap.getNextNote(min, max).getNoteValue();
                duration = rmap.getNextMidiRhythm();
                // Note on
                track.add(makeEvent(144, 1, noteint, volume, ticks));
                // Note off
                track.add(makeEvent(128, 1, noteint, volume, ticks + (duration / 2)));
                ticks += duration;
            }
        } catch (Exception e) {

        }
    }

    private static MidiEvent makeEvent(int comd, int chan, int one, int two, int tick) {
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
    
    public void setName(String var) {
        name = var;
    }
    
    public String getName() {
        return name;
    }
    
    public void playSequence() {
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

    public void saveSequence() {
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
    

}
