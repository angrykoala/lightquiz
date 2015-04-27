package com.demiurgosoft.lightquiz;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by demiurgosoft - 4/10/15
 */
public class SoundHandler {
    Context context;
    private MediaPlayer correctAnswer;
    private MediaPlayer questionSound;
    //private static MediaPlayer wrongAnswer;

    public SoundHandler(Context context) {
        this.context = context;
        correctAnswer = MediaPlayer.create(context, R.raw.correct_answ);
        //   correctAnswer=MediaPlayer.create(context, R.raw.wrong_answ);
    }

    public void playCorrectSound() {
        correctAnswer.start();
    }

    public void playQuestionSound() {
        questionSound.start();
    }

    public void setQuestionSound(String soundname) {
        int resourceId = context.getResources().getIdentifier("raw", soundname, "com.demiurgosoft.lightquiz");
        questionSound = MediaPlayer.create(context, resourceId);

    }

}
