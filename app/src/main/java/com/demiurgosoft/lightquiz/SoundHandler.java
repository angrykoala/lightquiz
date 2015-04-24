package com.demiurgosoft.lightquiz;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by demiurgosoft - 4/10/15
 */
public class SoundHandler {
    private MediaPlayer correctAnswer;
    //private static MediaPlayer wrongAnswer;

    public SoundHandler(Context context) {
        correctAnswer = MediaPlayer.create(context, R.raw.correct_answ);
        //   correctAnswer=MediaPlayer.create(context, R.raw.wrong_answ);
    }

    public void playCorrectSound() {
        correctAnswer.start();
    }

}
