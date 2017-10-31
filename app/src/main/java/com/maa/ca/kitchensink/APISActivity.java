package com.maa.ca.kitchensink;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.ca.integration.CaMDOCallback;
import com.ca.integration.CaMDOIntegration;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;


public class APISActivity extends BaseActivity {

    Button bSetCustomerId, bSetCustomerLocation, bAddSessionEvent, bAddSessionInfo, bEnableSDK, bDisableSDK,
            bStartApplicationTransaction, bStopApplicationTransaction, bLogin, bStopSession, bStartSession, bStopAndStartSession, btPinSsl,
            btRestSSLPinning, btSetBulkSSLPinning, userFeedBack, getPinnedUrl;

    Spinner etSessionEventDatatype, feedbackTypeSpinner;

    EditText etCustomerID, etCustomerLocationCountry, etCustomerLocationZipCode,
            etSessionEventValue, etSessionEventName, etSessionInfoValue, etSessionInfoName, etSessionInfoDataType,
            etTransactionServiceName, etApplicationTransaction, etApplicationTransactionFailureStringName, etUserName, etPassword,
            pinCert, bulkPinCert, feedbackText, urlToCheckForPinning;
    ImageButton ibCustomerIDInfo, ibCustomerLocationInfo, ibSessionEvent, ibSessionInfo, ibStartApplicationTransaction;
    private Handler uiHook;
    Button bLogNetworkEvent, bUploadEvents, btsendScreenShot;
    EditText etNetworkDataOut, etNetworkDataIn, etNetworkResponseTime, etNetworkStatusCode, etNetworkURL, etSendScreenShotName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHook = new Handler();
        setContentView(R.layout.activity_apis);
        Initialize();
        initializeNavigation();
        CaMDOIntegration.registerAppFeedBack(Feedback.getBroadCastReciever());

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


    private void Initialize() {
        bLogNetworkEvent = (Button) findViewById(R.id.bLogNetworkEvent);
        bUploadEvents = (Button) findViewById(R.id.bUploadEvents);
        btsendScreenShot = (Button) findViewById(R.id.btSendScreenShot);

        bSetCustomerId = (Button) findViewById(R.id.bSetCustomerId);
        bSetCustomerLocation = (Button) findViewById(R.id.bSetCustomerLocation);
        bAddSessionEvent = (Button) findViewById(R.id.bAddSessionEvent);
        bAddSessionInfo = (Button) findViewById(R.id.bAddSessionInfo);
        bEnableSDK = (Button) findViewById(R.id.bEnableSDK);
        bDisableSDK = (Button) findViewById(R.id.bDisEnableSDK);
        bStartApplicationTransaction = (Button) findViewById(R.id.bStartTransactionApplication);
        bStopApplicationTransaction = (Button) findViewById(R.id.bEndTransaction);
        bLogin = (Button) findViewById(R.id.bLogin);
        bStopSession = (Button) findViewById(R.id.bStopSession);
        bStartSession = (Button) findViewById(R.id.bStartSession);
        bStopAndStartSession = (Button) findViewById(R.id.bStopAndStartSession);
        ibCustomerIDInfo = (ImageButton) findViewById(R.id.ibCustomerIDInfo);
        ibCustomerLocationInfo = (ImageButton) findViewById(R.id.ibCustomerLocationInfo);
        ibSessionEvent = (ImageButton) findViewById(R.id.ibSessionEvent);
        ibSessionInfo = (ImageButton) findViewById(R.id.ibSessionInfo);
        ibStartApplicationTransaction = (ImageButton) findViewById(R.id.ibStartApplicationTransactionInfo);
        btPinSsl = (Button) findViewById(R.id.btSetSSLPinning);
        btRestSSLPinning = (Button) findViewById(R.id.btRestSSLPinning);
        btSetBulkSSLPinning = (Button) findViewById(R.id.btSetBulkSSLPinning);
        userFeedBack = (Button) findViewById(R.id.userFeedBack);
        getPinnedUrl = (Button) findViewById(R.id.getPinnedUrl);

        etCustomerID = (EditText) findViewById(R.id.etCustomerID);
        etCustomerLocationCountry = (EditText) findViewById(R.id.etCustomerLocationCountry);
        etCustomerLocationZipCode = (EditText) findViewById(R.id.etCustomerLocationZipCode);
        etSessionEventDatatype = (Spinner) findViewById(R.id.etSessionEventDatatype);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.custom_event_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etSessionEventDatatype.setAdapter(adapter);


        feedbackTypeSpinner = (Spinner) findViewById(R.id.feedback_type);
        ArrayAdapter<CharSequence> feedbackType = ArrayAdapter.createFromResource(this,
                R.array.feedback_types, android.R.layout.simple_spinner_item);
        feedbackType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        feedbackTypeSpinner.setAdapter(feedbackType);

        etSessionEventValue = (EditText) findViewById(R.id.etSessionEventValue);
        etSendScreenShotName = (EditText) findViewById(R.id.etSendScreenShotName);

        etNetworkDataOut = (EditText) findViewById(R.id.etNetworkDataOut);
        etNetworkDataIn = (EditText) findViewById(R.id.etNetworkDataIn);
        etNetworkResponseTime = (EditText) findViewById(R.id.etNetworkResponseTime);
        etNetworkStatusCode = (EditText) findViewById(R.id.etNetworkStatusCode);
        etNetworkURL = (EditText) findViewById(R.id.etNetworkURL);

        etSessionEventName = (EditText) findViewById(R.id.etSessionEventName);
        etSessionInfoValue = (EditText) findViewById(R.id.etSessionInfoValue);
        etSessionInfoName = (EditText) findViewById(R.id.etSessionInfoName);
        etSessionInfoDataType = (EditText) findViewById(R.id.etSessionInfoDatatype);
        etSessionInfoDataType.setText(CaMDOIntegration.CAMAA_STRING);
        etUserName = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etApplicationTransaction = (EditText) findViewById(R.id.etApplicationTransactionName);
        etApplicationTransactionFailureStringName = (EditText) findViewById(R.id.etApplicationTransactionFailureStringName);
        etTransactionServiceName = (EditText) findViewById(R.id.etTransactionServiceName);
        pinCert = (EditText) findViewById(R.id.etPEMCertificate);
        bulkPinCert = (EditText) findViewById(R.id.etBulkPEMCertificate);
        feedbackText = (EditText) findViewById(R.id.feedbackText);
        urlToCheckForPinning = (EditText) findViewById(R.id.urlToCheckForPinning);

        bSetCustomerId.setOnClickListener(this);
        bSetCustomerLocation.setOnClickListener(this);
        bAddSessionEvent.setOnClickListener(this);
        bAddSessionInfo.setOnClickListener(this);
        bEnableSDK.setOnClickListener(this);
        bDisableSDK.setOnClickListener(this);
        bStartApplicationTransaction.setOnClickListener(this);
        bStopApplicationTransaction.setOnClickListener(this);
        bLogin.setOnClickListener(this);
        bStopSession.setOnClickListener(this);
        bStartSession.setOnClickListener(this);
        bStopAndStartSession.setOnClickListener(this);
        btRestSSLPinning.setOnClickListener(this);
        btSetBulkSSLPinning.setOnClickListener(this);
        userFeedBack.setOnClickListener(this);
        getPinnedUrl.setOnClickListener(this);


        ibCustomerIDInfo.setOnClickListener(this);
        ibCustomerLocationInfo.setOnClickListener(this);
        ibSessionEvent.setOnClickListener(this);
        ibSessionInfo.setOnClickListener(this);
        ibStartApplicationTransaction.setOnClickListener(this);


        bLogNetworkEvent.setOnClickListener(this);
        bUploadEvents.setOnClickListener(this);
        btsendScreenShot.setOnClickListener(this);
        btPinSsl.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bAPIsActivity:
                break;

            case R.id.bSetCustomerId:
                String customerId = etCustomerID.getText().toString();
                //if (customerId.length() > 0) {
                    CaMDOIntegration.setCustomerId(customerId);
                //}
                break;
            case R.id.bSetCustomerLocation:
                String customerCountry = etCustomerLocationCountry.getText().toString();
                String customerZipCode = etCustomerLocationZipCode.getText().toString();

                if (customerCountry.length() > 0 && customerZipCode.length() > 0) {
                    CaMDOIntegration.setCustomerLocation(customerZipCode, customerCountry);
                }
                break;
            case R.id.bAddSessionEvent:
                try {
                    int pos = etSessionEventDatatype.getSelectedItemPosition();

                    String custName = etSessionEventName.getText().toString();
                    if(pos==0){
                        String custVal = etSessionEventValue.getText().toString();
                        CaMDOIntegration.logTextMetric(custName,custVal,null,null);
                    }else{
                        Double custVal = Double.parseDouble(etSessionEventValue.getText().toString());
                        CaMDOIntegration.logNumericMetric(custName,custVal,null,null);
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(this,"Error in input. Try again with proper values ",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.bAddSessionInfo:
                String sessionInfoValue = etSessionInfoValue.getText().toString();
                String sessionInfoName = etSessionInfoName.getText().toString();
                CaMDOIntegration.setSessionAttribute(sessionInfoName, sessionInfoValue);
                break;
            case R.id.bEnableSDK:
                CaMDOIntegration.enableSDK();
                break;
            case R.id.bDisEnableSDK:
                CaMDOIntegration.disableSDK();
                break;
            case R.id.bStartTransactionApplication:
                String applicationTransactionName = etApplicationTransaction.getText().toString();
                String transactionServiceName = etTransactionServiceName.getText().toString();
                Log.v("test", "transaction service name is " + transactionServiceName);
                if (applicationTransactionName.length() > 0 && transactionServiceName.length() > 0) {
                    CaMDOIntegration.startApplicationTransaction(applicationTransactionName, transactionServiceName,
                            callback);
                } else {
                    Log.v("test", "inside the else loop" + transactionServiceName);
                    if (applicationTransactionName.length() > 0) {
                        CaMDOIntegration.startApplicationTransaction(applicationTransactionName, getApplicationName(this),
                                callback);
                    } else {
                        CaMDOIntegration.startApplicationTransaction(getApplicationName(this),
                                callback);
                    }
                }
                break;
            case R.id.bLogin:
                Intent loginIntent = new Intent(this, TransactionActivity.class);
                startActivity(loginIntent);
                break;
            case R.id.bEndTransaction:
                String applicationTransactionName1 = etApplicationTransaction.getText().toString();
                String stopApplicationFailureName = etApplicationTransactionFailureStringName.getText().toString();
                if (applicationTransactionName1.length() > 0 && stopApplicationFailureName.length() > 0) {
                    CaMDOIntegration.stopApplicationTransaction(applicationTransactionName1, stopApplicationFailureName,
                            callback);
                } else if (applicationTransactionName1.length() > 0) {
                    CaMDOIntegration.stopApplicationTransaction(applicationTransactionName1,
                            callback);
                }
                break;
            case R.id.bLogNetworkEvent:
                String networkURL = etNetworkURL.getText().toString();
                String networkStatus = etNetworkStatusCode.getText().toString();
                String networkResponseTime = etNetworkResponseTime.getText().toString();
                String networkDataIn = etNetworkDataIn.getText().toString();
                String networkDataOut = etNetworkDataOut.getText().toString();
                if (networkURL.length() > 0 && networkURL.length() > 0 && networkURL.length() > 0 && networkURL.length() > 0 && networkURL.length() > 0) {
                    CaMDOIntegration.logNetworkEvent(networkURL, Integer.parseInt(networkStatus), Integer.parseInt(networkResponseTime),
                            Integer.parseInt(networkDataIn), Integer.parseInt(networkDataOut),
                            callback);
                } else {
                    Log.v("test", "Please provide all the input fields");
                }
                break;
            case R.id.bUploadEvents:
                CaMDOIntegration.uploadEvents(callback);
                break;
            case R.id.btSendScreenShot:
                String screenName = etSendScreenShotName.getText().toString();
                if (screenName.length() > 0) {
                    CaMDOIntegration.sendScreenShot(screenName, CaMDOIntegration.CAMAA_SCREENSHOT_QUALITY_HIGH, callback);
                } else {
                    CaMDOIntegration.sendScreenShot("screenName", CaMDOIntegration.CAMAA_SCREENSHOT_QUALITY_DEFAULT, callback);
                }
                break;
            case R.id.bStopSession: {
                CaMDOIntegration.stopCurrentSession();
            }
            break;
            case R.id.bStartSession: {
                CaMDOIntegration.startNewSession();
            }
            break;
            case R.id.bStopAndStartSession: {
                CaMDOIntegration.stopCurrentAndStartNewSession();
            }
            break;

            case R.id.ibCustomerIDInfo:
                Toast.makeText(getApplicationContext(), "CAMobileDevOps.setSessionAttribute(String key, String value);", Toast.LENGTH_LONG).show();
                break;
            case R.id.ibCustomerLocationInfo:
                Toast.makeText(getApplicationContext(), "Map<String, String > customerLocMap = new HashMap<String, String>();\n" +
                        "customerLocMap.put(CAMobileDevOps.CAMAA_HEADER_ZIPCODE, \"94538\");\n" +
                        "customerLocMap.put(CAMobileDevOps.CAMAA_HEADERCOUNTRY_CODE, \"US\");\n" +
                        "CAMobileDevOps.setCustomerLocation(customerLocMap)", Toast.LENGTH_LONG).show();
                break;
            case R.id.ibSessionEvent:
                Toast.makeText(getApplicationContext(), "CAMobileDevOps.logTextMetric(String name, String value, Map<String, String> attributes, CaMDOCallback callback);\n"+
                        "CAMobileDevOps.logNumericMetric(String name, Double value, Map<String, String> attributes, CaMDOCallback callback)", Toast.LENGTH_LONG).show();
                break;
            case R.id.ibSessionInfo:
                Toast.makeText(getApplicationContext(), "CAMobileDevOps.setSessionInfo(CAMobileDevOps.CAMAA_CUSTOMER_ID, “phone_number\", \"4085551212”);", Toast.LENGTH_LONG).show();
                break;
            case R.id.ibStartApplicationTransactionInfo:
                Toast.makeText(getApplicationContext(), "CAMobileDevOps.startApplicationTransaction(\"\"Shop - Add Item To Cart\");" +
                        "start Application Transaction by giving some transaction name, login and generate some events and end application transaction", Toast.LENGTH_LONG).show();
                break;

            case R.id.btSetSSLPinning:
                String[] certToPin = new String[1];
                certToPin[0] = pinCert.getText().toString().trim();
                if(certToPin[0].length()!=0){
                    ArrayList<byte[]> certData = getCertData(certToPin);
                    if(certData!=null){
                        CaMDOIntegration.setSSLPinningMode(getApplication(),CaMDOIntegration.CAMAA_SSL_PINNINGMODE_CERTIFICATE,certData.get(0));
                    }else{
                        Toast.makeText(getApplicationContext(), "Cant find Cert info from the specified assets cert file "+certToPin[0], Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Please provide certificate data", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.btSetBulkSSLPinning:
                String[] certsToPin = bulkPinCert.getText().toString().trim().split(",");
                if(certsToPin.length!=0){
                    ArrayList<byte[]> certsData = getCertData(certsToPin);
                    if(certsData!=null){
                        CaMDOIntegration.setSSLPinningMode(getApplication(),CaMDOIntegration.CAMAA_SSL_PINNINGMODE_CERTIFICATE,certsData);
                    }else{
                        Toast.makeText(getApplicationContext(), "Cant find Cert info from the specified assets cert file(s) ", Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Please provide certificate data", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.getPinnedUrl:
                String urlStringToCheckForPinning = urlToCheckForPinning.getText().toString().trim();
                final HttpURLConnection connection = getSDKInternalConnection(urlStringToCheckForPinning);
                if(connection == null){
                    Toast.makeText(APISActivity.this,"Error getting SDKInternalConnection ", Toast.LENGTH_LONG).show();
                }else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                int statusCode = connection.getResponseCode();
                                showMessage("URL returned with status code "+statusCode);

                            } catch (IOException e) {
                                showMessage("URL returned error "+e);
                            }
                        }
                    }).start();
                }
                break;


            case R.id.btRestSSLPinning:
                CaMDOIntegration.setSSLPinningMode(getApplication(),CaMDOIntegration.CAMAA_SSL_PINNINGMODE_NONE,new ArrayList<byte[]>());
                break;

            case R.id.userFeedBack:
                int pos = feedbackTypeSpinner.getSelectedItemPosition();
                String feedBackVal = feedbackText.getText().toString().trim();
                if(feedBackVal.length()!=0){
                    if(pos==0){
                        CaMDOIntegration.setUserFeedback(feedBackVal);
                    }else{
                        CaMDOIntegration.setCrashFeedback(feedBackVal);
                    }

                }else{
                    Toast.makeText(APISActivity.this,"Empty feedback text", Toast.LENGTH_LONG).show();
                }
                break;

            default:
                super.onClick(v);
        }

    }

    private HttpURLConnection getSDKInternalConnection(String urlStringToCheckForPinning) {
        try {

            HttpsURLConnection urlConnection = (HttpsURLConnection) new URL(urlStringToCheckForPinning).openConnection();
            urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
            Class sdkInternal = Class.forName("com.ca.mdo.MDOSSLPinning");
            Method applyPinning = sdkInternal.getDeclaredMethod("applyPinning", HttpsURLConnection.class);
            applyPinning.invoke(null, urlConnection);
            return urlConnection;

        }catch (Exception e){
            Log.e("APISActivity","Error accesing  getSDKInternalConnection "+e);
        }
        return null;
    }

    protected static String getApplicationName(Context ctx) {
        final PackageManager pm = ctx.getPackageManager();
        ApplicationInfo ai;
        try {
            ai = pm.getApplicationInfo(ctx.getPackageName(), 0);
        } catch (final PackageManager.NameNotFoundException e) {
            ai = null;
        }
        return (String) (ai != null ? pm.getApplicationLabel(ai) : "unknown");

    }


    private ArrayList<byte[]> getCertData(String[] certsPath){

        try {
            ArrayList<byte[]> returnVal = new ArrayList<>();
            for(String certPath : certsPath){
                BufferedInputStream certData=new BufferedInputStream(getAssets().open(certPath));
                byte[] data = new byte[certData.available()];
                certData.read(data);
                certData.close();
                returnVal.add(data);
            }
            return returnVal;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  void showMessage(final String msg){
        if(uiHook!=null){
            uiHook.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(APISActivity.this,msg,Toast.LENGTH_LONG).show();
                }
            });
        }

    }

}
