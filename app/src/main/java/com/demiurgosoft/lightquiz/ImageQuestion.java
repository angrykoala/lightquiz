package com.demiurgosoft.lightquiz;

/**
 * Created by demiurgosoft - 4/28/15
 */
public class ImageQuestion extends Question {
    public String image;


    @Override
    public QuestionType type() {
        return QuestionType.IMAGE;
    }

    @Override
    public void load() {
        RawQuestion raw = questionList.getRawQuestion(type());
        if (raw != null) {
            loadText(raw);
            loadAnswers(raw);
            loadImage(raw);
        }
    }

    @Override
    public boolean isValid() {
        return !(image == null || image.length() == 0) && super.isValid();
    }

    private void loadImage(RawQuestion question) {
        this.image = question.image;
    }
}
