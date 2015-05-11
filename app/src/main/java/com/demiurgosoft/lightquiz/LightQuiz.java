package com.demiurgosoft.lightquiz;

import android.app.Application;
import android.database.Cursor;

import java.io.IOException;


/**
 * Created by demiurgosoft - 4/24/15
 */
public class LightQuiz extends Application {
    private final String databaseName = "lq.db";
    private final String databaseQuery = "SELECT * FROM QUESTIONS";
    //"Global" Classes
    public Player player;
    public SoundHandler soundHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        player = new Player(this.getApplicationContext());
        soundHandler = new SoundHandler(this.getApplicationContext());
    }

    public void loadRawQuestions() throws IOException {
        SQLiteHelper database = new SQLiteHelper(this, databaseName);
        if (!database.openDataBase()) throw new RuntimeException("database not loaded");
        else {
            Cursor cursor = database.query(databaseQuery);
            Question.questionList = new QuestionSet(cursor);
        }
        database.close();
    }

    public void clearQuestions() {
        if (Question.questionList != null) Question.questionList.clear();
        Question.questionList = null;
    }

}
