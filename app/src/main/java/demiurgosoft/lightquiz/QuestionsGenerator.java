package demiurgosoft.lightquiz;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by demiurgosoft - 3/25/15
 */
public class QuestionsGenerator {
    private ArrayList<Question> questionsList;

    private boolean ready;

    public QuestionsGenerator(XmlPullParser parser) throws IOException, XmlPullParserException {
        questionsList = new ArrayList<>();
        loadQuestions(parser);
    }

    public boolean isReady() {
        return ready;
    }
    public Question getQuestion() {
        if (questionsList.isEmpty()) throw new RuntimeException("QuestionGenerator empty");
        else {
            Question res = questionsList.get(0);
            questionsList.remove(0);
            res.randomize();
            if (questionsList.isEmpty()) ready = false;
            return res;
        }
    }

    public boolean addQuestion(Question q) {
        boolean b = q.validQuestion();
        if (b) questionsList.add(q);
        return b;
    }

    private void readXML(XmlPullParser parser) throws XmlPullParserException, IOException {
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

    public void loadQuestions(XmlPullParser parser) throws XmlPullParserException, IOException {
        readXML(parser);
        Collections.shuffle(questionsList);
        ready = true;
    }
}
