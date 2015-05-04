package com.demiurgosoft.lightquiz;

/**
 * Created by demiurgosoft - 4/28/15
 */
public class TextQuestionLoader extends QuestionLoader {

    @Override
    public TextQuestion load() {
        TextQuestion question = new TextQuestion();
        question.load();
        if (question.isValid()) return question;
        else return null;
    }


  /*  @Override
    public boolean isType(Cursor cursor) {
        String s;
        s = cursor.getString(cursor.getColumnIndex(textColumn));
        return (s != null && !s.isEmpty());
    }*/
}
