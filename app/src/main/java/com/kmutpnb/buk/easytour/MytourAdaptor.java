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
    private String[] dateStrings, nameString, hrStartString;


    public MytourAdaptor(Context context,  String[] dateStrings, String[] hrStartString, String[] nameString) {
        this.context = context;
        this.dateStrings = dateStrings;
        this.hrStartString = hrStartString;
        this.nameString = nameString;



    }//contrutor


    @Override
    public int getCount() {
        return nameString.length;
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

        View view3 = Inflater.inflate(R.layout.mytour_program, viewGroup, false);
        TextView dateUseTextView = (TextView) view3.findViewById(R.id.tvdateMytour);
        dateUseTextView.setText(dateStrings[i]);
        TextView timeUseTextView = (TextView) view3.findViewById(R.id.tvtimeProgram);
        timeUseTextView.setText(hrStartString[i]);
        TextView nameTextView = (TextView) view3.findViewById(R.id.tvNameProgram);
        nameTextView.setText(nameString[i]);

        return view3;
    }
}//main class