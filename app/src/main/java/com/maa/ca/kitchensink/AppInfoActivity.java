package com.maa.ca.kitchensink;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class AppInfoActivity extends ActionBarActivity {

    private TextView mVersionLabel;
    private TextView mVersionValText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
        mVersionLabel = (TextView) findViewById(R.id.tvLabelVersion);
        mVersionValText = (TextView) findViewById(R.id.tvVersionVal);
        try {
            String versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            mVersionValText.setText(versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


}
