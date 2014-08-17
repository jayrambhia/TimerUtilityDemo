package com.fenchtose.timerutilitydemo;

import android.content.Context;
import android.os.Handler;

public class TimerUtility {
    private int time_in_ms;
    private final Context context;
    private final TimerActivity timerAct;
    private Handler handler;
    private final Runnable runnable;
    private long startTime, pauseTime, elapsedTime, remainingTime, resumeTime;

    public TimerUtility(Context con) {
        context = con;
        timerAct = (TimerActivity)context;

        runnable = new Runnable() {
            @Override
            public void run() {
                timerAct.onTimerStop();
            }
        };

    }

    public void setTime(int timeInMS) {
        time_in_ms = timeInMS;
    }
    
    public void startTimer() {
        startTime = System.currentTimeMillis();
        resumeTime = startTime;
        elapsedTime = 0;
        remainingTime = time_in_ms;

        handler = new Handler();
        handler.postDelayed(runnable, time_in_ms);
    }

    public void pauseTimer() {
        
        pauseTime = System.currentTimeMillis();
        elapsedTime = pauseTime - resumeTime + elapsedTime;
        remainingTime = time_in_ms - elapsedTime;
        handler.removeCallbacks(runnable);
    }

    public void resumeTimer() {
        resumeTime = System.currentTimeMillis();
        handler.postDelayed(runnable, remainingTime);
    }
}
