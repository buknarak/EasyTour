package com.kmutpnb.buk.easytour;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by BUK on 03-Apr-16.
 */
public class ConfirmMyTourAdapter extends BaseAdapter{

    private Context objContext;
    private String[] tourNameConStrings, timeuseConStrings;

    public ConfirmMyTourAdapter(Context objContext, String[] tourNameConStrings, String[] timeuseConStrings){

        this.objContext = objContext;
        this.tourNameConStrings = tourNameConStrings;
        this.timeuseConStrings = timeuseConStrings;
    }

    @Override
    public int getCount() {
        return tourNameConStrings.length;
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

        LayoutInflater objLayoutInflater = (LayoutInflater) objContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = objLayoutInflater.inflate(R.layout.firmmytour_listview, viewGroup, false);

        TextView tourNameConTextView = (TextView) view1.findViewById(R.id.tvTourNameCon);
        tourNameConTextView.setText(tourNameConStrings[i]);

        TextView timeuseTextView = (TextView) view1.findViewById(R.id.tvTimeuseCon);
        timeuseTextView.setText(timeuseConStrings[i]);

        return view1;
    }
}
