package demiurgosoft.lightquiz;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    int points=0;
    int questionNumber=0;
    //String question;
    //ArrayList<String> answers=new ArrayList<>();
    int correctAnswer;

    ImageView correctImg;
    ImageView wrongImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        correctImg=(ImageView) findViewById(R.id.correct_img);
        wrongImg=(ImageView) findViewById(R.id.wrong_img);
        hideAnswerImage();
        //test
        ArrayList<String> answers=new ArrayList<>();
        answers.add("Me");
        answers.add("You");
        answers.add("Guau");
        answers.add("*Eats poop*");
        nextQuestion("Who is a good boy?",answers,1);
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
        int answ=-1;
        switch (view.getId()) {
            case R.id.answer_1:
                answ=1;
                break;
            case R.id.answer_2:
                answ=2;
                break;
            case R.id.answer_3:
                answ=3;
                break;
            case R.id.answer_4:
                answ=4;
                break;
            default:
                throw new RuntimeException("Unknow button ID");
        }
        if(correctAnswer==answ) correctAnswer();
        else wrongAnswer();
    }
    public void exitButtonClicked(View view){

    }
    private void nextQuestion(String question,ArrayList<String> answers,int correct_answer){
        questionNumber++;
        this.correctAnswer=correct_answer;
        //if(answers.Lenght<4 err)
        //if correct_answer<0 || >4 err)
        Button b1=(Button) findViewById(R.id.answer_1);
        Button b2=(Button) findViewById(R.id.answer_2);
        Button b3=(Button) findViewById(R.id.answer_3);
        Button b4=(Button) findViewById(R.id.answer_4);
        b1.setText(answers.get(0));
        b2.setText(answers.get(1));
        b3.setText(answers.get(2));
        b4.setText(answers.get(3));
        TextView questionTitle=(TextView) findViewById(R.id.question_title);
        TextView questionText=(TextView) findViewById(R.id.question);
        questionTitle.setText("Question"+questionNumber);
        questionText.setText(question);
    }
    private void correctAnswer(){
        points+=10;
        correctImg.setVisibility(View.VISIBLE);
    }
    private void wrongAnswer(){
        wrongImg.setVisibility(View.VISIBLE);
    }
    private void hideAnswerImage(){
    correctImg.setVisibility(View.INVISIBLE);
    wrongImg.setVisibility(View.INVISIBLE);
    }
}
