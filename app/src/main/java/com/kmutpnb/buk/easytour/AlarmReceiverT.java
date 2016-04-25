package com.kmutpnb.buk.easytour;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by BUK on 16-Apr-16.
 */
public class AlarmReceiverT extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent arg1) {
//        Intent i = new Intent(context, ShowEvent.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(i);

        Intent service1 = new Intent(context, MyAlarmService.class);
        context.startService(service1);
    }

}