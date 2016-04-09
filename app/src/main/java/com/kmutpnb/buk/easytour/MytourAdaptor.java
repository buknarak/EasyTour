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
public class MytourAdaptor extends BaseAdapter {

    //explicit

    private Context context;
    private String[] tourNameStrings, hrStartStrings, dateStartStrings;

    public MytourAdaptor(Context context, String[] DateStartStrings, String[] HrStartStrings, String[] MyTourNameStrings) {
        this.context = context;
        this.dateStartStrings = DateStartStrings;
        this.hrStartStrings = HrStartStrings;
        this.tourNameStrings = MyTourNameStrings;
    }//contrutor

    @Override
    public int getCount() {
        return tourNameStrings.length;
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

        LayoutInflater Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view3 = Inflater.inflate(R.layout.mytour_program, viewGroup, false);//false no security

        TextView dateUseTextView = (TextView) view3.findViewById(R.id.dateMytour);
        dateUseTextView.setText(dateStartStrings[i]);

        TextView timeUseTextView = (TextView) view3.findViewById(R.id.timeProgram);
        timeUseTextView.setText(hrStartStrings[i]);

        TextView nameTextView = (TextView) view3.findViewById(R.id.NameProgram);
        nameTextView.setText(tourNameStrings[i]);

        return view3;
    }
}//main class
