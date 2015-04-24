package com.demiurgosoft.lightquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    //public static QuestionsGenerator generator;
    public static SoundHandler sound;
    private Player currentPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.demiurgosoft.lightquiz.R.layout.activity_main);
        currentPlayer = ((LightQuiz) getApplication()).player; //gets player from lightquiz application
        // = new Player(this.getApplicationContext());
        sound = new SoundHandler(this.getApplicationContext());
        updateHighScore();
        /*try {
            loadXmlQuestions();
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }*/

        //generator = new QuestionsGenerator();
     /*   SQLiteHelper db=new SQLiteHelper(this,"lq.db");
        try {
            db.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.openDataBase();
        db.loadQuestions(generator);
        db.close();
        Log.d("LOG MAIN", (String.valueOf(generator.size())));*/

    }

    @Override
    public void onResume() {
        super.onResume();
        updateHighScore();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.demiurgosoft.lightquiz.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.demiurgosoft.lightquiz.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    public void startGame(View view) {
        switch (view.getId()) {
            case com.demiurgosoft.lightquiz.R.id.start_button:
                // if (generator.isReady()) {
                    Intent intent = new Intent(this, PlayGame.class);
                    startActivity(intent);
                //  }
                break;
            default:
                throw new RuntimeException("Unknown button ID");
        }

    }

    private void updateHighScore() {
        TextView highScoreText = (TextView) findViewById(com.demiurgosoft.lightquiz.R.id.highscore);
        highScoreText.setText("High Score:  " + currentPlayer.getHighScore());
    }

    /*private void loadXmlQuestions() throws IOException, XmlPullParserException {
        XmlResourceParser xmlq = getResources().getXml(R.xml.questions);
        generator = new QuestionsGenerator(xmlq);
        xmlq.close();
    }*/
}

