package com.rus.minesweeper;

import android.os.Handler;
import android.widget.TextView;
import java.util.Locale;

public class Timer
{

    private Handler handler;
    private Runnable runnable;
    private int seconds = -1;
    private boolean isRunning = false;
    private TextView timerTextView;


    Timer(TextView timerTV){timerTextView = timerTV;}


    void setTimer()
    {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run()
            {
                if (isRunning)
                {
                    seconds++;
                    updateTimerTextView();
                    handler.postDelayed(this, 1000);
                }
            }
        };
    }

    void startTimer()
    {
        isRunning = true;
        handler.post(runnable);
    }

    void stopTimer()
    {
        isRunning = false;
        handler.removeCallbacks(runnable);
    }

    void updateTimerTextView()
    {
        int minutes = seconds / 60;
        int secs = seconds % 60;
        String time = String.format(Locale.getDefault(), "%02d:%02d", minutes, secs);
        timerTextView.setText(time);
    }

}