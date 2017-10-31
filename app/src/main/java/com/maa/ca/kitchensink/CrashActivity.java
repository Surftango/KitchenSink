package com.maa.ca.kitchensink;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ca.integration.CaMDOIntegration;
import com.maa.ca.kitchensink.utils.StartTimeGrabber;

public class CrashActivity extends BaseActivity{


    Button bUncaughtException, bSegmentationFault, bStackOverflow, bArrayOutOfIndex;
    ImageButton bInfoCrashes, bInfoInAppFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);
        UIInitialize();
        initializeNavigation();
        CaMDOIntegration.registerAppFeedBack(Feedback.getBroadCastReciever());
    }


    private void UIInitialize() {
        Log.d(Constants.LOG_TAG, "Initializing UI Elements for Crash Activity");
        bSegmentationFault = (Button) findViewById(R.id.bSegmentationFault);
        bStackOverflow = (Button) findViewById(R.id.bStackOverFlowError);
        bUncaughtException = (Button) findViewById(R.id.bUncaughtException);
        bArrayOutOfIndex = (Button) findViewById(R.id.bArrayOutOfIndex);
        bInfoCrashes = (ImageButton) findViewById(R.id.ibInfoCrashes);
        bInfoInAppFeedback = (ImageButton) findViewById(R.id.ibInfoInAppFeedback);

        bSegmentationFault.setOnClickListener(this);
        bStackOverflow.setOnClickListener(this);
        bUncaughtException.setOnClickListener(this);
        bArrayOutOfIndex.setOnClickListener(this);
        bInfoCrashes.setOnClickListener(this);
        bInfoInAppFeedback.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bCrashActivity:
                break;
            case R.id.bUncaughtException:
                //new StartTimeGrabber(CrashActivity.this).execute();
                createUnCaughtException();
                break;
            case R.id.bArrayOutOfIndex:
                createArrayOutOfIndex();
                break;
            case R.id.bSegmentationFault:
                createSegmentationFault();
                break;
            case R.id.bStackOverFlowError:
                createStackOverflow();
                break;

            case R.id.ibInfoCrashes:
                Toast.makeText(getApplicationContext(), "Simulate the Crashes by clicking \n" +
                        "on the buttons in crash section", Toast.LENGTH_LONG).show();
                break;
            case R.id.ibInfoInAppFeedback:
                Toast.makeText(getApplicationContext(), "The SDK provides a mechanism to relay info that 'previous session crashed' to the App Developer.\n" +
                        " This information can be used to provide mechanism to collect information from user", Toast.LENGTH_LONG).show();
                break;
            default:
                super.onClick(v);
        }

    }


    /**
     * Crash creating methods
     */
    private void createUnCaughtException() throws UnknownError {
        Log.d(Constants.LOG_TAG, "Creating UnCaught Exception");
        throw new UnknownError();

    }

    private void createArrayOutOfIndex() throws ArrayIndexOutOfBoundsException {
        Log.d(Constants.LOG_TAG, "Creating Array out of index error");
        throw new ArrayIndexOutOfBoundsException();
    }

    private void createSegmentationFault() throws IllegalAccessError {
        Log.d(Constants.LOG_TAG, "Creating Segmentation Fault");
        throw new IllegalAccessError();

    }

    private void createStackOverflow() throws StackOverflowError {
        Log.d(Constants.LOG_TAG, "Creating StackOverFlow");
        throw new StackOverflowError();
    }

}
