package com.mack.drmetronomemd;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.media.SoundPool;
import android.os.Handler;

public class MetRunnable extends Activity {
	Context context;
	private Handler handler = new Handler();
	Metronome met;
	
	Runnable runnable = new Runnable() {
		public void run() {
			//met.playBeep();
			handler.postDelayed(this, met.get_interval());
		}
	};
	
	public MetRunnable(Context context, Metronome met) {
		this.context = context;
		this.met = met;
	}
	
	// Call to pause
	public void onPause() {
		handler.removeCallbacks(runnable);
	}
	// Call to resume
	public void onResume() {
		handler.postDelayed(runnable, met.get_interval());
		super.onResume();
	}
}

/*
// SoundPool
private SoundPool soundPool;
private HashMap<Integer, Integer> soundPoolMap;

@SuppressLint("UseSparseArrays")
public void initSounds() {
	soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
	soundPoolMap = new HashMap<Integer, Integer>();
	soundPoolMap.put(SOUND_BEEP, soundPool.load(context, R.raw.beep, 1));
	
	// Get duration of each sound
	MediaPlayer mp = MediaPlayer.create(context, R.raw.beep);
	beepLen = mp.getDuration();
	
	// Set ID of each sound to variable
	beepID = soundPoolMap.get(SOUND_BEEP);
	
	// Calculate initial time interval
	double t = (60000.0 / ((double) bpm));
	interval = (long) (t);
}

public void playSound(int ID, double vol) {
	float volume = (float) vol;
	if (bpm != new_bpm) {
		double t = (60000.0 / ((double) new_bpm));
		interval = (long) (t);
		bpm = new_bpm;
	}
	//Toast.makeText(context, String.valueOf(interval), Toast.LENGTH_SHORT).show();
	soundPool.play(ID, volume, volume, 1, 0, 1f);
}
*/
