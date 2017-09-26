package com.sabutos.dictionary.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sabutos.dictionary.R;
import com.sabutos.dictionary.database.DatabaseOpenHelper;
import com.sabutos.dictionary.model.WordElements;

import org.xml.sax.Parser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.prefs.Preferences;

public class SingleItemActivity extends AppCompatActivity {
    String passedText = null,sWord,sMean,sSyno,sType,sExample;
    TextView textView1,textView2,textView3,textView4,textView5;
    private DatabaseOpenHelper mDatabase;
    private ArrayList<WordElements> mElementsArrayList;
    private WordElements wordElements;
    private int count=0;
    boolean test;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTheme().applyStyle(new Preference(this).getFontStyle().getResId(),true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item);
        setTitle(getIntent().getStringExtra("Text Extra").toString());

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        passedText = getIntent().getStringExtra("Text Extra");
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView3 = (TextView) findViewById(R.id.textView7);
        textView4 = (TextView) findViewById(R.id.textView8);
        textView5 = (TextView) findViewById(R.id.textView4);
        mDatabase = new DatabaseOpenHelper(this);
        mElementsArrayList = (ArrayList<WordElements>) mDatabase.getAllAlphabaticOrder(passedText);

        sWord = mElementsArrayList.get(count).getWord().toString();
        sMean = mElementsArrayList.get(count).getMeaning().toString();
        sSyno = mElementsArrayList.get(count).getSynonyms().toString();
        sType = mElementsArrayList.get(count).getType().toString();
        sExample = mElementsArrayList.get(count).getExample().toString();
        textView1.setText(sWord);
        textView3.setText(sMean);
        if(sSyno.equals("")){
            textView2.setText("Synonyms are not found.");
        }
        else {
            textView2.setText(sSyno);
        }
        if(sType.equals("a_prep"))
        {
            textView4.setText("("+"appropriate preposition"+")");
        }
        else if(sType.equals("")){
            textView4.setText("("+"phrase"+")");
        }
        else {
            textView4.setText("("+sType+")");
        }
        if(sExample.equals("")){
            textView5.setText("Example is not found.");
        }
        else {
            textView5.setText(sExample);
        }
        count++;
        if (count == mElementsArrayList.size()) {
            count = 0;
        }
        test = mDatabase.searchFeb(mElementsArrayList.get(count).getE_id());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.other_manu, menu);
        this.menu = menu;
        updateMenu();
        return true;
    }
    private void updateMenu() {

        // This does work
        MenuItem feb = menu.findItem(R.id.favourit);
       if (test) {
            feb.setChecked(true);
            feb.setIcon(R.drawable.liked);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.favourit:
                // User chose the "Favorite" action, mark the current item
                //Button love =

                mDatabase = new DatabaseOpenHelper(this);
                if (item.isChecked()) {
                    item.setChecked(false);
                    item.setIcon(R.drawable.like);
                    Toast.makeText(SingleItemActivity.this, "Remove from Favourite", Toast.LENGTH_SHORT).show();
                    mDatabase.deleteASingleItemFromFeb(mElementsArrayList.get(count).getE_id());
                    //saveInSp("isCheck", false);

                } else {
                    item.setChecked(true);
                    item.setIcon(R.drawable.liked);
                    Toast.makeText(SingleItemActivity.this, "Added to Favourite", Toast.LENGTH_SHORT).show();
                    mDatabase.addFavourit(mElementsArrayList.get(count).getE_id());
                    //saveInSp("isCheck", true);

                }
                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
/*mWord = sPref.getString("key1","");
                mMean = sPref.getString("key2","");
                textView1.setText(mWord);
                textView2.setText(mMean);*/