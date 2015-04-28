package com.demiurgosoft.lightquiz;

import android.database.Cursor;

/**
 * Created by demiurgosoft - 4/28/15
 */
public abstract class QuestionLoader {
    protected final String answer1 = "A1";
    protected final String answer2 = "A2";
    protected final String answer3 = "A3";
    protected final String answer4 = "A4";

    protected abstract Question load(Cursor cursor);

    public abstract boolean isType(Cursor cursor);


    protected void loadAnswers(Cursor cursor, Question question) {
        question.correctAnswer = 1;
        question.answers.add(cursor.getString(cursor.getColumnIndex(answer1)));
        question.answers.add(cursor.getString(cursor.getColumnIndex(answer2)));
        question.answers.add(cursor.getString(cursor.getColumnIndex(answer3)));
        question.answers.add(cursor.getString(cursor.getColumnIndex(answer4)));
    }

}
