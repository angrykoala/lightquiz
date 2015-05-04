package com.demiurgosoft.lightquiz;

/**
 * Created by demiurgosoft - 4/28/15
 */
public class TextQuestion extends Question {

    @Override
    public void load() {
        RawQuestion raw = questionList.getRawQuestion(type());
        if (raw != null) {
            loadText(raw);
            loadAnswers(raw);
        }
    }

    @Override
    public QuestionType type() {
        return QuestionType.TEXT;
    }

}
