package com.demiurgosoft.lightquiz;

/**
 * Created by demiurgosoft - 4/28/15
 */
public class ImageQuestionLoader extends QuestionLoader {


    @Override
    public ImageQuestion load() {
        ImageQuestion question = new ImageQuestion();
        question.load();
        if (question.isValid()) return question;
        else return null;
    }

  /*  @Override
    public boolean isType(Cursor cursor) {
        String s;
        s = cursor.getString(cursor.getColumnIndex(imageColumn));
        return (s != null && !s.isEmpty());
    }*/
}
