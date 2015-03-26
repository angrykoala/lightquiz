package demiurgosoft.lightquiz;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by andrew on 3/25/15.
 */
public class Question {
    public String text;
    public String[] answers;
    public int correctAnswer;
    public int difficulty;

    public Question() {
        difficulty = -1;
        correctAnswer = -1;
        answers = new String[4];
    }

    public boolean validQuestion() {
        boolean b = true;
        if (text.length() == 0) b = false;
        for (int i = 0; i < 4; i++) {
            if (answers[i].length() == 0) b = false;
        }
        if (correctAnswer < 1 || correctAnswer > 4) b = false;
        if (difficulty <= 0) b = false;
        return b;
    }

    public void readXML(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "Q");
        int current = 0; //current answer to read
        int eventType = parser.getEventType();
        boolean endQuestion = false;
        String currentTag = parser.getName();
        while (!endQuestion) {
            if (eventType == XmlPullParser.START_TAG) {
                currentTag = parser.getName();
                if (currentTag.equals("Q")) {
                    this.correctAnswer = Integer.parseInt(parser.getAttributeValue(null, "c_ans"));
                    this.difficulty = Integer.parseInt(parser.getAttributeValue(null, "dif"));
                }
            }
            if (eventType == XmlPullParser.TEXT) {
                if (currentTag.equals("A")) {
                    if (current < 4) answers[current] = parser.getText();
                    current++;
                } else if (currentTag.equals("Q")) {
                    this.text = parser.getText();
                }
            } else if (eventType == XmlPullParser.END_TAG) {
                if (parser.getName().equals("Q")) endQuestion = true;
            }
            eventType = parser.next();
        }

    }
}
