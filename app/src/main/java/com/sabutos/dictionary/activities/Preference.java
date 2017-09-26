package com.sabutos.dictionary.activities;

import android.content.Context;
import android.content.SharedPreferences;

import com.sabutos.dictionary.model.FontStyle;

/**
 * Created by s on 12/29/16.
 */

public class Preference {

    private final static String FONT_STYLE = "FONT_STYLE";

    private final Context context;

    public Preference(Context context) {
        this.context = context;
    }

    protected SharedPreferences open() {
        return context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }

    protected SharedPreferences.Editor edit() {
        return open().edit();
    }

    public FontStyle getFontStyle() {
        return FontStyle.valueOf(open().getString(FONT_STYLE,
                FontStyle.Medium.name()));
    }

    public void setFontStyle(FontStyle style) {
        edit().putString(FONT_STYLE, style.name()).commit();
    }



}
