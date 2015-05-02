package com.demiurgosoft.lightquiz;

import android.database.Cursor;

/**
 * Created by demiurgosoft - 4/28/15
 */
public class TextQuestionLoader extends QuestionLoader {

    @Override
    public TextQuestion load(Cursor cursor) {
        TextQuestion question = new TextQuestion();
        loadAnswers(cursor, question);
        loadText(cursor, question);
        return question;
    }

    @Override
    public boolean isType(Cursor cursor) {
        String s;
        s = cursor.getString(cursor.getColumnIndex(textColumn));
        return (s != null && !s.isEmpty());
    }
}
