package com.demiurgosoft.lightquiz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


public class PlayGame extends ActionBarActivity {
    private final int questionsDelay = 500;
    private final int questionsPoints = 10;
    private final int questionSeconds = 8;
    private final String databaseQuery = "SELECT  * FROM QUESTIONS";
    private final String databaseName = "lq.db";
    private int points = 0;
    private int lives = 10;
    private int correctAnswer;
    //Layout Stuff
    private ImageView correctImg;
    private ImageView wrongImg;
    private ImageView questionImg;
    private TextView questionText;
    private Button soundButton;
    private TextView pointsText;
    private TextView lifeText;
    private TextView countdownText;
    private Button[] answerButtons = new Button[4];

    private CountDownTimer countdown;
    private QuestionsGenerator generator;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);

        progress = new ProgressDialog(this);
        progress.setMessage("Loading Database ");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();


        new Thread(new Runnable() {
            public void run() {
                try {
                    loadQuestions();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                loadLayout();


                runOnUiThread(new Runnable() {
                    public void run() {
                        startGame();
                    }
                });
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case android.R.id.home:
                gameOver();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        countdown.cancel();
        this.finish();
        // gameOver();

    }


    @Override
    public void onBackPressed() {
        // this.finish();
        gameOver();
    }

    //an answer was clicked
    public void answerClicked(View view) {
        countdown.cancel();
        int answer; //-1 by default
        buttonsActive(false);
        switch (view.getId()) {
            case R.id.answer_1:
                answer = 1;
                break;
            case R.id.answer_2:
                answer = 2;
                break;
            case R.id.answer_3:
                answer = 3;
                break;
            case R.id.answer_4:
                answer = 4;
                break;
            default:
                throw new RuntimeException("Unknown button ID");
        }
        if (correctAnswer == answer) correctAnswer();
        else wrongAnswer();
        updateTexts();
        nextQuestion();
    }

    public void soundClick(View view) {
        switch (view.getId()) {
            case R.id.sound_button:
                playSound();
                break;
            default:
                throw new RuntimeException("Unknown button ID");

        }
    }

    //set a new question from generator

    private void playSound() {
        ((LightQuiz) this.getApplicationContext()).soundHandler.playQuestionSound();
    }

    private void setQuestion() {
        hideQuestionMultimedia();
        buttonsActive(true);
        Question quest = generator.getQuestion();//get a randomized question

        if (!quest.validQuestion()) throw new RuntimeException("Invalid Question");
        this.correctAnswer = quest.correctAnswer;
        for (int i = 0; i < 4; i++)
            answerButtons[i].setText(quest.answers.get(i)); //set questions layout

        hideAnswerImage();
        questionText.setText(quest.text);
        QuestionType qt = quest.type();
        switch (qt) {
            case TEXT:
                //showQuestionText(((TextQuestion) quest).text);
                break;
            case IMAGE:
                showQuestionImage(((ImageQuestion) quest).image);
                break;
            case SOUND:
                showQuestionSound(((SoundQuestion) quest).sound);
                playSound();
                break;
            default:
                throw new RuntimeException("Invalid question type");
        }
        countdown = new CountDownTimer(questionSeconds * 1000, 500) {
            @Override
            public void onTick(long millisUntilFinished) {
                countdownText.setText(Integer.toString((int) (millisUntilFinished / 1000) + 1));
            /* if(millisUntilFinished/1000 == 1) {
                 wrongAnswer();
                 updateTexts();
                 nextQuestion();
             }*/
            }

            @Override
            public void onFinish() {
                buttonsActive(false);
                wrongAnswer();
                updateTexts();
                nextQuestion();
            }
        }.start();
    }

    private void showQuestionSound(String sound) {
        soundButton.setVisibility(View.VISIBLE);
        soundButton.setClickable(true);
        ((LightQuiz) this.getApplicationContext()).soundHandler.setQuestionSound(sound);
    }

    private void showQuestionImage(String imagename) {
        int resourceId = this.getResources().getIdentifier(imagename, "drawable", this.getPackageName());
        questionImg.setImageResource(resourceId);
        questionImg.setVisibility(View.VISIBLE);
    }

    private void hideQuestionMultimedia() {
        questionImg.setVisibility(View.INVISIBLE);
        soundButton.setVisibility(View.INVISIBLE);
        ((LightQuiz) this.getApplicationContext()).soundHandler.stopQuestionSound();
        soundButton.setClickable(false);
    }
    //next question after som time
    private void nextQuestion() {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (generator.size() == 0) gameOver(); //no more questions left
                    else setQuestion();
                }
            }, questionsDelay);
        }

    //What happens when a correct answer was clicked
    private void correctAnswer() {
        points += questionsPoints;
        wrongImg.setVisibility(View.INVISIBLE);
        correctImg.setVisibility(View.VISIBLE);
        ((LightQuiz) this.getApplicationContext()).soundHandler.playCorrectSound();
    }

    //What happens when a wrong answer was clicked
    private void wrongAnswer() {
        correctImg.setVisibility(View.INVISIBLE);
        wrongImg.setVisibility(View.VISIBLE);
        ((LightQuiz) this.getApplicationContext()).soundHandler.playWrongSound();
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
        countdownText = (TextView) findViewById(R.id.countdown_text);
        questionImg = (ImageView) findViewById(R.id.question_image);
        questionText = (TextView) findViewById(R.id.question);
        soundButton = (Button) findViewById(R.id.sound_button);

        answerButtons[0] = (Button) findViewById(R.id.answer_1);
        answerButtons[1] = (Button) findViewById(R.id.answer_2);
        answerButtons[2] = (Button) findViewById(R.id.answer_3);
        answerButtons[3] = (Button) findViewById(R.id.answer_4);

        hideAnswerImage();
    }

    //Updates life and score texts
    private void updateTexts() {
        lifeText.setText("Life:" + lives);
        pointsText.setText("Score:" + points);
        countdownText.setText("");
    }

    private void gameOver() {
        hideQuestionMultimedia();
        countdown.cancel();
        buttonsActive(false);
        Intent intent = new Intent(this, GameOver.class);
        intent.putExtra("Score", points);
        startActivity(intent);
        this.finish();
    }

    //reloads questions from xml
   /* private void reload_questions() throws IOException, XmlPullParserException {
        XmlResourceParser xmlq = getResources().getXml(R.xml.questions);
        MainActivity.generator.loadQuestions(xmlq);
        xmlq.close();
    }*/

    //Starts game
    private void startGame() {
        points = 0;
        lives = 10;
        buttonsActive(true);
        updateTexts();
        setQuestion();
        progress.dismiss();
        Toast toast = Toast.makeText(this, "Quiz is Ready", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
        View fore = findViewById(R.id.foreground);
        fore.setVisibility(View.INVISIBLE);
    }


    private void loadQuestions() throws IOException {
        SQLiteHelper database = new SQLiteHelper(this, databaseName);
        if (!database.openDataBase()) Log.w("PlayGame", "error loading database");
        else {
            Cursor cursor = database.query(databaseQuery);
            this.generator = new QuestionsGenerator(cursor);
        }
        database.close();
    }

    private void loadQuestions(String genre) throws IOException {
        SQLiteHelper database = new SQLiteHelper(this, databaseName);
        if (!database.openDataBase()) Log.w("PlayGame", "error loading database");
        else {
            Cursor cursor = database.query(databaseQuery + "WHERE GENRE='" + genre + "'");
            this.generator = new QuestionsGenerator(cursor);
        }
        database.close();
    }
}