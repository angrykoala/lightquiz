package demiurgosoft.lightquiz;

import android.database.Cursor;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by demiurgosoft - 3/25/15
 */
public class Question {
    public String text;
    public ArrayList<String> answers;
    public int correctAnswer; //from 1 to 4


    public Question() {
        correctAnswer = -1;
        answers = new ArrayList<>();
    }

    public boolean validQuestion() {
        boolean b = true;
        if (text.length() == 0) b = false;
        if (answers.size() != 4) b = false;
        for (int i = 0; i < 4; i++) {
            if (answers.get(i).length() == 0) b = false;
        }
        if (correctAnswer < 1 || correctAnswer > 4) b = false;
        return b;
    }

    public void readCursor(Cursor cursor) {
        this.correctAnswer = 1;
        text = cursor.getString(cursor.getColumnIndex("QUESTION")); //columna question
        answers.add(cursor.getString(cursor.getColumnIndex("CA")));
        answers.add(cursor.getString(cursor.getColumnIndex("A1")));
        answers.add(cursor.getString(cursor.getColumnIndex("A2")));
        answers.add(cursor.getString(cursor.getColumnIndex("A3")));
    }
    public void readXML(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "Q");
        int current = 0; //current answer to read
        int eventType = parser.getEventType();
        String currentTag = parser.getName();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                currentTag = parser.getName();
             /*   if (currentTag.equals("Q")) {
                    this.difficulty = Integer.parseInt(parser.getAttributeValue(null, "dif"));
                }*/
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
    }

    public void randomize() {
        String s = getCorrectAnswer();
        Collections.shuffle(answers);
        correctAnswer = (answers.indexOf(s)) + 1;
    }

    private String getCorrectAnswer() {
        return answers.get(correctAnswer - 1);
    }
}
