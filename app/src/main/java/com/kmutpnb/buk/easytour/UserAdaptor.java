package com.kmutpnb.buk.easytour;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.BaseAdapter;

/**
 * Created by BUK on 15-Mar-16.
 */
public class UserAdaptor extends BaseAdapter {

//explicit

    private Context context;
    private String[] userStrings, passString, nameString, positionString;

    public UserAdaptor(Context context, String[] nameString, String[] userStrings, String[] positionString, String[] passString) {
        this.context = context;
        this.nameString = nameString;
        this.userStrings = userStrings;
        this.positionString = positionString;
        this.passString = passString;


    }//contrutor


    public int getCount() {
        return userStrings.length;
    }


    public Object getItem(int i) {
        return null;
    }


    public long getItemId(int i) {
        return 0;
    }


    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view1 = Inflater.inflate(R.layout.user_program_list, viewGroup, false);

        TextView nameView = (TextView) view1.findViewById(R.id.tvNameUser);
        nameView.setText(nameString[i]);
        TextView idView = (TextView) view1.findViewById(R.id.tvIdUser);
        idView.setText(userStrings[i]);
        TextView passwordView = (TextView) view1.findViewById(R.id.tvPassUser);
        passwordView.setText(passString[i]);
        TextView positionView = (TextView) view1.findViewById(R.id.tvPosition);
        positionView.setText(positionString[i]);
//        int y = Integer.valueOf(positionString[i]);
        if (positionString[i] == "1") {
            positionString[i] = "USER";
        }

        // Log.d("A",positionString[i]);
        return view1;

//        public View getView(int i, View view, ViewGroup viewGroup) {
//            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View view1 = layoutInflater.inflate(R.layout.tour_program_list, viewGroup, false);//false no security
//
//            TextView nameTextView = (TextView) view1.findViewById(R.id.textView5);
//            nameTextView.setText(nameStrings[i]);
//
//            TextView provinceView = (TextView) view1.findViewById(R.id.textView6);
//            provinceView.setText(provinceStrings[i]);
//
//            TextView timeUseTextView = (TextView) view1.findViewById(R.id.textView4);
//            timeUseTextView.setText(timeuseStrings[i]);
//
//
//            return view1;
//        }

    }

}//main class
