package com.sabutos.dictionary.adapter;

import android.app.SearchManager;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sabutos.dictionary.database.DatabaseOpenHelper;
import com.sabutos.dictionary.model.WordElements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by s on 12/11/16.
 */

public class WordProvider extends ContentProvider {

    DatabaseOpenHelper mDatabase;
    ArrayList<WordElements> words;

    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {

        mDatabase = new DatabaseOpenHelper(getContext());


        words = mDatabase.getAllWordSuggestions();
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{
                BaseColumns._ID,
                SearchManager.SUGGEST_COLUMN_TEXT_1,
                SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID

        });

        if(words!=null){
            String query = uri.getLastPathSegment().toString().toLowerCase();
            int limit = Integer.parseInt(uri.getQueryParameter(SearchManager.SUGGEST_PARAMETER_LIMIT));

            int length = words.size();

            for(int i = 0; i<length&&matrixCursor.getCount()<limit;i++){
                String word = words.get(i).getWord();
                if(word.contains(query)){
                    matrixCursor.addRow(new Object[]{i,word,i});
                }
            }
        }

        return matrixCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
