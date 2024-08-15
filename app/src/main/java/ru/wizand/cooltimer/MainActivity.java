package ru.wizand.cooltimer;

import static androidx.media3.common.util.Log.d;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.media3.common.util.Log;



public class MainActivity extends AppCompatActivity {

    private SeekBar seekBar;
    private TextView textView;
    private Boolean isTimerOn;
    private Button button;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        isTimerOn = false;


        seekBar.setMax(600);
        seekBar.setProgress(60);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                long l = i * 1000;
                updateTimer(l);



            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }

    public void start(View view) {

        if (!isTimerOn){
            button.setText("Stop");
            seekBar.setEnabled(false);
            isTimerOn = true;

            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long l) {
                    updateTimer(l);
                }

                @Override
                public void onFinish() {

                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bell_sound);
                    mediaPlayer.start();
                    resetTimer();
                }
            };

            countDownTimer.start();

        } else {
            resetTimer();

        }




    }

    private void updateTimer(long l) {
        int minutes = (int)l/1000/60;
        int seconds = (int)l/1000 - (minutes * 60);

        String minutesString = "";
        String secondsString = "";

        if (minutes < 10) {
            minutesString = "0" + minutes;
        }
        else {
            minutesString = "" + minutes;
        }

        if (seconds < 10) {
            secondsString = "0" + seconds;
        }
        else {
            secondsString = "" + seconds;
        }

        textView.setText(minutesString + ":" + secondsString);

    }
    private void resetTimer() {
        button.setText("Start");
        countDownTimer.cancel();
        seekBar.setEnabled(true);
        seekBar.setProgress(60);
        isTimerOn = false;
        textView.setText("01:00");

    }
}