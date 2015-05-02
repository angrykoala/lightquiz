package com.demiurgosoft.lightquiz;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by demiurgosoft - 3/25/15
 */
public abstract class Question {
    public ArrayList<String> answers = new ArrayList();
    public int correctAnswer = -1; //from 1 to 4
    public String text = null;


    public abstract QuestionType type();

    public boolean validQuestion() {
        if (text == null || text.length() == 0) return false;
        else if (answers.size() != 4) return false;
        else if (correctAnswer < 1 || correctAnswer > 4) return false;
        else {
            for (int i = 0; i < 4; i++) {
                if (answers.get(i).length() == 0) return false;
            }
        }
        return true;
    }

   /* public abstract void readCursor(Cursor cursor);{
       /* this.correctAnswer = 1;
        text = cursor.getString(cursor.getColumnIndex("QUESTION")); //columna question
        answers.add(cursor.getString(cursor.getColumnIndex("CA")));
        answers.add(cursor.getString(cursor.getColumnIndex("A1")));
        answers.add(cursor.getString(cursor.getColumnIndex("A2")));
        answers.add(cursor.getString(cursor.getColumnIndex("A3")));

//        sound=cursor.getString(cursor.getColumnIndex("SOUND_NAME"));
//        image=cursor.getString(cursor.getColumnIndex("IMAGE_NAME"));

    }*/
/*
    public boolean hasImage() {
        if (image == null || image.isEmpty()) return false;
        else return true;
    }

    public boolean hasSound() {
        if (sound == null || sound.isEmpty()) return false;
        else return true;

    }*/

   /* public void readXML(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "Q");
        int current = 0; //current answer to read
        int eventType = parser.getEventType();
        String currentTag = parser.getName();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                currentTag = parser.getName();
            }
            if (eventType == XmlPullParser.TEXT) {
                switch (currentTag) {
                    case "A":
                        if (current < 4) answers.add(parser.getText());
                        current++;
                        break;
                    case "Q":
                        this.text = parser.getText();
                        break;
                    case "CA":
                        if (current < 4) answers.add(parser.getText());
                        this.correctAnswer = current + 1;
                        current++;
                        break;
                }
            } else if (eventType == XmlPullParser.END_TAG) {
                if (parser.getName().equals("Q")) break;
            }
            eventType = parser.next();
        }
    }*/

    public void randomize() {
        String s = getCorrectAnswer();
        Collections.shuffle(answers);
        correctAnswer = (answers.indexOf(s)) + 1;
    }

    private String getCorrectAnswer() {
        return answers.get(correctAnswer - 1);
    }
}
