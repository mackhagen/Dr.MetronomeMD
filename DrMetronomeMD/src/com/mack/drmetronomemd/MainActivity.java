package com.mack.drmetronomemd;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends ActionBarActivity {
	Metronome met;
	
	// Handler and Runnable for main metronome function
	Handler handler = new Handler();
	Runnable runnable = new Runnable() {
		public void run() {
			met.play();
			handler.postDelayed(this, met.get_interval());
		}
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        
        // Initialize metronome and sounds
        met = new Metronome(this);
        met.initSounds();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
    	
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    // Toggle metronome on and off
	public void toggleMetronome(View view) throws InterruptedException {
    	boolean on = ((ToggleButton) view).isChecked();
    	if(on) {
    		runnable.run();
    	}
    	else {
    		handler.removeCallbacks(runnable);
    	}
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
    
    // Stop, Resume, Destroy sound runnable
    @Override
 	public void onPause() {
 		handler.removeCallbacks(runnable);
 		super.onPause();
 	}
 	@Override
    public void onResume() {
 		handler.postDelayed(runnable, met.get_interval());
 		super.onResume();
 	}
 	@Override
 	public void onDestroy() {
 		handler.removeCallbacks(runnable);
 		super.onDestroy();
 	}
}
