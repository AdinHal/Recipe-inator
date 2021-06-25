package com.example.recipeinator.fragments;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.transition.Scene;
import androidx.transition.Transition;
import androidx.transition.TransitionInflater;
import androidx.transition.TransitionManager;

import com.example.recipeinator.R;
import com.example.recipeinator.util.AfterTextWatcher;

import java.util.Locale;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static android.content.Context.MODE_PRIVATE;

public class TimerFragment extends Fragment {
    private EditText mTimeInput;
    private TextView mCountDown;
    private Button mStartPauseBtn, mResetBtn;

    private CountDownTimer countDownTimer;
    private boolean isRunning;
    private long mStartTime, mTimeLeft, mEndTime;

    private final long defaultTime;

    public TimerFragment(long defaultTimeMinutes){
        this.defaultTime = defaultTimeMinutes * 1000 * 60;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTimeInput = view.findViewById(R.id.edit_text_input);
        mCountDown = view.findViewById(R.id.text_view_countdown);
        mStartPauseBtn = view.findViewById(R.id.button_start_pause);
        mResetBtn = view.findViewById(R.id.button_reset);
        mTimeInput.addTextChangedListener(new AfterTextWatcher(this::updateTime));

        mStartPauseBtn.setOnClickListener(v -> {
            if(isRunning){
                pauseTimer();
            }else{
                startTimer();
            }
        });

        mResetBtn.setOnClickListener(v -> resetTimer());
    }

    private void updateTime(Editable s){
        String input = mTimeInput.getText().toString();
        if (input.length() == 0) {
            Toast.makeText(getContext(), R.string.time_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        long inMillis = Long.parseLong(input) * 60000;
        if(inMillis == 0){
            Toast.makeText(getContext(), R.string.enter_positive, Toast.LENGTH_SHORT).show();
            return;
        }

        setTime(inMillis);
    }

    private void setTime(long milliseconds){
        mStartTime = milliseconds;
        resetTimer();
    }

    private void startTimer(){
        closeKeyboard();
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
            mResetBtn.setVisibility(View.INVISIBLE);
            //myImgView.setBackgroundResource(R.drawable.pause);
            mStartPauseBtn.setText(R.string.pause);
        }else {
            mTimeInput.setVisibility(View.VISIBLE);
            //myImgView.setBackgroundResource(R.drawable.start);
            mStartPauseBtn.setText(R.string.start);

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
        Activity activity = requireActivity();
        View view = activity.getCurrentFocus();
        if(view != null){
            // arbitrates interaction between applications and the current input method
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    @Override
    public void onStop(){
        super.onStop();

        SharedPreferences prefs = requireContext().getSharedPreferences("prefs", MODE_PRIVATE);
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
    public void onStart(){
        super.onStart();
        // A SharedPreferences object points to a file containing key-value pairs and provides
        // simple methods to read and write them. Each SharedPreferences file is managed by the
        // framework and can be private or shared
        SharedPreferences prefs = requireContext().getSharedPreferences("prefs",MODE_PRIVATE);

        mStartTime = prefs.getLong("startTimeInMillis", defaultTime);
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

    @Override
    public void onDestroy() {
        SharedPreferences prefs = requireContext().getSharedPreferences("prefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
        super.onDestroy();
    }
}
