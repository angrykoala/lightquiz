package com.demiurgosoft.lightquiz;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by demiurgosoft - 5/4/15
 * Stored data from database ordered
 */
public class QuestionSet {
    private ArrayList<RawQuestion> imageQuestions = new ArrayList<>();
    private ArrayList<RawQuestion> soundQuestions = new ArrayList<>();
    private ArrayList<RawQuestion> textQuestions = new ArrayList<>();
    private int imageindex;
    private int soundindex;
    private int textindex;

    //load from Cursor (database)
    public QuestionSet(Cursor cursor) {
        if (cursor.moveToFirst()) {
            do {
                RawQuestion question = new RawQuestion(cursor);
                switch (question.getType()) {
                    case IMAGE:
                        imageQuestions.add(question);
                        break;
                    case SOUND:
                        soundQuestions.add(question);
                        break;
                    case TEXT:
                        textQuestions.add(question);
                        break;
                }
            } while (cursor.moveToNext());
        }
        restart();
    }


    //Example of question loader from XML file
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

    public int size(QuestionType type) {
        switch (type) {
            case IMAGE:
                return imageQuestions.size();
            case SOUND:
                return soundQuestions.size();
            case TEXT:
                return textQuestions.size();
            default:
                return 0;
        }
    }

    public int size() {
        return imageQuestions.size() + soundQuestions.size() + textQuestions.size();
    }

    public void clear() {
        imageindex = soundindex = textindex = 0;
        imageQuestions.clear();
        soundQuestions.clear();
        textQuestions.clear();
    }

    public void restart() {
        imageindex = soundindex = textindex = 0;
        Collections.shuffle(imageQuestions);
        Collections.shuffle(soundQuestions);
        Collections.shuffle(textQuestions);
    }

    public RawQuestion getRawQuestion(QuestionType type) {
        switch (type) {
            case IMAGE:
                if (imageindex >= imageQuestions.size()) return null;
                else {
                    imageindex++;
                    return imageQuestions.get(imageindex - 1);
                }
            case SOUND:
                if (soundindex >= soundQuestions.size()) return null;
                else {
                    soundindex++;
                    return soundQuestions.get(soundindex - 1);
                }
            case TEXT:
                if (textindex >= textQuestions.size()) return null;
                else {
                    textindex++;
                    return textQuestions.get(textindex - 1);
                }
            default:
                return null;

        }
    }
   /* public RawQuestion getRawQuestion(){
        if(soundindex+imageindex+textindex>=size()) return null;
        RawQuestion rawq;
        Random rand=new Random();
        do{
            int selec=rand.nextInt(QuestionType.values().length);
            rawq=getRawQuestion(QuestionType.values()[selec]);
        }while(rawq==null);
        return rawq;
    }*/
}
