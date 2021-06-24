package com.example.recipeinator.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipeinator.R;

import java.util.Locale;

public class TimerActivity extends AppCompatActivity {
    private EditText mTimeInput;
    private TextView mCountDown;
    private Button mSetBtn, mStartPauseBtn, mResetBtn;

    private CountDownTimer countDownTimer;
    private boolean isRunning;
    private long mStartTime, mTimeLeft, mEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        mTimeInput = findViewById(R.id.edit_text_input);
        mCountDown = findViewById(R.id.text_view_countdown);
        mSetBtn = findViewById(R.id.button_set);
        mStartPauseBtn = findViewById(R.id.button_start_pause);
        mResetBtn = findViewById(R.id.button_reset);

        mSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = mTimeInput.getText().toString();
                if (input.length() == 0) {
                    Toast.makeText(TimerActivity.this,"Time can't be empty",Toast.LENGTH_SHORT).show();
                    return;
                }

                long inMillis = Long.parseLong(input) * 60000;
                if(inMillis == 0){
                    Toast.makeText(TimerActivity.this,"Enter a positive number",Toast.LENGTH_SHORT).show();
                    return;
                }

                setTime(inMillis);
                mTimeInput.setText("");
            }
        });

        mStartPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isRunning){
                    pauseTimer();
                }else{
                    startTimer();
                }
            }
        });

        mResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });
    }

    private void setTime(long milliseconds){
        mStartTime = milliseconds;
        resetTimer();
        closeKeyboard();
    }

    private void startTimer(){
        mEndTime = System.currentTimeMillis()+mTimeLeft;

        countDownTimer = new CountDownTimer(mTimeLeft,1000){

            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeft = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                isRunning = false;
                updateWatchInterface();
            }
        }.start();

        isRunning = true;
        updateWatchInterface();
    }

    private void pauseTimer(){
        countDownTimer.cancel();
        isRunning = false;
        updateWatchInterface();
    }

    private void resetTimer(){
        mTimeLeft = mStartTime;
        updateCountDownText();
        updateWatchInterface();
    }

    private void updateCountDownText(){
        int hours = (int)(mTimeLeft/1000)/3600;
        int minutes = (int)(mTimeLeft/1000)%3600/60;
        int seconds = (int)(mTimeLeft/1000)%60;

        String timeLeftFormat;

        if(hours>0){
            timeLeftFormat = String.format(Locale.getDefault(),"%d:%02d:%02d",hours,minutes,seconds);
        }else{
            timeLeftFormat = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        }

        mCountDown.setText(timeLeftFormat);
    }

    private void updateWatchInterface(){
        if(isRunning){
            mTimeInput.setVisibility(View.INVISIBLE);
            mSetBtn.setVisibility(View.INVISIBLE);
            mResetBtn.setVisibility(View.INVISIBLE);
            //myImgView.setBackgroundResource(R.drawable.pause);
            mStartPauseBtn.setText("Pause");
        }else {
            mTimeInput.setVisibility(View.VISIBLE);
            mSetBtn.setVisibility(View.VISIBLE);
            //myImgView.setBackgroundResource(R.drawable.start);
            mStartPauseBtn.setText("Start");

            if (mTimeLeft < 1000) {
                mStartPauseBtn.setVisibility(View.INVISIBLE);
            } else {
                mStartPauseBtn.setVisibility(View.VISIBLE);
            }
            if (mTimeLeft < mStartTime) {
                mResetBtn.setVisibility(View.VISIBLE);
            } else {
                mResetBtn.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        if(view != null){
            // arbitrates interaction between applications and the current input method
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    @Override
    protected void onStop(){
        super.onStop();

        SharedPreferences prefs = getSharedPreferences("prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong("startTimeInMillis",mStartTime);
        editor.putLong("millisLeft",mTimeLeft);
        editor.putBoolean("timeRunning",isRunning);
        editor.putLong("endTime",mEndTime);

        editor.apply();

        if(countDownTimer!=null){
            countDownTimer.cancel();
        }
    }


    @Override
    protected void onStart(){
        super.onStart();
        // A SharedPreferences object points to a file containing key-value pairs and provides
        // simple methods to read and write them. Each SharedPreferences file is managed by the
        // framework and can be private or shared
        SharedPreferences prefs = getSharedPreferences("prefs",MODE_PRIVATE);

        mStartTime = prefs.getLong("startTimeInMillis",600000);
        mTimeLeft = prefs.getLong("millisLeft",mStartTime);
        isRunning = prefs.getBoolean("timerRunning",false);

        updateCountDownText();
        updateWatchInterface();

        if(isRunning){
            mEndTime = prefs.getLong("endTime",0);
            mTimeLeft = mEndTime - System.currentTimeMillis();

            if(mTimeLeft < 0){
                mTimeLeft = 0;
                isRunning = false;
                updateCountDownText();
                updateWatchInterface();
            }else{
                startTimer();
            }
        }
    }
}
