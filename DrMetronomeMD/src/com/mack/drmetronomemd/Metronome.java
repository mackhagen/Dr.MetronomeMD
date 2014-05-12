package com.mack.drmetronomemd;

import android.media.AudioManager;
import android.media.SoundPool;
import android.content.Context;

import java.util.*;

public class Metronome {
	// Variables
	private int bpm; // Beats per minute
	private int tSig; // Time signature, beats per measure
	private long interval; // Interval between sounds in ms
	private double mainVolume = 1.0; // Double between 0.0 and 1.0
	private final Context context;
	
	// Subdivision Objects
	public Subdivision whole;
	public Subdivision eighth;
	public Subdivision sixteenth;
	public Subdivision triplet;
	
	// Sounds
	public static final int SOUND_BEEP = 1;
	public static final int SOUND_WHOLE = 2;
	public static final int SOUND_EIGHTH = 3;
	public static final int SOUND_SIXTEENTH = 4;
	public static final int SOUND_TRIPLET = 5;
	
	// SoundPool
	private SoundPool soundPool;
	private HashMap<Integer, Integer> soundPoolMap;
	
	// Default constructor
	public Metronome(final Context context) {
		bpm = 100;
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
		tSig = t;
		whole = new Subdivision(1);
		eighth = new Subdivision(2, false);
		sixteenth = new Subdivision(4, false);
		triplet = new Subdivision(3, false);
		this.context = context;
	}
	
	public void initSounds() {
		soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
		soundPoolMap = new HashMap<Integer, Integer>();
		soundPoolMap.put(SOUND_BEEP, soundPool.load(context, R.raw.beep, 1));
	}
	
	public void playSound(int sound, double vol) {
		float volume;
		if (vol > 1.0)
			volume = (float) 1.0;
		else if (vol < 0.0)
			volume = (float) 0.0;
		else
			volume = (float) vol;
		soundPool.play(soundPoolMap.get(sound), volume, volume, 1, 0, 1f);
	}
	
	// Play methods
	public void play() {
		double t = (60.0 / ((double) bpm)) - 0.1;
    	interval = (long) (t * 1000);
		playSound(SOUND_BEEP, mainVolume);
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
	
	public int get_bpm() {
		return bpm;
	}
	
	public void set_bpm(int b) {
		bpm = b;
	}
	
	public int get_tsig() {
		return tSig;
	}
	
	// Set time signature only if 0 < ts < 12
	public void set_tsig(int t){
		if (t < 0 || t > 12) {
			tSig = 4;
		}
		else {
			tSig = t;
		}
	}
	
	public long get_interval() {
		return interval;
	}
	
	public double get_vol() {
		return mainVolume;
	}
	
	public void set_vol(double vol) {
		mainVolume = vol;
	}
}
