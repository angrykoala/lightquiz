package com.demiurgosoft.lightquiz;

import android.app.Application;


/**
 * Created by demiurgosoft - 4/24/15
 */
public class LightQuiz extends Application {
    public Player player;

    @Override
    public void onCreate() {
        super.onCreate();
        player = new Player(this.getApplicationContext());
    }

}
