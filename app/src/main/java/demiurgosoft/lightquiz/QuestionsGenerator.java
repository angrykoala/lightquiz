package demiurgosoft.lightquiz;

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
    ArrayList<Question> leftQuestions;
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
        return b;
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
        restartQuestions();
    }

    private int randomValue() {
        return randSelector.nextInt(leftQuestions.size());
    }

    private void restartQuestions() {
        leftQuestions = (ArrayList<Question>) questionsList.clone();
        //Log.d("questions", String.valueOf(leftQuestions.size()));
    }
}
