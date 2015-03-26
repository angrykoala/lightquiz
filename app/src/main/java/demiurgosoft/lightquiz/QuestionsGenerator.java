package demiurgosoft.lightquiz;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by andrew on 3/25/15.
 */
public class QuestionsGenerator {
    ArrayList<Question> questionsList;
    Random randSelector;

    public QuestionsGenerator() {
        questionsList = new ArrayList<>();
        randSelector = new Random();
    }

    public int size() {
        return questionsList.size();
    }

    public boolean addQuestion(Question q) {
        boolean b = q.validQuestion();
        if (b) questionsList.add(q);
        Log.d("Add question", String.valueOf(b));
        return b;
    }

    public Question getQuestion() {
        if (size() == 0) throw new RuntimeException("QuestionGenerator empty");
        else {
            int randvalue = randomValue();
            Question res = questionsList.get(randvalue);
            questionsList.remove(randvalue);
            return res;
        }
    }

    public void readXML(XmlPullParser parser) throws XmlPullParserException, IOException {
        // parser.require(XmlPullParser.START_TAG, null, "LightQuiz");
        int eventType = parser.getEventType();
        String currentTag;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                currentTag = parser.getName();
                if (currentTag.equals("Q")) {
                    Question new_q = new Question();
                    new_q.readXML(parser);
                    addQuestion(new_q);
                }
            }
            eventType = parser.next();
        }
    }

    private int randomValue() {
        return randSelector.nextInt(size());
    }
}
