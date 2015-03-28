package demiurgosoft.lightquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class GameResult extends ActionBarActivity {
    int points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);
        Intent intent = getIntent();
        this.points = intent.getIntExtra("Game_Result", 0);
        showScore();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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

    private void showScore() {
        TextView scoreText = (TextView) findViewById(R.id.score_text);
        scoreText.setText("Score: " + points);
        if (setHighScore()) {
            TextView newHighScoreText = (TextView) findViewById(R.id.new_highscore_text);
            newHighScoreText.setVisibility(View.VISIBLE);

        }

    }

    //returns true if new highscore
    public boolean setHighScore() {
        boolean b = MainActivity.currentPlayer.setHighScore(points);
        MainActivity.currentPlayer.savePlayer(this);
        return b;
    }
}
