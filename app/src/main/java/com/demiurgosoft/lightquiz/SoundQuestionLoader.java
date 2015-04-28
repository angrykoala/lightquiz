package com.demiurgosoft.lightquiz;

import android.database.Cursor;

/**
 * Created by demiurgosoft - 4/28/15
 */
public class SoundQuestionLoader extends QuestionLoader {
    public static final String soundColumn = "SOUND_NAME";

    @Override
    protected SoundQuestion load(Cursor cursor) {
        SoundQuestion question = new SoundQuestion();
        loadAnswers(cursor, question);
        question.sound = cursor.getString(cursor.getColumnIndex(soundColumn));
        return question;
    }

    @Override
    public boolean isType(Cursor cursor) {
        String s;
        s = cursor.getString(cursor.getColumnIndex(soundColumn));
        return (s != null && !s.isEmpty());
    }
}
