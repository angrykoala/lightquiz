package com.demiurgosoft.lightquiz;

import android.database.Cursor;

/**
 * Created by demiurgosoft - 5/4/15
 */
public class RawQuestion {
    public static final String textattr = "QUESTION";
    private static final String correctAnswerattr = "CA";
    private static final String answer2attr = "A1";
    private static final String answer3attr = "A2";
    private static final String answer4attr = "A3";
    private static final String soundattr = "SOUND_NAME";
    private static final String imageattr = "IMAGE_NAME";

    public String text;
    public String correctAnswer;
    public String answer1;
    public String answer2;
    public String answer3;
    public String sound;
    public String image;

    public RawQuestion(Cursor cursor) {
        correctAnswer = cursor.getString(cursor.getColumnIndex(correctAnswerattr));
        answer1 = cursor.getString(cursor.getColumnIndex(answer2attr));
        answer2 = cursor.getString(cursor.getColumnIndex(answer3attr));
        answer3 = cursor.getString(cursor.getColumnIndex(answer4attr));
        text = cursor.getString(cursor.getColumnIndex(textattr));
        sound = cursor.getString(cursor.getColumnIndex(soundattr));
        image = cursor.getString(cursor.getColumnIndex(imageattr));
    }

    public QuestionType getType() {
        if (sound != null && !sound.isEmpty()) return QuestionType.SOUND;
        else if (image != null && !image.isEmpty()) return QuestionType.IMAGE;
        else if (text != null && !text.isEmpty()) return QuestionType.TEXT;
        else return null;
    }
    //Example of loader from xml file instead of sql database (not functional)
    /* public RawQuestion(XmlPullParser parser) throws XmlPullParserException, IOException {
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

}

