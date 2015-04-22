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
public class QuestionsGenerator {
    private ArrayList<Question> questionsList;

    private boolean ready;

    //Default constructor
    public QuestionsGenerator() {
        questionsList = new ArrayList<>();
        ready = false;
    }

    //Constructor from a XMLParser
    public QuestionsGenerator(XmlPullParser parser) throws IOException, XmlPullParserException {
        questionsList = new ArrayList<>();
        loadQuestions(parser);
    }

    public QuestionsGenerator(Cursor dbCursor) {
        questionsList = new ArrayList<>();
        if (dbCursor != null) loadQuestions(dbCursor);
    }

    //true if it have at least one valid question
    public boolean isReady() {
        return ready;
    }

    public void shuffle() {
        Collections.shuffle(this.questionsList);
    }

    //return a question (runtime exception if empty), removes question from list
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

    //adds a question if valid
    private boolean addQuestion(Question q) {
        boolean valid = q.validQuestion();
        if (valid) {
            questionsList.add(q);
        }
        return valid;
    }

    //return generator size
    public int size() {
        return questionsList.size();
    }

    //Reads questions from xmlpullparser
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

    //reads from a cursor (database)
    private void readCursor(Cursor cursor) {
        if (cursor.moveToFirst()) {
            Question question;
            do {
                question = new Question();
                question.readCursor(cursor);
                addQuestion(question);
            } while (cursor.moveToNext());
        }
    }

    private void loadQuestions(XmlPullParser parser) throws XmlPullParserException, IOException {
        readXML(parser);
        shuffle();
        checkReady();
    }

    private void loadQuestions(Cursor cursor) {
        readCursor(cursor);
        shuffle();
        checkReady();
    }

    private void checkReady() {
        if (this.size() >= 1 && questionsList.get(0).validQuestion())
            ready = true;
    }
}
