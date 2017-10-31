package com.maa.ca.kitchensink;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ca.integration.CaMDOCallback;
import com.ca.integration.CaMDOIntegration;

/**
 * Created by sharu03 on 8/13/15.
 */
public class BaseActivity extends ActionBarActivity implements View.OnClickListener {

    private Button bCrashActivity, bNetworkActivity, bAPIActivity, bWebViewAcitivity, bEventsActivity, bFragmentActivity, bBluetoothActivity;

    public static String TAG = "BaseActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.global, menu);
        return true;
    }

    CaMDOCallback callback = new CaMDOCallback(new Handler()) {
        @Override
        public void onError(int errorCode, Exception exception) {
            Log.d("KitchenSink", "This is call back from error");
        }

        @Override
        public void onSuccess(Bundle data) {
            Log.d("KitchenSink", "this is call bakc from success");
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.appInfoMenu) {
            Intent intent = new Intent(this, AppInfoActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.action_settings) {
            return true;
        } else if (item.getItemId() == R.id.action_forceUpload) {
            try {
                Log.i(TAG, "calling uploadEvents");
                CaMDOIntegration.uploadEvents(callback);
                Log.i(TAG, "done calling uploadEvents");
            } catch (Exception e) {
                Log.e(TAG, "Error in uploadEvents: " + e);
            }
            return true;
        } else if (item.getItemId() == R.id.action_viewloaded) {
            try {
                Log.i(TAG, "calling viewloaded");
                CaMDOIntegration.viewLoaded("test", 10, callback);
                Log.i(TAG, "done calling viewloaded");
            } catch (Exception e) {
                Log.e(TAG, "Error in viewloaded: " + e);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Action Bar Navigation
     */
    protected void initializeNavigation() {
        /**Activity based buttons*/
        Log.d(Constants.LOG_TAG, "Initializing the Navigation for Crash Activity");
        bCrashActivity = (Button) findViewById(R.id.bCrashActivity);
        bAPIActivity = (Button) findViewById(R.id.bAPIsActivity);
        bWebViewAcitivity = (Button) findViewById(R.id.bWebViewActivity);
        bEventsActivity = (Button) findViewById(R.id.bEventsActivity);
        bNetworkActivity = (Button) findViewById(R.id.bNetworkActivity);
        bFragmentActivity = (Button) findViewById(R.id.bFragmentActivity);
        bBluetoothActivity = (Button)findViewById(R.id.bBluetoothActivity);
        bCrashActivity.setOnClickListener(this);
        bAPIActivity.setOnClickListener(this);
        bWebViewAcitivity.setOnClickListener(this);
        bEventsActivity.setOnClickListener(this);
        bNetworkActivity.setOnClickListener(this);
        bFragmentActivity.setOnClickListener(this);
        bBluetoothActivity.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bCrashActivity:
                Intent crashViewIntent = new Intent(this, CrashActivity.class);
                startActivity(crashViewIntent);
                Log.d(Constants.LOG_TAG, "Opening WebView Activity from CrashActivity");
                break;
            case R.id.bWebViewActivity:
                Intent webViewIntent = new Intent(this, WebViewActivity.class);
                startActivity(webViewIntent);
                Log.d(Constants.LOG_TAG, "Opening WebView Activity from CrashActivity");
                break;
            case R.id.bAPIsActivity:
                Intent apiIntent = new Intent(this, APISActivity.class);
                startActivity(apiIntent);
                Log.d(Constants.LOG_TAG, "Opening APIS Activity from CrashActivity");
                break;
            case R.id.bNetworkActivity:
                Intent networkIntent = new Intent(this, NetActivity.class);
                startActivity(networkIntent);
                Log.d(Constants.LOG_TAG, "Opening Network Activity from CrashActivity");
                break;
            case R.id.bEventsActivity:
                Intent eventIntent = new Intent(this, EventsActivity.class);
                startActivity(eventIntent);
                Log.d(Constants.LOG_TAG, "Opening Events Activity from CrashActivity");
                break;
            case R.id.bFragmentActivity: {
                Intent fragIntent = new Intent(this, FragmentActivity.class);
                startActivity(fragIntent);
                Log.d(Constants.LOG_TAG, "Opening Fragment Activity");
                break;
            }
            case R.id.bBluetoothActivity:
                Intent bluetoothIntent = new Intent(this, BluetoothActivity.class);
                startActivity(bluetoothIntent);
                Log.d(Constants.LOG_TAG, "Opening Bluetooth Activity");
                break;
        }
        finish();
    }
}
