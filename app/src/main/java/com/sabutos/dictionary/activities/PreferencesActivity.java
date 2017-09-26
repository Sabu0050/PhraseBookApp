package com.sabutos.dictionary.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.sabutos.dictionary.R;
import com.sabutos.dictionary.model.FontStyle;

public class PreferencesActivity extends AppCompatActivity {


    Spinner fontStylesView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTheme().applyStyle(new Preference(this).getFontStyle().getResId(), true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);
        setTitle("Setting");
        Preference prefs = new Preference(this);

        fontStylesView = (Spinner) findViewById(R.id.fontType);
        fontStylesView.setAdapter(new ArrayAdapter<FontStyle>(this,R.layout.support_simple_spinner_dropdown_item,FontStyle.values()));
        fontStylesView.setSelection(prefs.getFontStyle().ordinal());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.preferences, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_done:
                onMenuDone();
                finish();
                return true;
            case R.id.menu_cancel:
                finish();
                return true;
            default:
                return false;
        }
    }

    private void onMenuDone() {
        Preference prefs = new Preference(this);
        fontStylesView = (Spinner) findViewById(R.id.fontType);
        prefs.setFontStyle((FontStyle) fontStylesView.getSelectedItem());

    }


}
