package com.demiurgosoft.lightquiz;

import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by demiurgosoft - 3/25/15
 */
public class QuestionsGenerator {
    private ArrayList<Question> questionsList;

    //Default constructor
    public QuestionsGenerator() {
        questionsList = new ArrayList<>();
    }

    //Constructor from a XMLParser
    /*public QuestionsGenerator(XmlPullParser parser) throws IOException, XmlPullParserException {
        questionsList = new ArrayList<>();
        loadQuestions(parser);
    }*/

    public QuestionsGenerator(Cursor dbCursor) {
        questionsList = new ArrayList<>();
        if (dbCursor != null) loadQuestions(dbCursor);
    }

    //true if it have at least one valid question
    public boolean empty() {
        return questionsList.isEmpty();
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
            if (res.validQuestion() == false)
                throw new RuntimeException("Question not valid"); //read another question?
            res.randomize();
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
   /* private void readXML(XmlPullParser parser) throws XmlPullParserException, IOException {
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
    }*/


    /*private void loadQuestions(XmlPullParser parser) throws XmlPullParserException, IOException {
        readXML(parser);
        shuffle();
        checkReady();
    }*/
    //loads question from Cursor (database)
    private void loadQuestions(Cursor cursor) {
        if (cursor.moveToFirst()) {
            ImageQuestionLoader imageLoader = new ImageQuestionLoader();
            SoundQuestionLoader soundLoader = new SoundQuestionLoader();
            TextQuestionLoader textLoader = new TextQuestionLoader();
            do {
                if (textLoader.isType(cursor))
                    addQuestion(textLoader.load(cursor));
                else if (soundLoader.isType(cursor))
                    addQuestion(soundLoader.load(cursor));
                else if (imageLoader.isType(cursor))
                    addQuestion(imageLoader.load(cursor));
                else Log.d("Question Generator", "Question type not found");

            } while (cursor.moveToNext());
        }
        shuffle();
    }
}
