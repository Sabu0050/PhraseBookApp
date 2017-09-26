package com.sabutos.dictionary.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.sabutos.dictionary.R;
import com.sabutos.dictionary.adapter.ListAdapter;
import com.sabutos.dictionary.database.DatabaseOpenHelper;
import com.sabutos.dictionary.model.WordElements;

import java.util.ArrayList;

public class FebouritActivity extends AppCompatActivity {


    private ListView allItemList;
    private ArrayList<WordElements> mElementsArrayList;
    private ListAdapter adapter;
    private DatabaseOpenHelper mDatabase;
    TextView textView;

    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_febourit);
        setTitle("Favorite");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8471623365486761/5774730205");
        requestNewInterstitial();

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        allItemList = (ListView) findViewById(R.id.allAlphabaticalItemList);
        mDatabase = new DatabaseOpenHelper(this);
        mElementsArrayList = (ArrayList<WordElements>) mDatabase.getListOfAllFebourit();
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
                            Intent i = new Intent(FebouritActivity.this,SingleItemActivity.class);
                            i.putExtra("Text Extra",String.valueOf(passText));
                            startActivity(i);
                            mDatabase.addHistory(mElementsArrayList.get(position).getE_id());
                        }
                    });
                }
                else{
                TextView textView = (TextView) view.findViewById(R.id.textView);
                String passText = textView.getText().toString();
                Intent i = new Intent(FebouritActivity.this,SingleItemActivity.class);
                i.putExtra("Text Extra",String.valueOf(passText));
                startActivity(i);
                mDatabase.addHistory(mElementsArrayList.get(position).getE_id());
                }
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.deletemenu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.delete:
                mDatabase = new DatabaseOpenHelper(this);
                mDatabase.deleteAllFromFebourit();
                adapter.refreshWords(mElementsArrayList);



                // as a favorite...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
    public void onRestart(){
        super.onRestart();
        Intent show = new Intent(FebouritActivity.this, FebouritActivity.class);
        startActivity(show);

        this.finish();
    }
    public void onBackPressed(){
        super.onBackPressed();
    }
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }
}
