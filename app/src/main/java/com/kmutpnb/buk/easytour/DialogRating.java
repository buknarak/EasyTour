package com.kmutpnb.buk.easytour;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

/**
 * Created by BUK on 24-May-16.
 */
public class DialogRating extends Dialog {
    Context context;
    RatingBar ratingbar;
    Button btnOk,btnCancel;

    public DialogRating(Context context) {
        //สร้าง Dialog ใน Context ที่ส่งมา
        super(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ทำการ SetView ให้กับ Dialog

        setContentView(R.layout.rating_dialog);
        setTitle("ระดับความพึงพอใจ");

        //เชื่อมตัวแปรต่างๆ เข้ากับ View
        ratingbar = (RatingBar)findViewById(R.id.ratingBar1);
//        LayerDrawable stars = (LayerDrawable) ratingbar.getProgressDrawable();
//        stars.getDrawable(2).setColorFilter(Color.CYAN, PorterDuff.Mode.SRC_ATOP);
        DrawableCompat.setTint(ratingbar.getProgressDrawable(), Color.CYAN);
        btnOk = (Button)findViewById(R.id.btnOk);
        btnCancel = (Button)findViewById(R.id.btnCancel);


        //ให้ RatingBar แสดง 5 ดาวเป็นค่าเริ่มต้น
       // this.ratingbar.setRating(5);

        //กำหนด Event ให้กับปุ่ม OK และ Cancel
        btnOk.setOnClickListener(new OKListener());
        btnCancel.setOnClickListener(new CancelListener());
    }


    //ฟังก์ชันทำงานเมื่อมีการกดปุ่ม OK
    private class OKListener implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub

            //เรียกฟังก์ชัน setRating ใน RatingDialogActivity เพื่อแสดงคะแนน
            ((ShowDetailPlaceActivity)context).setRating(ratingbar.getRating());

            //ปิดการแสดง Dialog
            DialogRating.this.dismiss();
        }
    }

    //ฟังก์ชันทำงานเมื่อมีการกดปุ่ม Cancel
    private class CancelListener implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub

            //ปิดการแสดง Dialog
            DialogRating.this.dismiss();
        }
    }
}
