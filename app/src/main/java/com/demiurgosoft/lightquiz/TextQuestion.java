package com.demiurgosoft.lightquiz;

/**
 * Created by demiurgosoft - 4/28/15
 */
public class TextQuestion extends Question {
    //public String text;

    @Override
    public QuestionType type() {
        return QuestionType.TEXT;
    }

}
