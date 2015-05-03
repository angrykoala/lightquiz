package com.demiurgosoft.lightquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class GameOver extends ActionBarActivity {
    int score;
    private Player currentPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        score = intent.getIntExtra("Score", -1);
        if (score == -1) throw new RuntimeException("Score couldn't be loaded");
        currentPlayer = ((LightQuiz) getApplication()).player;
        setContentView(R.layout.activity_game_over);
        setHighScore();
        showFinalScore();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_over, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
    //shows gameOver information
    private void showFinalScore() {
        TextView scoreText = (TextView) findViewById(R.id.score_text);
        scoreText.setText("Score: " + score);
        if (setHighScore()) {
            TextView newHighScoreText = (TextView) findViewById(R.id.new_highscore_text);
            newHighScoreText.setVisibility(View.VISIBLE);
        }
    }

    //Another button was clicked
    public void buttonClick(View view) {
        switch (view.getId()) {
            case R.id.restart_button:
                Intent intent = new Intent(this, PlayGame.class);
                startActivity(intent);
                this.finish();

                break;
            case R.id.return_button:
                this.finish();
                break;
            default:
                throw new RuntimeException("Unknown button ID");
        }

    }

    //returns true if new highscore
    private boolean setHighScore() {
        return currentPlayer.setHighScore(score);
    }
}
