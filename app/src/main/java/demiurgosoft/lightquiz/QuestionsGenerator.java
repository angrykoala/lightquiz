package demiurgosoft.lightquiz;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by demiurgosoft - 3/25/15
 */
public class QuestionsGenerator {
    private ArrayList<Question> questionsList;
    private ArrayList<Question> leftQuestions;
    private Random randSelector;

    private boolean ready;
    private XmlPullParser parser;

    public QuestionsGenerator(XmlPullParser parser) {
        questionsList = new ArrayList<>();
        randSelector = new Random();
        leftQuestions = new ArrayList<>();
        this.parser = parser;
        try {
            readXML();
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isReady() {
        return ready;
    }
    public int size() {
        return questionsList.size();
    }

    public void clearLeftQuestons() {
        leftQuestions.clear();
    }
    public Question getQuestion() {
        if (size() == 0) throw new RuntimeException("QuestionGenerator empty");
        else {
            if (leftQuestions.size() == 0) restartQuestions();
            int randvalue = randomValue();
            Question res = leftQuestions.get(randvalue);
            leftQuestions.remove(randvalue);
            res.randomize();
            return res;
        }
    }

    private boolean addQuestion(Question q) {
        boolean b = q.validQuestion();
        if (b) questionsList.add(q);
        return b;
    }

    private void readXML() throws XmlPullParserException, IOException {
        Log.d("Questions generator", "read XML");
        ready = false;
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
        //restartQuestions();
        ready = true;
    }

    private int randomValue() {
        return randSelector.nextInt(leftQuestions.size());
    }

    private void restartQuestions() {
        leftQuestions.clear();
        leftQuestions = (ArrayList<Question>) questionsList.clone();
        Log.d("Questions generator", "restart");
    }
}
