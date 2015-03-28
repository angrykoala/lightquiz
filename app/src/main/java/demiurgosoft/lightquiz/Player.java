package demiurgosoft.lightquiz;

import android.content.Context;
import android.content.SharedPreferences;


public class Player {
    //private String name;
    private int highScore;

    public Player(Context context) {
        loadPlayer(context);
    }

    //sets new highscore if score is higher
    public boolean setHighScore(int score) {
        if (score > highScore) {
            this.highScore = score;
            return true;
        } else return false;
    }

    public int getHighScore() {
        return highScore;
    }

    public void resetScore() {
        this.highScore = 0;
    }

    public void savePlayer(Context context) {
        SharedPreferences playerData = context.getSharedPreferences(context.getString(R.string.data_name), 0);
        SharedPreferences.Editor editor = playerData.edit();
        editor.putInt("highScore", highScore);

        editor.apply(); //will write in bacground, for instant write use commit
    }

    private void loadPlayer(Context context) {
        SharedPreferences playerData = context.getSharedPreferences(context.getString(R.string.data_name), 0);
        this.highScore = playerData.getInt("highScore", 0);
    }


}
