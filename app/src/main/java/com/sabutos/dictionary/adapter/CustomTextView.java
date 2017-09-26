package com.sabutos.dictionary.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by s on 1/4/17.
 */

public class CustomTextView extends android.support.v7.widget.AppCompatTextView{
    public CustomTextView(Context context) {
        super(context);
        load();
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        load();
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        load();
    }

    public void load(){
        setTypeface(
                Typeface.createFromAsset(getContext().getAssets(),"arimamaduraithin.ttf"),1
        );
    }


}
