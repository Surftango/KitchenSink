package com.maa.ca.kitchensink;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ca.integration.CaMDOIntegration;

/**
 * Created by kanch06 on 6/27/15.
 */
public class Feedback {


    private static BroadcastReceiver mMessageReceiver;
    private static boolean shown;

    public static BroadcastReceiver getBroadCastReciever() {
        mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //Customer implements  the code here and calls this method.
                //Notify the App user that the previous session crashed and collect feedback optionally.
                //Implement any UI Logic to get any response from the user. For example, AlertView, TextFields.
                //Once you get the feedback, convert it into setCustomerFeedback and send the data.
                //For example: if the feedback string collected was "Your app crashed when I was..."
                Log.d(Constants.LOG_TAG, "In App Feedback Enabled");
                if (!shown) {
                    CaMDOIntegration.setCustomerFeedback("Your app crashed when I was...");
                }
                shown = true;
            }
        };
        return mMessageReceiver;
    }
}
