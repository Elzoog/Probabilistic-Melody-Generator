# Probabilistic-Melody-Generator
A java program to generate melodies according to the frequency of notes.

It is intended to experiment with the hypothesis stated in twelve tone music that tonal music is such that certain notes are more likely than others.   For example, if C and G is more likely than D, then perhaps the song is in the key of C.   To test this, I have created a GUI so that the probability of each note occurance can be entered.    The default probabilities are based on my best guess as to the probability of notes in the key of C major based on the following:

1)  Take a Fibinacci sequence until you start to get large numbers 1, 1, 2, 3, 5, ... 1346269, 2178309, ...
2)  Take the next 12 numbers 1346269, 2178309, 5702887, 9227465, 14930352, 24157817, 39088169, 63245986, 
102334155, 165580141, 267914296, 433494437
3)  Add them up (1129200283) then divide that into each one to get a probability distribution 
0.0011922322, 0.0019290723, ..., 0.3838950835
4)  Assign those probablities so that if C is the most likely it will get 0.3839 probability, the second most likely, G will get 0.2368, and so forth.   The actual numbers in the program were computed with much higher Fibinacci numbers than the ones used in this README file.

If later on I find a much better probability distribution for C major I will use it as the default instead.  

The program is set up so that if you want to enter numbers that don't all add up to 1, the program will automatically normalize it.   For example, if you set C = 12, C-Sharp = 11, D = 10, D-Sharp = 9, ... B = 1, it will automatically add the values up (78) and divide each one to get a probability distribution.  In which case, C will have a 15.38% probability of occuring, C-Sharp will have a 14.1% probability of occuring, and so forth down to B having a probability of 1.28% chance of occuring.

Obviously, if you want to set it up for a particular scale, for example D major, you can set the probability of the notes not occuring in D major to 0.   Setting everything else to 1 you would get C = 0, C-Sharp = 1, D = 1, D-Sharp = 0, and so forth.

Currently, the program will generate notes between C in the bass clef, to B just below middle C.   

The rhythm is randomly generated so that a quarter note has a 4/7 chance of occuring, eighth note has a 2/7 chance of occuring, and a sixteenth note has a 1/7 chance of occuring.

I also added the ability to save the melody as a MIDI file so that it can be used with other programs.

Improvements I am considering

1)  Have the user be able to specify a different range besides C in the bass clef to B below middle C
2)  Improve the GUI
3)  Add the possibility of specifying the probabilities in the following way:   If a note is C, the probability the next note will be C = X, the probability the next note will be C-Sharp is X, and so forth.  Assign probabilities to all the other notes.  That way you can for example, make repeated notes impossible, make it likely that a B will resolve to C, and so forth.

Although I currently think the program is useful as is, I will continue to work on improving it.

Please send any comments to

Brian M. Dean
elzoog4180 at yahoo dot com
