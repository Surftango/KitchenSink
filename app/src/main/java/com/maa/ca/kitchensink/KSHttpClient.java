/**
 * Copyright (C) 2015, CA.  All rights reserved.
 */
package com.maa.ca.kitchensink;

import android.net.http.AndroidHttpClient;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Chaitanya Kannali on 5/19/15.
 * Implement methods from Android Http Client , Apache Http Client and Http URL Connection
 */
public class KSHttpClient {

    public NetworkData _network;


    public KSHttpClient(NetworkData data) {
        this._network = data;
    }


    /**
     * Public interface to execute network calls
     *
     * @return
     */
    public String execute() {
        StringBuilder response = new StringBuilder("Success of ").append(_network).append(":");
//        Log.d(Constants.LOG_TAG,_network.data.getBytes().length+" is size of data.........");
        try {
            switch (_network.networkCallType) {
                case "androidHttpClientExecute":
                    response.append(androidHttpClientExecute());
                    break;
                case "abstractHttpClientExecute":
                    response.append(abstractHttpClientExecute());
                    break;
                case "defaultHttpClientExecute":
                    response.append(defaultHttpClientExecute());
                    break;
                case "httpClientExecuteHostRequestContext":
                    response.append(httpClientExecuteHostRequestContext());
                    break;
                case "httpClientExecuteHostRequest":
                    response.append(httpClientExecuteHostRequest());
                    break;
                case "httpClientExecuteRequestContext":
                    response.append(httpClientExecuteRequestContext());
                    break;
                case "httpClientExecuteRequest":
                    response.append(httpClientExecuteHostRequest());
                    break;
                case "httpUrlConnection":
                    response.append(httpUrlConnection());
                    break;
                case "urlConnection":
                    response.append(urlConnection());
                    break;
                case "okhttpConnection":
                    response.append(okHttpConnection());
                    break;
                default:
                    response = new StringBuilder("Failure - network Call Type not selected");
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            response = new StringBuilder("Failure of ").append(_network).append("  Reason is -  ").append(e.toString());
        }
        return response.toString();
    }


    /**
     * AndroidHttpClient with Execute of Request.
     *
     * @return
     * @throws IOException
     */
    private String androidHttpClientExecute() throws IOException {
        AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
        HttpUriRequest request = getHttpUriRequest();
        HttpResponse response = client.execute(request);
        String resp = EntityUtils.toString(response.getEntity());
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            int tries = 10;
            while (statusCode != HttpStatus.SC_OK && tries < 11) {
                Header[] headers = response.getHeaders("Location");
                if (headers != null && headers.length != 0) {
                    String newUrl =
                            headers[headers.length - 1].getValue();
                    if (newUrl != null && newUrl.startsWith("http"))
                        _network.requestUrl = newUrl;
                    else
                        _network.requestUrl = _network.requestUrl + newUrl;
                    request = getHttpUriRequest();
                    response = client.execute(request);
                    statusCode = response.getStatusLine().getStatusCode();
                    resp = EntityUtils.toString(response.getEntity());
                }
                tries++;
            }
        }
        String returned = isFailed(statusCode) ? getFailedStatusHtml(resp, statusCode) : getSuccessResponseHtml(statusCode);
        client.close();
        return returned;

    }

    /**
     * AbstractHttpClient with Execute of Request,Context
     *
     * @return
     * @throws IOException
     */
    private String abstractHttpClientExecute() throws IOException {
        AbstractHttpClient client = new DefaultHttpClient();
        HttpContext context = new BasicHttpContext();
        HttpUriRequest request = getHttpUriRequest();
        HttpResponse response = client.execute(request, context);
        String resp = EntityUtils.toString(response.getEntity());
        int statusCode = response.getStatusLine().getStatusCode();
        return isFailed(statusCode) ? getFailedStatusHtml(resp, statusCode) : getSuccessResponseHtml(statusCode);

    }


    /**
     * DefaultHttpClient with execute of Request
     *
     * @return
     * @throws IOException
     */
    private String defaultHttpClientExecute() throws IOException {
        DefaultHttpClient client = new DefaultHttpClient();
        HttpUriRequest request = getHttpUriRequest();
        HttpResponse response = client.execute(request);
        String resp = EntityUtils.toString(response.getEntity());
        int statusCode = response.getStatusLine().getStatusCode();
        return isFailed(statusCode) ? getFailedStatusHtml(resp, statusCode) : getSuccessResponseHtml(statusCode);

    }


    /**
     * Http Client Execute with Host,Request,Context
     *
     * @return
     * @throws IOException
     */
    private String httpClientExecuteHostRequestContext() throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpHost host = new HttpHost(_network.getHostName(), _network.getPort());
        HttpRequest request = getHttpRequest();
        HttpContext context = new BasicHttpContext();
        HttpResponse response = client.execute(host, request, context);
        String resp = EntityUtils.toString(response.getEntity());
        int statusCode = response.getStatusLine().getStatusCode();
        return isFailed(statusCode) ? getFailedStatusHtml(resp, statusCode) : getSuccessResponseHtml(statusCode);

    }


    /**
     * Http Client Execute with Host , Request.
     *
     * @return
     * @throws IOException
     */
    private String httpClientExecuteHostRequest() throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpHost host = new HttpHost(_network.getHostName(), _network.getPort());
        HttpRequest request = getHttpRequest();
        HttpResponse response = client.execute(host, request);
        String resp = EntityUtils.toString(response.getEntity());
        int statusCode = response.getStatusLine().getStatusCode();
        return isFailed(statusCode) ? getFailedStatusHtml(resp, statusCode) : getSuccessResponseHtml(statusCode);

    }


    /**
     * Http Execute Client with Request and Context
     *
     * @return
     * @throws IOException
     */
    private String httpClientExecuteRequestContext() throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpContext context = new BasicHttpContext();
        HttpUriRequest request = getHttpUriRequest();
        HttpResponse response = client.execute(request, context);
        String resp = EntityUtils.toString(response.getEntity());
        int statusCode = response.getStatusLine().getStatusCode();
        return isFailed(statusCode) ? getFailedStatusHtml(resp, statusCode) : getSuccessResponseHtml(statusCode);

    }


    /**
     * HttpClient execute Request
     *
     * @return
     * @throws IOException
     */
    private String httpClientExecuteRequest() throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpUriRequest request = getHttpUriRequest();
        HttpResponse response = client.execute(request);
        ;
        String resp = EntityUtils.toString(response.getEntity());
        int statusCode = response.getStatusLine().getStatusCode();
        return isFailed(statusCode) ? getFailedStatusHtml(resp, statusCode) : getSuccessResponseHtml(statusCode);

    }


    private String httpUrlConnection() throws IOException {
        StringBuffer response = new StringBuffer("");
        if (_network.method.equalsIgnoreCase("GET")) {
            response.append(getUsingHttpUrlConnection());
        } else {
            response.append(postUsingHttpUrlConnection());

        }
        return response.toString();
    }

    private String okHttpConnection() throws IOException {
        StringBuffer response = new StringBuffer("");
        if (_network.method.equalsIgnoreCase("GET")) {
            response.append(getUsingokHttpConnection());
        } else {
            response.append(postUsingokHttpConnection());

        }
        return response.toString();
    }

    private String urlConnection() throws IOException {
        StringBuffer response = new StringBuffer("");
        if (_network.method.equalsIgnoreCase("GET")) {
            response.append(getUsingUrlConnection());
        } else {
            response.append(postUsingUrlConnection());

        }
        return response.toString();
    }

    /**
     * Get method using HttpUrlConnection.
     *
     * @return
     * @throws IOException
     */
    public String getUsingHttpUrlConnection() throws IOException {
        URL url = new URL(_network.requestUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setInstanceFollowRedirects(true);
        connection.setRequestMethod("GET");
        connection.connect();
        String resp = readStream(connection.getInputStream());
        int statusCode = connection.getResponseCode();
        return isFailed(statusCode) ? getFailedStatusHtml(resp, statusCode) : getSuccessResponseHtml(statusCode);

    }


    /**
     * Post method using UrlConnection.
     *
     * @return
     * @throws IOException
     */
    public String postUsingHttpUrlConnection() throws IOException {
        URL url = new URL(_network.requestUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setInstanceFollowRedirects(false);
        conn.setRequestMethod("POST");
        conn.setUseCaches(false);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(baos);
        osw.write(_network.data);
        int statusCode = conn.getResponseCode();
        String resp = readStream(conn.getInputStream());
        return isFailed(statusCode) ? getFailedStatusHtml(resp, statusCode) : getSuccessResponseHtml(statusCode);
    }


    /**
     * Get method using UrlConnection.
     *
     * @return
     * @throws IOException
     */
    public String getUsingUrlConnection() throws IOException {
        URL url = new URL(_network.requestUrl);
        URLConnection connection = (URLConnection) url.openConnection();
        connection.connect();
        String resp = readStream(connection.getInputStream());
        return getSuccessResponseHtml(resp);

    }

    /**
     * Get method using UrlConnection.
     *
     * @return
     * @throws IOException
     */
    public String getUsingokHttpConnection() throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        ;

//        RequestBody body = RequestBody.create(MediaType.parse("application/text; charset=utf-8"),_network.data);
        Request request = new Request.Builder()
                .url(_network.requestUrl)
                .build();

        Response response = client.newCall(request).execute();
        return response.toString();
    }

    /**
     * Post method using HttpUrlConnection.
     *
     * @return
     * @throws IOException
     */
    public String postUsingokHttpConnection() throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        ;

        RequestBody body = RequestBody.create(MediaType.parse("application/text; charset=utf-8"), _network.data);
        Request request = new Request.Builder()
                .url(_network.requestUrl)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        return response.toString();
    }

    /**
     * Post method using HttpUrlConnection.
     *
     * @return
     * @throws IOException
     */
    public String postUsingUrlConnection() throws IOException {
        URL url = new URL(_network.requestUrl);
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(baos);
        osw.write(_network.data);
        osw.flush();
        String resp = readStream(conn.getInputStream());
        return getSuccessResponseHtml(resp);
    }


    /**
     * ReadStream from connection.
     *
     * @param in
     */
    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer content = new StringBuffer("");

        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

    /**
     * Helper method to get HttpUriRequest from initialized Network Data Object
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    private HttpUriRequest getHttpUriRequest() throws UnsupportedEncodingException {
        HttpUriRequest request = null;
        if (_network.method.equalsIgnoreCase("GET")) {
            request = new HttpGet(_network.requestUrl);
        }
        if (_network.method.equalsIgnoreCase("POST")) {
            HttpPost postRequest = new HttpPost(_network.requestUrl);
            StringEntity entity = new StringEntity(_network.data);
            postRequest.setEntity(entity);
            request = postRequest;

        }
        return request;
    }

    /**
     * Helper method to get HttpRequest from initialized Network Data Object
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    private HttpRequest getHttpRequest() throws UnsupportedEncodingException {
        HttpRequest request = getHttpUriRequest();
        return request;
    }

    private int firstDigit(int num) {
        if (num / 10 == 0)
            return num;
        return firstDigit(num / 10);

    }


    private String getSuccessResponseHtml(int statusCode) {
        return "Status Code : <font color=green><b>" + statusCode + "</b></font>";
    }

    private String getSuccessResponseHtml(String resp) {
        return resp != null ? resp.substring(0, 150) : "";
    }

    private String getFailedStatusHtml(String resp, int statusCode) {
        if (firstDigit(statusCode) != 2)
            resp = "Failure : with status code <font color=red><b>" + statusCode + "</b></font> , response " + resp;
        return resp;
    }

    private boolean isFailed(int status) {
        return firstDigit(status) != 2;
    }




}
