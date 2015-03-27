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


public class MainActivity extends ActionBarActivity {

    int points;
    int questionNumber;
    //String question;
    //ArrayList<String> answers=new ArrayList<>();
    int correctAnswer;
    QuestionsGenerator generator;
    ImageView correctImg;
    ImageView wrongImg;

    public MainActivity() {
        questionNumber = 0;
        points = 0;
        generator = new QuestionsGenerator();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        correctImg=(ImageView) findViewById(R.id.correct_img);
        wrongImg=(ImageView) findViewById(R.id.wrong_img);
        try {
            load_xml_questions();
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        setQuestion();
    }

    private void load_xml_questions() throws IOException, XmlPullParserException {
        XmlResourceParser xmlq = getResources().getXml(R.xml.questions);
        generator.readXML(xmlq);
        xmlq.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void answerClicked(View view) {
        int answ = -1;
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
        nextQuestion();
    }

    public void exitButtonClicked(View view){
        finish();
    }

    private void setQuestion() {
        buttonsActive(true);
        Question quest = generator.getQuestion();
        questionNumber++;
        if (!quest.validQuestion()) throw new RuntimeException("Invalid Question");
        this.correctAnswer = quest.correctAnswer;
        Button b1=(Button) findViewById(R.id.answer_1);
        Button b2=(Button) findViewById(R.id.answer_2);
        Button b3=(Button) findViewById(R.id.answer_3);
        Button b4=(Button) findViewById(R.id.answer_4);
        b1.setText(quest.answers[0]);
        b2.setText(quest.answers[1]);
        b3.setText(quest.answers[2]);
        b4.setText(quest.answers[3]);
        TextView questionTitle=(TextView) findViewById(R.id.question_title);
        TextView questionText=(TextView) findViewById(R.id.question);
        questionTitle.setText("Question " + questionNumber);
        questionText.setText(quest.text);
        hideAnswerImage();
    }

    private void nextQuestion() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setQuestion();
            }
        }, 800);

    }
    private void correctAnswer(){
        points+=10;
        wrongImg.setVisibility(View.INVISIBLE);
        correctImg.setVisibility(View.VISIBLE);
    }
    private void wrongAnswer(){
        correctImg.setVisibility(View.INVISIBLE);
        wrongImg.setVisibility(View.VISIBLE);
    }
    private void hideAnswerImage(){
    correctImg.setVisibility(View.INVISIBLE);
    wrongImg.setVisibility(View.INVISIBLE);
    }

    private void buttonsActive(boolean b) {
        Button b1 = (Button) findViewById(R.id.answer_1);
        Button b2 = (Button) findViewById(R.id.answer_2);
        Button b3 = (Button) findViewById(R.id.answer_3);
        Button b4 = (Button) findViewById(R.id.answer_4);
        b1.setClickable(b);
        b2.setClickable(b);
        b3.setClickable(b);
        b4.setClickable(b);

    }
}
