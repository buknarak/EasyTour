package com.kmutpnb.buk.easytour;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


/**
 * Created by BUK on 09-Feb-16.
 */
public class MyAlertDialog {

    public void myDialog(Context context, String strTitle, String strMessage) {

        AlertDialog.Builder objBuilder = new AlertDialog.Builder(context);

        objBuilder.setIcon(R.drawable.danger);
        objBuilder.setTitle(strTitle);
        objBuilder.setMessage(strMessage);
        objBuilder.setCancelable(false); //ถ้าไม่กดตกลงจะออกจากเตือนไม่ได้
        objBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();//ให้ pop up หายไป

            }
        });
        objBuilder.show();
    }

}//MAin class
