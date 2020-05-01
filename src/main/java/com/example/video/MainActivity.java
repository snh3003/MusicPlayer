package com.example.video;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    AudioManager audioManager;

    public void play(View view){

        mediaPlayer.start();
    }
    public void pause(View view){

        mediaPlayer.pause();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioManager=(AudioManager) getSystemService(AUDIO_SERVICE);
        int maxVol=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVol=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        mediaPlayer= MediaPlayer.create(this, R.raw.interstellar);

        SeekBar volumeControl = (SeekBar) findViewById(R.id.volumeSeekBar);
        volumeControl.setMax(maxVol);
        volumeControl.setProgress(currentVol);

        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.i("Seekbar changed", Integer.toString(progress));
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        final SeekBar playSeekBar = (SeekBar) findViewById(R.id.seekBar2);
        playSeekBar.setMax(mediaPlayer.getDuration());
        playSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                Log.i("SeekBar Changed", Integer.toString(i));
                mediaPlayer.seekTo(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();
            }


            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.start();
            }
        });
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                playSeekBar.setProgress(mediaPlayer.getCurrentPosition());
            }
        },0,300);
       // VideoView videoView = (VideoView)findViewById(R.id.videoView2);

//        videoView.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.howdragon);
//        MediaController mediaController= new MediaController(this);
//        mediaController.setAnchorView(videoView);
//        videoView.setMediaController(mediaController);
//        videoView.start();

    }
}
