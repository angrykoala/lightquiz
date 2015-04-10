package demiurgosoft.lightquiz;

import android.content.Intent;
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

    private ImageView correctImg;
    private ImageView wrongImg;
    private TextView pointsText;
    private TextView lifeText;
    private Button[] answerButtons = new Button[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        loadLayout();
        updateTexts();
        setQuestion();
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
        gameOver();
    }

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

    private void correctAnswer() {
        points += questionsPoints;
        wrongImg.setVisibility(View.INVISIBLE);
        correctImg.setVisibility(View.VISIBLE);
        MainActivity.sound.playCorrectSound();
    }

    private void wrongAnswer() {
        correctImg.setVisibility(View.INVISIBLE);
        wrongImg.setVisibility(View.VISIBLE);
        lives--;
        if (lives == 0) gameOver();
    }

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

    }

    private void updateTexts() {
        lifeText.setText("Life:" + lives);
        pointsText.setText("Score:" + points);
    }
    private void gameOver() {
        Intent intent = new Intent(this, GameResult.class);
        intent.putExtra("Game_Result", points);
        startActivity(intent); //starts new activity
        this.finish(); //finish this activity
    }

    private void reload_questions() throws IOException, XmlPullParserException {
        XmlResourceParser xmlq = getResources().getXml(R.xml.questions);
        MainActivity.generator.loadQuestions(xmlq);
        xmlq.close();
    }
}
