package com.sabutos.dictionary.activities;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.InterstitialAd;
import com.sabutos.dictionary.R;
import com.sabutos.dictionary.adapter.CustomListAdapter;
import com.sabutos.dictionary.adapter.ListAdapter;
import com.sabutos.dictionary.database.DatabaseOpenHelper;
import com.sabutos.dictionary.model.WordElements;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    InterstitialAd mInterstitialAd;

    private ListView itemList;
    private ArrayList<WordElements> mElementsArrayList;
    private CustomListAdapter adapter;
    private DatabaseOpenHelper mDatabase;
    SearchView searchView;
    String suggestion;
    int pos;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8471623365486761/5774730205");
        requestNewInterstitial();


        itemList = (ListView) findViewById(R.id.listView);
        mDatabase = new DatabaseOpenHelper(this);
        File database = getApplicationContext().getDatabasePath(DatabaseOpenHelper.DBNAME);
        if (false == database.exists()) {
            mDatabase.getReadableDatabase();
            //Copy db
            if (copyDatabase(this)) {
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        mElementsArrayList = (ArrayList<WordElements>) mDatabase.getListOfAllWord();
        count++;
        if (count == mElementsArrayList.size()) {
            count = 0;
        }
        adapter = new CustomListAdapter(getApplicationContext(), R.layout.item_list_view, mElementsArrayList);
        itemList.setAdapter(adapter);
        itemList.setFastScrollEnabled(true);
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                    mInterstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            if (position == 0 || position == 5 || position == 10 || position == 15 || position == 20 || position == 25 || position == 30 || position == 35 || position == 40 || position == 45 || position == 50 ||
                                    position == 55 || position == 60 || position == 65 || position == 70 || position == 75 || position == 80 || position == 85 || position == 90 || position == 95 ||
                                    position == 100 || position == 105 || position == 110 || position == 115 || position == 120 || position == 125) {
                                TextView textView = (TextView) view.findViewById(R.id.textView);

                                String passText = textView.getText().toString();
                                Intent i = new Intent(MainActivity.this, AlphabateAllItemActivity.class);
                                i.putExtra("Text Extra", String.valueOf(passText));
                                startActivity(i);
                            } else {
                                TextView textView1 = (TextView) view.findViewById(R.id.textView);
                                String passText = textView1.getText().toString();
                                Intent i = new Intent(MainActivity.this, SingleItemActivity.class);
                                i.putExtra("Text Extra", String.valueOf(passText));
                                startActivity(i);
                                mDatabase.addHistory(mElementsArrayList.get(position).getE_id());
                            }
                        }
                    });
                } else {
                    if (position == 0 || position == 5 || position == 10 || position == 15 || position == 20 || position == 25 || position == 30 || position == 35 || position == 40 || position == 45 || position == 50 ||
                            position == 55 || position == 60 || position == 65 || position == 70 || position == 75 || position == 80 || position == 85 || position == 90 || position == 95 ||
                            position == 100 || position == 105 || position == 110 || position == 115 || position == 120 || position == 125) {
                        TextView textView = (TextView) view.findViewById(R.id.textView);

                        String passText = textView.getText().toString();
                        Intent i = new Intent(MainActivity.this, AlphabateAllItemActivity.class);
                        i.putExtra("Text Extra", String.valueOf(passText));
                        startActivity(i);
                    } else {
                        TextView textView1 = (TextView) view.findViewById(R.id.textView);
                        String passText = textView1.getText().toString();
                        Intent i = new Intent(MainActivity.this, SingleItemActivity.class);
                        i.putExtra("Text Extra", String.valueOf(passText));
                        startActivity(i);
                        mDatabase.addHistory(mElementsArrayList.get(position).getE_id());
                    }
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            // super.onBackPressed();
            //}
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                new AlertDialog.Builder(this)
                        //.setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Exit")
                        .setMessage("Are you want to exit?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        }
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        activateSearchView(menu);

        return true;
    }

    private void activateSearchView(Menu menu) {

        MenuItem searchItem = menu.findItem(R.id.search);
        // searchItem.expandActionView();

        searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(searchItem);

        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(
                new ComponentName(this, MainActivity.class)
        ));
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return true;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                suggestion = getSuggestion(position);
                pos = Integer.parseInt(getSuggestionPosition(position))+1;
                //Toast.makeText(getBaseContext(),"Position is: "+pos,Toast.LENGTH_SHORT).show();
                searchView.setQuery(suggestion, true);// submit query now
                mDatabase.addHistory(pos);
                return true;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                String passText = suggestion.toString();
                Intent i = new Intent(MainActivity.this,SingleItemActivity.class);
                i.putExtra("Text Extra",String.valueOf(passText));
                startActivity(i);

                return true;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }

    private String getSuggestion(int position) {
        Cursor cursor = (Cursor) searchView.getSuggestionsAdapter().getItem(
                position);
        String suggest1 = cursor.getString(cursor
                .getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1));

        return suggest1;
    }
    private String getSuggestionPosition(int position) {
        Cursor cursor = (Cursor) searchView.getSuggestionsAdapter().getItem(
                position);
        String suggest2 = cursor.getString(cursor
                .getColumnIndex(BaseColumns._ID));
        return suggest2;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==R.id.search){
            return true;
        }
        else if(id==R.id.clearHistory){
            mDatabase = new DatabaseOpenHelper(this);
            mDatabase.deleteAllFromHistory();

            return true;
        }
        else if (id==R.id.clearFevourit){
            mDatabase = new DatabaseOpenHelper(this);
            mDatabase.deleteAllFromFebourit();

            return true;
        }
        //noinspection SimplifiableIfStatement
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            Intent i = new Intent(MainActivity.this,FebouritActivity.class);
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(MainActivity.this,HistoryActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_share) {
            Intent i=new Intent(android.content.Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(android.content.Intent.EXTRA_SUBJECT,"Dictionary");
            i.putExtra(android.content.Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.sabutos.dictionary");
            startActivity(Intent.createChooser(i,"Share via"));

        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(this,PreferencesActivity.class);
            startActivity(intent);

        }
        else if(id==R.id.nav_about){
            Intent intent = new Intent(this,AboutActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private boolean copyDatabase(Context context) {
        try {

            InputStream inputStream = context.getAssets().open(DatabaseOpenHelper.DBNAME);
            String outFileName = DatabaseOpenHelper.DBLOCATION + DatabaseOpenHelper.DBNAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();

            outputStream.close();
            Log.w("MainActivity", "DB copied");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean onSearchRequested() {
        Bundle appData = new Bundle();
        //appData.putString("a", "world");
        startSearch(null, false, appData, false);
        return true;
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        // Do not need to recreate menu
        if (Build.VERSION.SDK_INT >= 11) {
            // Calling twice: first empty text field, second iconify the view
            //searchView.setIconified(true);
            searchView.setQuery("", false);

        }
    }


}
