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
    private Player currentPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.demiurgosoft.lightquiz.R.layout.activity_main);
        currentPlayer = ((LightQuiz) getApplication()).player; //gets player from lightquiz application
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
        ((LightQuiz) this.getApplicationContext()).clearQuestions();
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
        Intent intent;
        switch (view.getId()) {
            case R.id.start_button:
                // if (generator.isReady()) {
                intent = new Intent(this, PlayGame.class);
                startActivity(intent);
                //  }
                break;
            case R.id.other_game_button:
                String selection = selecGenre();
                intent = new Intent(this, PlayGame.class);
                intent.putExtra("Genre", selection);
                startActivity(intent);
                break;
            default:
                throw new RuntimeException("Unknown button ID");
        }

    }

    String selecGenre() {
        //show an alertdialog with options, see http://developer.android.com/guide/topics/ui/dialogs.html
        return QuestionGenres.CIENCIAS.toString();
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

