package com.sabutos.dictionary.model;

import com.sabutos.dictionary.R;

/**
 * Created by s on 1/1/17.
 */

public enum FontStyle {
    Small(R.style.FontStyle_Small, "Small"),
    Medium(R.style.FontStyle_Medium, "Medium"),
    Large(R.style.FontStyle_Large, "Large");

    private int resId;
    private String title;

    public int getResId() {
        return resId;
    }

    public String getTitle() {
        return title;
    }

    FontStyle(int resId, String title) {
        this.resId = resId;
        this.title = title;
    }
}
