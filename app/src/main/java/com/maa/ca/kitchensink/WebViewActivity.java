package com.maa.ca.kitchensink;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

//import com.ca.android.app.CaMDOWebView;
import com.ca.integration.CaMDOIntegration;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class WebViewActivity extends BaseActivity {


    Button bYoutube, bTechCrunch, bStackOverflow, bGo, bJSApi;
    ImageButton wvBack, wvForward;
    EditText etWebViewUrlField;
    WebView wvWEbPage;
    //post server data = http://posttestserver.com/data/JS_HOOKS/

    String defaultURl = "http://www.google.com";
    String techCrunchUrl = "http://www.techCrunch.com";
    String jsAPIUrl = "file:///android_asset/jsApi.html";
    String youtubeUrl = "http://www.youtube.com";
    String stackoverflowUrl = "http://www.stackoverflow.com";
    private ViewGroup.LayoutParams orgLayoutParams;
    Handler uiHook;


    @SuppressLint("NewApi")
    public void initialize() {
        bYoutube = (Button) findViewById(R.id.bYoutube);
        bTechCrunch = (Button) findViewById(R.id.bTechCrunch);
        bStackOverflow = (Button) findViewById(R.id.bStackOverFlow);
        bJSApi = (Button) findViewById(R.id.bJSApi);
        bGo = (Button) findViewById(R.id.bGo);
        etWebViewUrlField = (EditText) findViewById(R.id.etWebViewUrlField);
        wvBack = (ImageButton) findViewById(R.id.bBack);
        wvForward = (ImageButton) findViewById(R.id.bForward);
        /*WebView Settings*/
        wvWEbPage = (WebView) findViewById(R.id.wvWebPage);
        //CaMDOWebView.setWebViewClient(wvWEbPage,new camaaWebViewClient());
        wvWEbPage.setWebViewClient(new WebViewClient());
        wvWEbPage.setWebChromeClient(new WebChromeClient());

        wvWEbPage.getSettings().setJavaScriptEnabled(true);

        wvWEbPage.getSettings().setAllowContentAccess(true);
        wvWEbPage.getSettings().setAllowUniversalAccessFromFileURLs(true);
        wvWEbPage.getSettings().setDomStorageEnabled(true);
        wvWEbPage.setVerticalScrollBarEnabled(true);
        wvWEbPage.setHorizontalScrollBarEnabled(true);
        wvWEbPage.addJavascriptInterface(new TempJSInterface(wvWEbPage,uiHook,this), "nativeHook");

        wvWEbPage.loadUrl(defaultURl);

        bYoutube.setOnClickListener(this);
        bTechCrunch.setOnClickListener(this);
        bStackOverflow.setOnClickListener(this);
        bJSApi.setOnClickListener(this);
        bGo.setOnClickListener(this);
        wvBack.setOnClickListener(this);
        wvForward.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHook = new Handler();
        setContentView(R.layout.activity_web_view);
        initialize();
        initializeNavigation();
        CaMDOIntegration.registerAppFeedBack(Feedback.getBroadCastReciever());
    }

    Boolean validateUrl(String url) {
        //String pattern1 = "/^(http(s)?:\\/\\/.)?(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)";
        String pattern2 = "(http:\\/\\/|https:\\/\\/)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?";
        Pattern p = Pattern.compile(pattern2);
        Matcher m = p.matcher(url);
        if (m.find()) {
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bWebViewActivity:
                break;
            case R.id.bYoutube:
                wvWEbPage.loadUrl(youtubeUrl);
                break;
            case R.id.bTechCrunch:
                wvWEbPage.loadUrl(techCrunchUrl);
                break;
            case R.id.bStackOverFlow:
                wvWEbPage.loadUrl(stackoverflowUrl);
                break;
            case R.id.bGo:
                loadPageFromUrl();
                break;
            case R.id.bJSApi:
                wvWEbPage.loadUrl(jsAPIUrl);
                break;
            case R.id.bBack:
                if(wvWEbPage!=null && wvWEbPage.canGoBack()){
                    wvWEbPage.goBack();
                }else{
                    Toast.makeText(WebViewActivity.this,"can't go back",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bForward:
                if(wvWEbPage!=null && wvWEbPage.canGoForward()){
                    wvWEbPage.goForward();
                }else{
                    Toast.makeText(WebViewActivity.this,"can't go forward",Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                super.onClick(v);
        }
    }

    private void loadPageFromUrl() {
        String url = etWebViewUrlField.getText().toString();
        if (validateUrl(url)) {
            wvWEbPage.loadUrl(url);
            Log.d(Constants.LOG_TAG, "loading page with url" + url);
        } else {
            Log.d(Constants.LOG_TAG, "Not a valid url");
            Toast.makeText(WebViewActivity.this,"Not a valid url",Toast.LENGTH_SHORT).show();
        }
    }


    private class camaaWebViewClient extends WebViewClient {



        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            injectScript(view);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
            return super.shouldInterceptRequest(view, request);
        }
    }



    public void injectScript(WebView view){

        try {
            //reading interceptor script once
            InputStream  input = getAssets().open("inject.js");
            byte[] buffer = new byte[input.available()];
            input.read(buffer);
            input.close();
            // String-ify the script byte-array using BASE64 encoding !!!
            String INTERCEPTER_SCRIPT = Base64.encodeToString(buffer, Base64.NO_WRAP);

            String scriptString = "javascript:(function() {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var script = document.createElement('script');" +
                    "script.type = 'text/javascript';" +
                    "script.innerHTML = window.atob('" + INTERCEPTER_SCRIPT + "');" +
                    "parent.appendChild(script)" +
                    "})()";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                view.evaluateJavascript(scriptString,null);
            }
            /*
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                view.evaluateJavascript(scriptString,null);
            }else{
                view.loadUrl(scriptString);
            }*/

        } catch (IOException e) {
            Log.e("","Exception "+e);
        }

    }

}
