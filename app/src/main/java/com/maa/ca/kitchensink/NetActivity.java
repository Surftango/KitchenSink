/**
 * Copyright (C) 2015, CA.  All rights reserved.
 */
package com.maa.ca.kitchensink;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.ca.integration.CaMDOIntegration;

import java.util.Arrays;


public class NetActivity extends BaseActivity {

    Button oneKb, tenKb, hundredKb, oneMB, send;
    RadioButton http, https;
    RadioGroup protocols;
    TextView output;
    EditText urlEntered;
    Spinner callType;
    RadioGroup methods;
    RadioButton get, post;
    String baseGetUrl = "httpbin.org/bytes/";
    String basePostUrl = "httpbin.org/post";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        initializeUI();
        initializeNavigation();
        CaMDOIntegration.registerAppFeedBack(Feedback.getBroadCastReciever());

    }

    private void initializeUI() {
        //Buttons
        oneKb = (Button) findViewById(R.id.btn1Kb);
        tenKb = (Button) findViewById(R.id.btn10Kb);
        hundredKb = (Button) findViewById(R.id.btn100Kb);
        oneMB = (Button) findViewById(R.id.btn1Mb);
        send = (Button) findViewById(R.id.btnSend);
        send.setOnClickListener(this);
        oneKb.setOnClickListener(this);
        tenKb.setOnClickListener(this);
        hundredKb.setOnClickListener(this);
        oneMB.setOnClickListener(this);

        //Url and Output
        urlEntered = (EditText) findViewById(R.id.txtUrl);
        output = (TextView) findViewById(R.id.txtOutput);
        //Protocols
        protocols = (RadioGroup) findViewById(R.id.rbgProtocol);
        http = (RadioButton) findViewById(R.id.rbHttp);
        https = (RadioButton) findViewById(R.id.rbHttps);
        //Methods
        methods = (RadioGroup) findViewById(R.id.rbgMethod);
        get = (RadioButton) findViewById(R.id.rbGet);
        post = (RadioButton) findViewById(R.id.rbPost);
        //Spinner
        callType = (Spinner) findViewById(R.id.spnCallType);

    }


    /**
     * User Click listener
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        NetworkData networkData = getNetworkData(v);
        boolean includeData = false;
        if (!networkData.method.equalsIgnoreCase("GET")) {
            includeData = true;
        }
        networkData.networkCallType = String.valueOf(callType.getSelectedItem());
        switch (v.getId()) {
            case R.id.bNetworkActivity:
                break;
            case R.id.btn1Kb:
                Log.d(Constants.LOG_TAG, networkData.method + " 1KB Request Sent");
                if (!includeData)
                    networkData.requestUrl = networkData.requestUrl + "1024";
                networkData.size = "1Kb";
                if (includeData) networkData.data = getRandomSizedData(1024);
                doNetworkRequest(networkData);
                break;
            case R.id.btn10Kb:
                Log.d(Constants.LOG_TAG, networkData.method + " 10KB Request Sent");
                if (!includeData)
                    networkData.requestUrl = networkData.requestUrl + "10240";
                networkData.size = "10Kb";

                if (includeData) networkData.data = getRandomSizedData(10240);
                doNetworkRequest(networkData);
                break;
            case R.id.btn100Kb:
                Log.d(Constants.LOG_TAG, networkData.method + " of 100KB Request Sent");
                if (!includeData)
                    networkData.requestUrl = networkData.requestUrl + "102400";
                networkData.size = "100Kb";

                if (includeData) networkData.data = getRandomSizedData(102400);
                doNetworkRequest(networkData);
                break;
            case R.id.btn1Mb:
                Log.d(Constants.LOG_TAG, networkData.method + " of  1MB Request Sent");
                if (!includeData)
                    networkData.requestUrl = networkData.requestUrl + "1024000";
                networkData.size = "1Mb";
                if (includeData) networkData.data = getRandomSizedData(1024000);
                doNetworkRequest(networkData);
                break;
            case R.id.btnSend:
                Log.d(Constants.LOG_TAG, networkData.method + " of " + networkData.requestUrl + " Request Sent");
                doNetworkRequest(networkData);
                break;

            default:
                super.onClick(v);

        }

    }

    /**
     * User selected network information into an Object.
     *
     * @param view
     * @return
     */
    private NetworkData getNetworkData(View view) {
        NetworkData network = new NetworkData();
        StringBuilder httpUrl = new StringBuilder(https.isChecked() ? "https://" : "http://");
        network.method = methods.getCheckedRadioButtonId() == get.getId() ? "GET" : "POST";
        httpUrl.append(network.method.equalsIgnoreCase("GET") ? baseGetUrl : basePostUrl);
        if (view.getId() == R.id.btnSend) {
            network.requestUrl = urlEntered.getText().toString();
            //validate String here.
            network.method = "GET";
            network.isCustomUrl = true;
        } else {
            network.requestUrl = httpUrl.toString();
        }
        return network;
    }

    /**
     * invokes async http call.
     *
     * @param networkData
     */
    public void doNetworkRequest(NetworkData networkData) {
        output.setText("Loading .....");
        new ExecuteCall().execute(networkData);
    }


    /**
     * Async Http Request Maker.
     */
    class ExecuteCall extends AsyncTask<NetworkData, String, String> {
        @Override
        protected String doInBackground(NetworkData... params) {
            NetworkData _network = params[0];
            KSHttpClient client = new KSHttpClient(_network);
            String result = client.execute();
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s == null) {
                output.setText("Error! , No Call made -  Please the logs");
            } else {
//                output.setText(s);
                output.setText(Html.fromHtml(s));
                output.setMovementMethod(LinkMovementMethod.getInstance());
            }
        }
    }

    /**
     * Fill a sized char array.
     *
     * @param size
     * @return
     */
    public String getRandomSizedData(int size) {
        char[] data = new char[size];
        Arrays.fill(data, 'a');
        return new String(data);
    }


}
