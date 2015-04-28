package com.demiurgosoft.lightquiz;

import android.database.Cursor;

/**
 * Created by demiurgosoft - 4/28/15
 */
public class ImageQuestionLoader extends QuestionLoader {
    public static final String imageColumn = "IMAGEN_NAME";

    @Override
    public ImageQuestion load(Cursor cursor) {
        ImageQuestion question = new ImageQuestion();
        loadAnswers(cursor, question);
        question.image = cursor.getString(cursor.getColumnIndex(imageColumn));
        return question;
    }

    @Override
    public boolean isType(Cursor cursor) {
        String s;
        s = cursor.getString(cursor.getColumnIndex(imageColumn));
        return (s != null && !s.isEmpty());
    }
}
