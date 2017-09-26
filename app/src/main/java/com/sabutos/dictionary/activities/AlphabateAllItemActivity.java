package com.sabutos.dictionary.activities;

import android.app.ActionBar;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.sabutos.dictionary.R;
import com.sabutos.dictionary.adapter.ListAdapter;
import com.sabutos.dictionary.database.DatabaseOpenHelper;
import com.sabutos.dictionary.model.WordElements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.support.v7.appcompat.R.styleable.SearchView;

public class AlphabateAllItemActivity extends AppCompatActivity {
    String passedText = null;
    private ListView allItemList;
    private ArrayList<WordElements> mElementsArrayList;
    private ListAdapter adapter;
    private DatabaseOpenHelper mDatabase;
    private int count=0;
    InterstitialAd mInterstitialAd;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabate_all_item);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        passedText = getIntent().getStringExtra("Text Extra");
        allItemList = (ListView) findViewById(R.id.allAlphabaticalItemList);
        mDatabase = new DatabaseOpenHelper(this);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8471623365486761/5774730205");
        requestNewInterstitial();

        mElementsArrayList = (ArrayList<WordElements>) mDatabase.getAllAlphabaticOrder(passedText);
        count++;
        if(count==mElementsArrayList.size()){
            count=0;
        }
        adapter = new ListAdapter(getApplicationContext(),R.layout.item_list_view,mElementsArrayList);
        allItemList.setAdapter(adapter);
        allItemList.setFastScrollEnabled(true);
        allItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                    mInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            TextView textView = (TextView) view.findViewById(R.id.textView);
                            String passText = textView.getText().toString();
                            Intent i = new Intent(AlphabateAllItemActivity.this, SingleItemActivity.class);
                            i.putExtra("Text Extra", String.valueOf(passText));
                            startActivity(i);
                            mDatabase.addHistory(mElementsArrayList.get(position).getE_id());
                        }
                    });
                } else {
                    TextView textView = (TextView) view.findViewById(R.id.textView);
                    String passText = textView.getText().toString();
                    Intent i = new Intent(AlphabateAllItemActivity.this, SingleItemActivity.class);
                    i.putExtra("Text Extra", String.valueOf(passText));
                    startActivity(i);
                    mDatabase.addHistory(mElementsArrayList.get(position).getE_id());
                }
            }
        });

    }
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
