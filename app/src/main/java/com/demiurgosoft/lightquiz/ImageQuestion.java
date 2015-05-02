package com.demiurgosoft.lightquiz;

/**
 * Created by demiurgosoft - 4/28/15
 */
public class ImageQuestion extends Question {
    public String image;
    public String text;

    @Override
    public QuestionType type() {
        return QuestionType.IMAGE;
    }

    @Override
    public boolean validQuestion() {
        if (image == null || image.length() == 0) return false;
        else return super.validQuestion();
    }
}
