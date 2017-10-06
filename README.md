# Probabilistic-Melody-Generator
A java program to generate melodies according to the frequency of notes.

This is currently a version which I feel satisfied with also providing the source code.   It will generate melodies based on the probabilities that certain notes will occur which the user enters using sliding bars.   Things to do:

1)  Find out why the right panel puts a lot of space between the Generate Melody button and the information panels.

2)  Add another tab to generate melodies based on what note probably follows a given note.  Say for example, the note played is C, the user should then be able to enter in the probability that the next note will be C-Sharp (or any other note).  Much of the code for this is sort of included but I have not implemented this functionality yet.

3)  Write up documentation on how to use the program.

4)  Write up documentation on the source code (it has become more complicated than I initially expected).

Compared with the previous version, different sorts of functionality have been seperated into different classes.

If you hit "Save Melody", the program will save it as a MIDI file which can then be modified or read by other programs.

The main program can be run using Oracle's java runtime environment as follows:

java -jar ProbableisticMelody.jar

I am also providing the source code in two different ways:

1)  As individual files on this web site.
2)  As a zip file, ProbableisticMelodySource.zip

Keep in mind that I created it using the Netbeans 8.2 IDE   I can't speak to how easy, or difficult, it would be to edit it using Eclipse.

Please send any comments to

Brian M. Dean
elzoog4180 at yahoo dot com
