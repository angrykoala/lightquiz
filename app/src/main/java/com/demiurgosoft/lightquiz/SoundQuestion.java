package com.demiurgosoft.lightquiz;

/**
 * Created by demiurgosoft - 4/28/15
 */
public class SoundQuestion extends Question {
    public String sound;
    public String text;

    @Override
    public QuestionType type() {
        return QuestionType.SOUND;
    }

    @Override
    public boolean validQuestion() {
        if (sound == null || sound.length() == 0) return false;
        else return super.validQuestion();
    }
}
