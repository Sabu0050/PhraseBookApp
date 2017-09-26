package com.sabutos.dictionary.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sabutos.dictionary.R;
import com.sabutos.dictionary.model.WordElements;

import java.util.ArrayList;

/**
 * Created by s on 12/28/16.
 */

public class CustomListAdapter extends BaseAdapter {
    private Context mContext;
    private int textViewResourceId;
    private ArrayList<WordElements> mWordElementList;

    public CustomListAdapter(Context mContext, int textViewResourceId, ArrayList<WordElements> mWordElementList) {
        this.mContext = mContext;
        this.textViewResourceId = textViewResourceId;
        this.mWordElementList = mWordElementList;
    }

    @Override
    public int getCount() {
        return mWordElementList.size();
    }

    @Override
    public Object getItem(int i) {
        return mWordElementList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mWordElementList.get(i).getE_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        LayoutInflater mInflater = (LayoutInflater) mContext
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = null;

        if (convertView==null){
            notifyDataSetChanged();
            convertView = mInflater.inflate(R.layout.item_list_view,null);

            TextView tv_category = (TextView) convertView.findViewById(R.id.textView);

            WordElements wordElements = mWordElementList.get(position);
            tv_category.setText(wordElements.getWord());

        }
        if (position%5 ==0){

            //convertView.setBackgroundColor(Color.parseColor("#999999"));
            convertView.setBackground(mContext.getResources().getDrawable(R.drawable.custom_list));

        }

        return convertView;
    }
}
