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

        Intent service1 = new Intent(context, MyAlarmService.class);
        context.startService(service1);
    }

}