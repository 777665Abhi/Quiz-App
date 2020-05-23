package au_edu_jcu_cp3406.quizapp.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

public class AudioUtil
{
    private static boolean isPlayingAudio;
    private static MediaPlayer mediaPlayer;
    private static SoundPool soundPool;

    public static void playAudio(Context c, int id) {
        mediaPlayer = MediaPlayer.create(c, id);
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 50);
        if (!mediaPlayer.isPlaying()) {
            isPlayingAudio = true;
            mediaPlayer.start();
            mediaPlayer.setLooping(true);
        }
    }

    public static void stopPlayAudio(Context c) {
      if(mediaPlayer!=null)
        if(mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    }
