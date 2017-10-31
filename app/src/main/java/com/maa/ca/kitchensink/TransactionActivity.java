package com.maa.ca.kitchensink;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.ca.integration.CaMDOIntegration;


public class TransactionActivity extends BaseActivity {


    Button bPingGoogle, bEventGenerator;
    Switch bSwitch;
    SeekBar seekBar;
    TextView tvOutput;

    private void initialise() {
        Log.d(Constants.LOG_TAG, "Initialising the UI Elements from the transaction activity");
        bPingGoogle = (Button) findViewById(R.id.bPingGoogle);
        bEventGenerator = (Button) findViewById(R.id.bEventGenerator);
        bSwitch = (Switch) findViewById(R.id.bSwitch);
        seekBar = (SeekBar) findViewById(R.id.sbSeekBar);
        tvOutput = (TextView) findViewById(R.id.tvOutput);
        bPingGoogle.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        initialise();
        initializeNavigation();
        CaMDOIntegration.registerAppFeedBack(Feedback.getBroadCastReciever());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bPingGoogle:
                pingGoogle();
                break;
            default:
                super.onClick(v);
        }
    }

    private void pingGoogle() {


    }
}
