package quangnq.co.languagefunny.common;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

import java.io.IOException;

/**
 * this class work as sound controller
 * Created by QuangNQ on 3/9/2018.
 */
public class SoundManager {
    
    /**
     * media player
     */
    private static MediaPlayer sMediaPlayer;
    
    /**
     * activity play sound
     */
    private Activity mActivity;
    
    /**
     * one second
     */
    private static final int ONE_SECOND = 1000;
    
    /***
     * get activity as parameter purpose to init MediaPlayer
     * @param activity
     */
    public SoundManager(Activity activity) {
        this.mActivity = activity;
    }
    
    /***
     * play music by id
     * @param id
     * @return duration + 1000ms
     */
    public int setPlay(int id) {
        if (sMediaPlayer != null && this.sMediaPlayer.isPlaying()) {
            sMediaPlayer.pause();
            sMediaPlayer.reset();
            sMediaPlayer = null;
        }
        sMediaPlayer = MediaPlayer.create(mActivity, id);
        this.play();
        // sound's length + 1000 milisecond
        return sMediaPlayer.getDuration() + ONE_SECOND;
    }
    
    /***
     * play music by id
     * @param path
     * @return duration + 1000ms
     */
    public int setPlay(String path) {
        if (sMediaPlayer != null && this.sMediaPlayer.isPlaying()) {
            sMediaPlayer.pause();
            sMediaPlayer.reset();
            sMediaPlayer = null;
        }
        Uri myUri = Uri.parse(path);
        sMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            sMediaPlayer.setDataSource(mActivity.getApplicationContext(), myUri);
            sMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.play();
        // sound's length + 1000 milisecond
        return sMediaPlayer.getDuration() + ONE_SECOND;
    }
    
    /***
     * check if diffirent sound is playling, it should stop before start other one
     */
    private void play() {
        sMediaPlayer.start();
    }
    
}
