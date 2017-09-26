package com.sabutos.dictionary.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.sabutos.dictionary.R;
import com.sabutos.dictionary.model.WordElements;

import java.util.ArrayList;

/**
 * Created by devil on 28-Nov-16.
 */

public class ListAdapter extends BaseAdapter implements Filterable {
    private Context mContext;
    private int textViewResourceId;
    WordFilter wordFilter;
    private ArrayList<WordElements> mWordElementList;
    private ArrayList<WordElements> wordFilterList;
    public ListAdapter(Context mContext, int textViewResourceId, ArrayList<WordElements> mWordElementList) {
        this.mContext = mContext;
        this.textViewResourceId = textViewResourceId;
        this.mWordElementList = mWordElementList;
        wordFilterList=mWordElementList;
    }


    @Override
    public int getCount() {
        return mWordElementList.size();
    }

    @Override
    public Object getItem(int position) {
        return mWordElementList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mWordElementList.get(position).getE_id();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
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

        return convertView;

    }

    @Override
    public Filter getFilter() {
        if(wordFilter==null){
            wordFilter = new WordFilter();

        }
        return wordFilter;
    }

    private class WordFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<WordElements> tempList = new ArrayList<WordElements>();

                // search content in friend list
                for (int i =0;i<wordFilterList.size();i++) {
                    if ((wordFilterList.get(i).getWord().toLowerCase()).contains(constraint.toString().toLowerCase())) {
                        WordElements wordElements = new WordElements(wordFilterList.get(i).getE_id(),wordFilterList.get(i).getWord(),wordFilterList.get(i).getMeaning(),
                                wordFilterList.get(i).getSynonyms(),wordFilterList.get(i).getExample(),wordFilterList.get(i).getType(),wordFilterList.get(i).getA_id());
                        wordFilterList.add(wordElements);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = mWordElementList.size();
                filterResults.values = mWordElementList;
            }

            return filterResults;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            wordFilterList = (ArrayList<WordElements>) results.values;
            notifyDataSetChanged();

        }
    }

    public void refreshWords(ArrayList<WordElements> wordElements){
        this.mWordElementList.clear();
        this.mWordElementList.addAll(wordElements);
        notifyDataSetChanged();
    }
}
