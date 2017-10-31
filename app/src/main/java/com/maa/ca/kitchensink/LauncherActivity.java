package com.maa.ca.kitchensink;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

import com.ca.integration.CaMDOIntegration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;


public class LauncherActivity extends Activity {
    ImageView imBackGroundImage;
    AlertDialog alertDialog;
    //Could use Phase instead of CountDownLatch for api > 21
    private CountDownLatch latch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_launcher);
        CaMDOIntegration.registerAppFeedBack(getBroadCastReciever());
        imBackGroundImage = (ImageView) findViewById(R.id.imLauncherView);
        latch = new CountDownLatch(1);
        splashTimer();
        setPermissions();
        proceedToNextScreen();
    }


    /**
     * Proceed to the Next screen, of all the tasks are done.
     */
    private void proceedToNextScreen() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(latch.getCount()>0){
                    try {
                        Thread.sleep(500);
                        Log.d(Constants.LOG_TAG, "waiting for "+latch.getCount()+" latches");
                    } catch (InterruptedException e) {}
                }
                Log.d(Constants.LOG_TAG, "Starting Navigation to next screen from the launcher activity");
                Intent i = new Intent(LauncherActivity.this, CrashActivity.class);
                startActivity(i);
            }
        }).start();
    }

    private BroadcastReceiver getBroadCastReciever() {
        BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                incrementLatch();

                try {
                    Log.d(Constants.LOG_TAG, "In App Feedback Enabled with alert DialogBuilder");
                    LayoutInflater li = LayoutInflater.from(LauncherActivity.this);
                    View promptsView = li.inflate(R.layout.userfeedback, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LauncherActivity.this);
                    alertDialogBuilder.setView(promptsView);
                    final EditText userInput = (EditText) promptsView
                            .findViewById(R.id.editTextDialogUserInput);

                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            CaMDOIntegration.setCustomerFeedback(userInput.getText().toString());
                                            Log.d(Constants.LOG_TAG, "This is string which is being set" + userInput.getText().toString());
                                        }
                                    })
                            .setNegativeButton("Cancel",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            dialog.dismiss();
                                        }
                                    });
                    alertDialog = alertDialogBuilder.create();
                    alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            latch.countDown();
                        }
                    });
                    alertDialog.show();
                    Log.d(Constants.LOG_TAG, "Is alert shown is set to true");
                } catch (Exception e) {
                    latch.countDown();
                }

            }
        };
        return mMessageReceiver;
    }

    /**
     * Registers the Splash screen to wait for 2Sec
     */
    private void splashTimer(){
        new Thread() {
            @Override
            public void run() {
                try {
                    Log.d(Constants.LOG_TAG, "Sleeping Launcher activity for 2 seconds");
                    Thread.sleep(2000);

                } catch (Exception ignore) {
                }
                latch.countDown();
            }
        }.start();
    }

    /**
     * Runtime permission handling
     */
    private void setPermissions() {



        //Location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            incrementLatch();
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    Constants.PERMISSION_CALLBACK);

        }


        //Bluetooth
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            incrementLatch();
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.BLUETOOTH},
                    Constants.PERMISSION_CALLBACK);

        }
    }

    private void incrementLatch() {
        latch = new CountDownLatch((int)latch.getCount()+1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        //Mock implementation
        latch.countDown();
    }
}
