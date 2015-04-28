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
        boolean b = true;
        if (sound == null || sound.length() == 0) b = false;
        if (answers.size() != 4) b = false;
        for (int i = 0; i < 4; i++) {
            if (answers.get(i).length() == 0) b = false;
        }
        if (correctAnswer < 1 || correctAnswer > 4) b = false;
        return b;
    }
}
