package demiurgosoft.lightquiz;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


public class PlayGame extends ActionBarActivity {
    private static int questionsDelay = 500;
    private static int questionsPoints = 10;
    private int points = 0;
    private int lives = 10;
    private int correctAnswer;
    private boolean isGameOver;
    //Layout Stuff
    private ImageView correctImg;
    private ImageView wrongImg;
    private TextView pointsText;
    private TextView lifeText;
    private Button[] answerButtons = new Button[4];
    private View gameOverLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        loadLayout();
        restartGame();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play_game, menu);
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

    @Override
    public void onBackPressed() {
        if (isGameOver) this.finish();
        else gameOver();
    }

    //an answer was clicked
    public void answerClicked(View view) {
        int answ; //-1 by default
        buttonsActive(false);
        switch (view.getId()) {
            case R.id.answer_1:
                answ = 1;
                break;
            case R.id.answer_2:
                answ = 2;
                break;
            case R.id.answer_3:
                answ = 3;
                break;
            case R.id.answer_4:
                answ = 4;
                break;
            default:
                throw new RuntimeException("Unknown button ID");
        }
        if (correctAnswer == answ) correctAnswer();
        else wrongAnswer();
        updateTexts();
        nextQuestion();
    }

    //Another button was clicked
    public void buttonClick(View view) {
        switch (view.getId()) {
            case R.id.restart_button:
                restartGame();
                break;
            case R.id.return_button:
                this.finish();
                break;
            default:
                throw new RuntimeException("Unknown button ID");
        }

    }

    //set a new question from generator
    private void setQuestion() {
        buttonsActive(true);
        Question quest = MainActivity.generator.getQuestion();
        if (!quest.validQuestion()) throw new RuntimeException("Invalid Question");
        this.correctAnswer = quest.correctAnswer;
        Button b1 = (Button) findViewById(R.id.answer_1);
        Button b2 = (Button) findViewById(R.id.answer_2);
        Button b3 = (Button) findViewById(R.id.answer_3);
        Button b4 = (Button) findViewById(R.id.answer_4);
        b1.setText(quest.answers.get(0));
        b2.setText(quest.answers.get(1));
        b3.setText(quest.answers.get(2));
        b4.setText(quest.answers.get(3));
        TextView questionText = (TextView) findViewById(R.id.question);

        questionText.setText(quest.text);
        hideAnswerImage();
    }

    //next question after som time
    private void nextQuestion() {
        if (!MainActivity.generator.isReady()) {
            try {
                reload_questions();
            } catch (IOException | XmlPullParserException e) {
                e.printStackTrace();
            }
        }
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setQuestion();
            }
        }, questionsDelay);

    }

    //What happens when a correct answer was clicked
    private void correctAnswer() {
        points += questionsPoints;
        wrongImg.setVisibility(View.INVISIBLE);
        correctImg.setVisibility(View.VISIBLE);
        MainActivity.sound.playCorrectSound();
    }

    //What happens when a wrong answer was clicked
    private void wrongAnswer() {
        correctImg.setVisibility(View.INVISIBLE);
        wrongImg.setVisibility(View.VISIBLE);
        lives--;
        if (lives == 0) gameOver();
    }

    //Hides any answer image (tick or cross)
    private void hideAnswerImage() {
        correctImg.setVisibility(View.INVISIBLE);
        wrongImg.setVisibility(View.INVISIBLE);
    }

    private void buttonsActive(boolean b) {
        for (Button button : answerButtons) {
            button.setClickable(b);
        }
    }

    private void loadLayout() {
        correctImg = (ImageView) findViewById(R.id.correct_img);
        wrongImg = (ImageView) findViewById(R.id.wrong_img);
        pointsText = (TextView) findViewById(R.id.points_text);
        lifeText = (TextView) findViewById(R.id.life_text);

        answerButtons[0] = (Button) findViewById(R.id.answer_1);
        answerButtons[1] = (Button) findViewById(R.id.answer_2);
        answerButtons[2] = (Button) findViewById(R.id.answer_3);
        answerButtons[3] = (Button) findViewById(R.id.answer_4);

        gameOverLayout = findViewById(R.id.game_over);
        gameOverLayout.setVisibility(View.INVISIBLE);
    }

    //Updates life and score texts
    private void updateTexts() {
        lifeText.setText("Life:" + lives);
        pointsText.setText("Score:" + points);
    }

    private void gameOver() {
        buttonsActive(false);
        gameOverLayout.setVisibility(View.VISIBLE);
        isGameOver = true;
        showFinalScore();
    }

    //reloads questions from xml
    private void reload_questions() throws IOException, XmlPullParserException {
        XmlResourceParser xmlq = getResources().getXml(R.xml.questions);
        MainActivity.generator.loadQuestions(xmlq);
        xmlq.close();
    }

    //restarts game
    private void restartGame() {
        hideAnswerImage();
        isGameOver = false;
        points = 0;
        lives = 10;
        buttonsActive(true);
        gameOverLayout.setVisibility(View.INVISIBLE);
        updateTexts();
        setQuestion();
    }

    //shows gameOver information
    private void showFinalScore() {
        TextView scoreText = (TextView) findViewById(R.id.score_text);
        scoreText.setText("Score: " + points);
        if (setHighScore()) {
            TextView newHighScoreText = (TextView) findViewById(R.id.new_highscore_text);
            newHighScoreText.setVisibility(View.VISIBLE);
        }
    }

    //returns true if new highscore
    private boolean setHighScore() {
        boolean b = MainActivity.currentPlayer.setHighScore(points);
        MainActivity.currentPlayer.savePlayer(this);
        return b;
    }
}