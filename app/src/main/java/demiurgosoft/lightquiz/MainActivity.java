package demiurgosoft.lightquiz;

import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {
    public static Player currentPlayer;
    public static QuestionsGenerator generator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        currentPlayer = new Player(this);
        updateHighScore();
        try {
            load_xml_questions();
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        updateHighScore();
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

    public void startGame(View view) {
        switch (view.getId()) {
            case R.id.start_button:
                if (generator.isReady()) {
                    Intent intent = new Intent(this, PlayGame.class);
                    startActivity(intent);
                }
                break;
            default:
                throw new RuntimeException("Unknown button ID");
        }

    }

    private void updateHighScore() {
        TextView highScoreText = (TextView) findViewById(R.id.highscore);
        highScoreText.setText("High Score:  " + currentPlayer.getHighScore());
    }

    private void load_xml_questions() throws IOException, XmlPullParserException {
        XmlResourceParser xmlq = getResources().getXml(R.xml.questions);
        generator = new QuestionsGenerator(xmlq);
        xmlq.close();
    }
}

