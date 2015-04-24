package com.demiurgosoft.lightquiz;

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
    private final Context dbContext;
    //The Android's default system path of your application database.
    private String dbPath; // = "/data/data/com.demiurgosoft.lightquiz/databases/";
    private String dbName;
    private SQLiteDatabase database;


    public SQLiteHelper(Context context, String fileName) {
        super(context, fileName, null, 1);
        if (android.os.Build.VERSION.SDK_INT >= 17) {
            dbPath = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            dbPath = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.dbName = fileName;
        this.dbContext = context;

    }

    //Open the database, so we can query it (create if dont exists)
    public boolean openDataBase() throws SQLException, IOException {
        createDataBase(); //if db dont exist
        String mPath = dbPath + dbName;
        //Log.v("mPath", mPath);
        database = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.OPEN_READONLY);//CREATE_IF_NECESSARY en vez de OPEN_READONLY si hay problemas
        //mDataBase = SQLiteDatabase.openDatabase(mPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        return database != null;
    }

    public Cursor query(String dbquery) {
        SQLiteDatabase db = this.getReadableDatabase(); //getWritableDatabase()??
        return db.rawQuery(dbquery, null);
    }

    private void createDataBase() throws IOException {
        //If database not exists copy it from the assets
        if (!checkDataBase()) {
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
        File dbFile = new File(dbPath + dbName);
        //Log.v("dbFile", dbFile + "   "+ dbFile.exists());
        return dbFile.exists();
    }

    private void copyDataBase() throws IOException {
        InputStream mInput = dbContext.getAssets().open(dbName);
        String outFileName = dbPath + dbName;
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

}