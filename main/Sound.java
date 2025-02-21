// CREDITS TO: RyiSnow for the checkVolume() code implementation

package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

// This class manages sound effects
public class Sound {
    Clip clip; // Represents a sound clip that can be played
    URL soundURL[] = new URL[30]; // we use this to store the file path of the sound files
    FloatControl fc; // Controls the volume of the sound
    int volumeScale = 3; // Initial volume scale
    float volume; // Variable to store the calculated volume level

    // Constructor: Initialize the sound file URLs for different sound effects
    public Sound() {
        soundURL[0] = getClass().getResource("/sound/sample.wav");
        soundURL[1] = getClass().getResource("/sound/coin.wav");
        soundURL[2] = getClass().getResource("/sound/powerup.wav");
        soundURL[3] = getClass().getResource("/sound/unlock.wav");
        soundURL[4] = getClass().getResource("/sound/fanfare.wav");
        soundURL[5] = getClass().getResource("/sound/gameover.wav");
        soundURL[6] = getClass().getResource("/sound/cursor.wav");
        soundURL[7] = getClass().getResource("/sound/pistol.wav");
        soundURL[8] = getClass().getResource("/sound/shotgun.wav");
        soundURL[9] = getClass().getResource("/sound/m4.wav");
        soundURL[10] = getClass().getResource("/sound/minigun.wav");
    }

    // Set the sound file for a particular sound effect
    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip(); // Obtain a new audio clip
            clip.open(ais); // Open the audio input stream
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            // FloatControl accepts numbers between minus 80 to 6

            checkVolume(); // Adjust volume based on the volume scale
        } catch (Exception e) {
            // Handle any exceptions that might occur during audio setup
        }
    }

    // Start playing the sound clip
    public void play() {
        clip.start();
    }

    // Loop the sound continuously
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    // Stop playing the sound clip
    public void stop() {
        clip.stop();
    }

    // Adjust the volume based on the specified volume scale
    public void checkVolume() {
        switch (volumeScale) {
            case 0:
                volume = -80f;
                break;

            case 1:
                volume = -20f;
                break;

            case 2:
                volume = -12f;
                break;

            case 3:
                volume = -5f;
                break;

            case 4:
                volume = 1f;
                break;

            case 5:
                volume = 6f;
                break;
        }

        fc.setValue(volume); // Set the volume using the FloatControl
    }
}
