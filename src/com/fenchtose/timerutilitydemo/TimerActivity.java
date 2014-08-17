package com.fenchtose.timerutilitydemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

public class TimerActivity extends Activity implements NumberPicker.OnValueChangeListener {
	
	private NumberPicker numberPicker;
	private Button startButton;
	private Button pauseButton;
	private TimerUtility timer;
	
	private int state = 0;
	private static final int STATE_OFF = 0;
	private static final int STATE_RUNNING = 1;
	private static final int STATE_PAUSED = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timer);
		
		numberPicker = (NumberPicker)findViewById(R.id.numberPicker1);
		numberPicker.setMaxValue(30);
		numberPicker.setMinValue(1);
		numberPicker.setWrapSelectorWheel(false);
		numberPicker.setOnValueChangedListener(this);
		
		startButton = (Button)findViewById(R.id.button_start);
		pauseButton = (Button)findViewById(R.id.button_pause);
		
		timer = new TimerUtility(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timer, menu);
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
	
	public void startClicked(View v) {
		if (state != STATE_OFF) {
			Toast.makeText(getApplicationContext(), "Timer is running", Toast.LENGTH_SHORT).show();
			return;
		}
		
		state = STATE_RUNNING;
		int timeInterval = numberPicker.getValue();
		timer.setTime(timeInterval*1000);
		timer.startTimer();
		
		pauseButton.setVisibility(View.VISIBLE);
		
		Toast.makeText(getApplicationContext(), "Timer started for " + String.valueOf(timeInterval) + " seconds", Toast.LENGTH_SHORT).show();
	}
	
	public void pauseClicked(View v) {
		if (state == STATE_RUNNING) {
			state = STATE_PAUSED;
			timer.pauseTimer();
			pauseButton.setText("Resume");

		} else if (state == STATE_PAUSED) {
			state = STATE_RUNNING;
			timer.resumeTimer();
			pauseButton.setText("Pause");
		}
	}
	
	public void onTimerStop() {
		state = STATE_OFF;
		pauseButton.setVisibility(View.GONE);
		Toast.makeText(getApplicationContext(), "Timer stopped", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
		
	}

}
