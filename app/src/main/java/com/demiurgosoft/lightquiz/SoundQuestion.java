package com.demiurgosoft.lightquiz;

/**
 * Created by demiurgosoft - 4/28/15
 */
public class SoundQuestion extends Question {
    public String sound; //sound resource value

    public SoundQuestion() {
        load();
    }
    @Override
    public void load() {
        RawQuestion raw = questionList.getRawQuestion(type());
        if (raw != null) {
            loadText(raw);
            loadAnswers(raw);
            loadSound(raw);
        }
    }

    @Override
    public QuestionType type() {
        return QuestionType.SOUND;
    }

    @Override
    public boolean isValid() {
        return !(sound == null || sound.length() == 0) && super.isValid();
    }

    private void loadSound(RawQuestion question) {
        this.sound = question.sound;
    }
}
