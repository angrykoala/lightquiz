package demiurgosoft.lightquiz;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by demiurgosoft - 4/15/15
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    //The Android's default system path of your application database.
    private static String DB_PATH; // = "/data/data/com.demiurgosoft.lightquiz/databases/";

    private static String DB_NAME = "lighquiz.db";
    private final Context dbContext;
    private SQLiteDatabase database;


    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, 1);
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.dbContext = context;

    }

    public void createDataBase() throws IOException {
        //If database not exists copy it from the assets

        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {
            this.getReadableDatabase();
            this.close();
            try {
                //Copy the database from assests
                copyDataBase();
                Log.e("database", "createDatabase database created");
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    //Check that the database exists here: /data/data/your package/databases/Da Name
    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists();
    }

    private void copyDataBase() throws IOException {
        InputStream mInput = dbContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    //Open the database, so we can query it
    public boolean openDataBase() throws SQLException {
        String mPath = DB_PATH + DB_NAME;
        //Log.v("mPath", mPath);
        database = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.OPEN_READONLY);//CREATE_IF_NECESSARY
        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return database != null;
    }

       /* DATABASE_NAME = DBActivity.DatabaseName;
        // checking database and open it if exists
        if (checkDataBase()) {
            openDataBase();
        } else
        {
            try {
                this.getReadableDatabase();
                copyDataBase();
                this.close();
                openDataBase();

            } catch (IOException e) {
                throw new Error("Error copying database");
            }
            Toast.makeText(context, "Initial database is created", Toast.LENGTH_LONG).show();
        }
    }*/

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public synchronized void close() {
        if (database != null)
            database.close();
        super.close();
    }

    public void loadQuestions() {
        //List<Question> books = new LinkedList<Questions>();

        // 1. build the query
        String query = "SELECT  * FROM LIGHTQUIZ";

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);


        // 3. go over each row, build book and add it to list
        Question question;
        if (cursor.moveToFirst()) {
            do {
                question = new Question();
                question.correctAnswer = 1;
                question.text = cursor.getString(cursor.getColumnIndex("QUESTION")); //columna question
                question.answers.add(cursor.getString(cursor.getColumnIndex("CA")));
                question.answers.add(cursor.getString(cursor.getColumnIndex("A1")));
                question.answers.add(cursor.getString(cursor.getColumnIndex("A2")));
                question.answers.add(cursor.getString(cursor.getColumnIndex("A3")));

                MainActivity.generator.addQuestion(question);
            } while (cursor.moveToNext());
        }

    }
}