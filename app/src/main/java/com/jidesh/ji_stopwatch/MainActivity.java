package com.jidesh.ji_stopwatch;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;



public class MainActivity extends AppCompatActivity {

    TextView displaytime;
    Button start,pause,reset;
    LinearLayout actionlayout;
    Boolean waspause;
    Handler timehandler;
    Runnable timerunnable;
    int seconds;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        waspause = false;

        seconds = 0;

        timehandler = new Handler();
        //connect ui with button and text
        displaytime = findViewById(R.id.time_display);
        start       = findViewById(R.id.start);
        pause       = findViewById(R.id.pause);
        reset       = findViewById(R.id.reset);
        actionlayout= findViewById(R.id.action_btn);

        //Listner for button
        start.setOnClickListener(v -> {
            start.setVisibility(View.GONE);
            actionlayout.setVisibility(View.VISIBLE);
            runTimer();

        });

        pause.setOnClickListener(v -> {

            if(waspause)
            {
                // in pause
                waspause = false;
                pause.setText("Pause");
                runTimer();
            }
            else
            {
                // in start
                waspause = true;
                pause.setText("Resume");
                timehandler.removeCallbacks(timerunnable);

            }

        });

        reset.setOnClickListener(v -> {
            actionlayout.setVisibility(View.GONE);
            start.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(),"Reset",Toast.LENGTH_SHORT).show();

            timehandler.removeCallbacks(timerunnable);
            seconds = 0;
            displaytime.setText( String.format(Locale.getDefault(), "%02d:%02d:%02d", 0, 0, 0));
            waspause = false;
            pause.setText("Pause");

        });

    }

    void runTimer()
    {

        timerunnable = new Runnable() {
            int mill,minutes,secs;
            @Override

            public void run()
            {
                mill = seconds%60;  //milliseconds
                secs = seconds /60; //seconds
                minutes = secs / 60; // minutes
                if(secs > 60)
                {
                    secs = secs%60;
                }

                String time = String.format(Locale.getDefault(), "%02d:%02d:%02d",  minutes, secs,mill);
                displaytime.setText(time);

                seconds++;
                timehandler.removeCallbacks(this);
                timehandler.postDelayed(this,1);

            }
        };

        timehandler.post(timerunnable);

    }
}