package com.sabutos.dictionary.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sabutos.dictionary.model.WordElements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by devil on 27-Nov-16.
 */

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "phrasesdictionary.sqlite";
    public static final String DBLOCATION = "/data/data/com.sabutos.dictionary/databases/";
    private Context mContext;
    private SQLiteDatabase mDatabase;


    public DatabaseOpenHelper(Context context) {
        super(context, DBNAME, null, 1);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(DBNAME).getPath();
        if (mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    public void addFavourit(int e_id){
        openDatabase();
        mDatabase= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fevid",e_id);
        //contentValues.put("flag",flag);
        mDatabase.insert("favourit",null,contentValues);
        closeDatabase();
    }
    public void addHistory(int e_id){
        openDatabase();
        mDatabase= this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("historyid",e_id);
        mDatabase.insert("history",null,contentValues);
        closeDatabase();
    }


    public List<WordElements> getListOfAllWord(){
        WordElements wordElements = null;
        List<WordElements> wordElementsList  =new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("select phraseidioms.e_id,phraseidioms.word,phraseidioms.meanings,phraseidioms.synonyms,phraseidioms.example,phraseidioms.type,phraseidioms.a_id from alphabates join phraseidioms where alphabates.id = phraseidioms.a_id group by phraseidioms.word order by alphabates.alphabate",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            wordElements = new WordElements(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getInt(6));
            wordElementsList.add(wordElements);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return wordElementsList;
    }
    public List<WordElements> getListOfAllFebourit(){
        WordElements wordElements = null;
        List<WordElements> wordElementsList  =new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("select phraseidioms.e_id,phraseidioms.word,phraseidioms.meanings,phraseidioms.synonyms,phraseidioms.example,phraseidioms.type,phraseidioms.a_id from favourit join phraseidioms where favourit.fevid=phraseidioms.e_id group by phraseidioms.word order by phraseidioms.word",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            wordElements = new WordElements(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getInt(6));
            wordElementsList.add(wordElements);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return wordElementsList;
    }
    public List<WordElements> getListOfAllHistory(){
        WordElements wordElements = null;
        List<WordElements> wordElementsList  =new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("select phraseidioms.e_id,phraseidioms.word,phraseidioms.meanings,phraseidioms.synonyms,phraseidioms.example,phraseidioms.type,phraseidioms.a_id from history join phraseidioms where history.historyid=phraseidioms.e_id group by phraseidioms.word order by phraseidioms.word",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            wordElements = new WordElements(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getInt(6));
            wordElementsList.add(wordElements);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return wordElementsList;
    }
    public List<WordElements> getAllAlphabaticOrder(String refWord){
        WordElements wordElements = null;
        List<WordElements> wordElementsList  =new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("select * from phraseidioms where word like '"+refWord+"%' group by word order by word",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            wordElements = new WordElements(cursor.getInt(0),cursor.getString(1).toLowerCase(),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getInt(6));
            wordElementsList.add(wordElements);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return wordElementsList;
    }
    public ArrayList<WordElements> getAllWordSuggestions() {
        WordElements wordElements = null;
        ArrayList<WordElements> wordElementsList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("select phraseidioms.e_id,phraseidioms.word,phraseidioms.meanings,phraseidioms.synonyms,phraseidioms.example,phraseidioms.type,phraseidioms.a_id from phraseidioms", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            wordElements = new WordElements(cursor.getInt(0),cursor.getString(1).toLowerCase(),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getInt(6));
            wordElementsList.add(wordElements);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return wordElementsList;

    }
    public void deleteAllFromHistory(){
        openDatabase();
        mDatabase.execSQL("delete from history where id > -1");
        closeDatabase();
    }
    public void deleteAllFromFebourit(){
        openDatabase();
        mDatabase.execSQL("delete from favourit where id > -1");
        closeDatabase();
    }
    public void deleteASingleItemFromFeb(int e_id){
        openDatabase();
        mDatabase.execSQL("delete from favourit where fevid="+e_id);
        closeDatabase();
    }
    public boolean searchFeb(int flag){
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("select fevid from phraseidioms join favourit where phraseidioms.e_id=fevid and fevid='"+flag+"'",null);
        int value=0;
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            value = cursor.getInt(0);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        if(value>0){
            return true;
        }else{
            return false;
        }


        //return value;
    }
}
