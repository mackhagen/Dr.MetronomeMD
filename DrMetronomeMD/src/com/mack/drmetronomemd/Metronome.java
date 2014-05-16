package com.mack.drmetronomemd;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import java.util.*;

public class Metronome {
	private final Context context;
	
	// Variables
	private double bpm; // Beats per minute
	private double new_bpm;
	private int tSig; // Time signature, beats per measure
	private int interval; // Interval between sounds in ms
	private double mainVolume = 1.0; // Double between 0.0 and 1.0
    
	private AudioGenerator audioGenerator = new AudioGenerator(8000);
	
	// Subdivision Objects
	public Subdivision whole;
	public Subdivision eighth;
	public Subdivision sixteenth;
	public Subdivision triplet;
	
	// Sounds
	public static final int FREQ_TICK = 600;
	public static final int FREQ_WHOLE = 900;
	public static final int FREQ_EIGHTH = 550;
	public static final int FREQ_SIXTEENTH = 520;
	public static final int FREQ_TRIPLET = 500;
	
	double[] tickS;
    double[] wholeS;
    double[] eighthS;
    double[] sixteenthS;
    double[] tripletS;
	
	// Default constructor
	public Metronome(final Context context) {
		audioGenerator.createPlayer();
		bpm = 100;
		new_bpm = 100;
		tSig = 4;
		whole = new Subdivision(1);
		eighth = new Subdivision(2, false);
		sixteenth = new Subdivision(4, false);
		triplet = new Subdivision(3, false);
		this.context = context;
	}
	// Constructor with bpm input
	public Metronome(final Context context, int b) {
		bpm = b;
		new_bpm = bpm;
		tSig = 4;
		whole = new Subdivision(1);
		eighth = new Subdivision(2, false);
		sixteenth = new Subdivision(4, false);
		triplet = new Subdivision(3, false);
		this.context = context;
	}
	// Constructor with bpm and time signature input
	public Metronome(final Context context, int b, int t) {
		bpm = b;
		new_bpm = bpm;
		tSig = t;
		whole = new Subdivision(1);
		eighth = new Subdivision(2, false);
		sixteenth = new Subdivision(4, false);
		triplet = new Subdivision(3, false);
		this.context = context;
	}
	
	public void initSounds() {
		calcInterval();
		
		// Sound Sample Arrays
	    tickS = audioGenerator.getSineWave(interval, 8000, FREQ_TICK);
	}
	
	public void calcInterval() {
        interval = (int) ((60.0 / bpm) * 8000.0);
    }
	
	public void play() {
		calcInterval();
		if (bpm != new_bpm) {
			bpm = new_bpm;
		}
		calcInterval();
        double[] sound = new double[(int) interval];
        int t = 0;
        for(int i = 0; i < sound.length / 2; i++) {
	        sound[i] = tickS[t];
	        t++;
        }
        audioGenerator.writeSound(sound);
}

	/*
	// Play methods
	public void playBeep() {
		playSound(beepID, mainVolume);
	}
	
	public void playWhole() {
		playSound(SOUND_WHOLE, whole.getSubVol());
	}
	
	public void playEighth() {
		playSound(SOUND_EIGHTH, eighth.getSubVol());
	}
	
	public void playSixteenth() {
		playSound(SOUND_SIXTEENTH, sixteenth.getSubVol());
	}
	
	public void playTriplet() {
		playSound(SOUND_TRIPLET, triplet.getSubVol());
	}
	*/
	
	// Getters and Setters
	public double get_bpm() {
		return bpm;
	}
	
	public void set_bpm(int b) {
		if (b < 30)
			new_bpm = 30;
		else if (b > 300)
			new_bpm = 300;
		else
			new_bpm = b;
	}
	
	public int get_tsig() {
		return tSig;
	}
	
	public void set_tsig(int t){
		if (t < 1 || t > 12)
			tSig = 1;
		else
			tSig = t;
	}
	
	public long get_interval() {
		return interval / 8000;
	}
	
	public double get_vol() {
		return mainVolume;
	}
	
	public void set_vol(double vol) {
		mainVolume = vol;
	}
}
