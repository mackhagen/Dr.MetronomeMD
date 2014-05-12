package com.mack.drmetronomemd;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;

public class MetRunnable extends Activity {
	Context context;
	private Handler handler = new Handler();
	Metronome met;
	
	Runnable runnable = new Runnable() {
		public void run() {
			met.play();
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
