package com.demiurgosoft.lightquiz;

import android.app.Application;
import android.database.Cursor;


/**
 * Created by demiurgosoft - 4/24/15
 */
public class LightQuiz extends Application {
    private final String databaseQuery = "SELECT  * FROM QUESTIONS";
    private final String databaseName = "lq.db";
    //"Global" Classes
    public Player player;
    public SoundHandler soundHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        player = new Player(this.getApplicationContext());
        soundHandler = new SoundHandler(this.getApplicationContext());
    }

    public void loadDatabase(String query) {
        SQLiteHelper db = new SQLiteHelper(this.getApplicationContext(), databaseName);
        Cursor cursor = db.query(query);
        if (cursor != null) Question.questionList = new QuestionSet(cursor);
        db.close();
    }

    public void loadDatabase() {
        loadDatabase(databaseQuery);
    }

}
