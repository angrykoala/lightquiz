package com.demiurgosoft.lightquiz;

/**
 * Created by demiurgosoft - 4/28/15
 */
public class SoundQuestionLoader extends QuestionLoader {

    @Override
    public SoundQuestion load() {
        SoundQuestion question = new SoundQuestion();
        question.load();
        if (question.isValid()) return question;
        else return null;
    }

   /* @Override
    public boolean isType(Cursor cursor) {
        String s;
        s = cursor.getString(cursor.getColumnIndex(soundColumn));
        return (s != null && !s.isEmpty());
    }*/
}
