package com.maa.ca.kitchensink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.maa.ca.kitchensink.Bluetooth.DeviceScanActivity;

public class BluetoothActivity extends BaseActivity {

    Button bBluetoothScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        Initialize();
	initializeNavigation();
    }

    private void Initialize() {
       bBluetoothScan = (Button) findViewById(R.id.bBluetoothScan);
       bBluetoothScan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bBluetoothScan:
                Intent i = new Intent(this, DeviceScanActivity.class);
                startActivity(i);
                break;
            default:
                super.onClick(v);
        }
    }
}
