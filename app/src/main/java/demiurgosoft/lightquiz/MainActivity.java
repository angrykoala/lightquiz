package demiurgosoft.lightquiz;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
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
        hideAnswerImage();
        try {
            load_xml_questions();
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }
        nextQuestion(generator.getQuestion());
    }

    private void load_xml_questions() throws IOException, XmlPullParserException {
        XmlResourceParser xmlq = getResources().getXml(R.xml.questions);
        generator.readXML(xmlq);
       /* int eventType = xmlq.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG) {
                Log.d("tag:","["+xmlq.getName()+"]");
                if(xmlq.getName().equals("Q")) {
                    Question q=new Question();
                    q.readXML(xmlq);
                    Log.d("question",q.text);
                    Log.d("question",q.answers[0]);
                    if(q.validQuestion()) Log.d("question","valid");
                    else  Log.d("question","invalid");
                    nextQuestion(q);

                }
            } else if (eventType == XmlPullParser.TEXT) {
                Log.d("text", xmlq.getText());
            } else if (eventType == XmlPullParser.END_TAG) {
                Log.d("End tag", xmlq.getName());
            }*/
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
                throw new RuntimeException("Unknown button ID");
        }
        if(correctAnswer==answ) correctAnswer();
        else wrongAnswer();


    }
    public void exitButtonClicked(View view){

    }

    private void nextQuestion(Question quest) {
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
        questionTitle.setText("Question"+questionNumber);
        questionText.setText(quest.text);
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
}
