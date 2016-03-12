package com.kmutpnb.buk.easytour;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by BUK on 12-Mar-16.
 */
public class TourAdaptor extends BaseAdapter{

    //explicit

    private Context context;
    private String[] nameStrings, provinceStrings, timeuseStrings;

    public TourAdaptor(Context context, String[] nameStrings, String[] provinceStrings, String[] timeuseStrings) {
        this.context = context;
        this.nameStrings = nameStrings;
        this.provinceStrings = provinceStrings;
        this.timeuseStrings = timeuseStrings;
    }//contrutor

    @Override
    public int getCount() {
        return nameStrings.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.tour_program_list, viewGroup, false);//false no security

        TextView nameTextView = (TextView) view1.findViewById(R.id.textView5);
        nameTextView.setText(nameStrings[i]);

        TextView provinceView = (TextView) view1.findViewById(R.id.textView6);
        provinceView.setText(provinceStrings[i]);

        TextView timeUseTextView = (TextView) view1.findViewById(R.id.textView4);
        timeUseTextView.setText(timeuseStrings[i]);


        return view1;
    }
}//main class
