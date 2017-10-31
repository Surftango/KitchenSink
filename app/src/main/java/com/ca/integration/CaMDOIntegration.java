/**
 * Copyright (C) 2015, CA.  All rights reserved.
 */
package com.ca.integration;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.location.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Integrate this class in your project to send custom events and transactions.  When the app is wrapped
 * in MAA container, these methods will re-directed to corresponding methods in MAA Wrapper
 */

public class CaMDOIntegration {
    public static final String CAMAA_HEADER_ZIPCODE = "zp";
    public static final String CAMAA_HEADER_COUNTRY = "ct";
    public static final String CAMAA_CUSTOMER_ID = "customerId";
    public static final String CAMAA_CRASH_OCCURRED = "crashOccured";
    public static final String CAMAA_DOUBLE = "double";
    public static final String CAMAA_STRING = "string";
    public static final String CAMAA_CUSTOM = "custom";
    public static final int CAMAA_SCREENSHOT_QUALITY_HIGH = 30;
    public static final int CAMAA_SCREENSHOT_QUALITY_MEDIUM = 20;
    public static final int CAMAA_SCREENSHOT_QUALITY_LOW = 15;
    public static final int CAMAA_SCREENSHOT_QUALITY_DEFAULT = -1;

    public static final String CAMAA_SSL_PINNINGMODE_NONE = "none";
    public static final String CAMAA_SSL_PINNINGMODE_PUBLICKEY = "pk";
    public static final String CAMAA_SSL_PINNINGMODE_CERTIFICATE = "certificate";
    public static final String CAMAA_SSL_PINNINGMODE_FINGERPRINT_SHA1SIGNATURE = "sha1signature";
    public static final String CAMAA_SSL_PINNINGMODE_PUBLICKEYHASH = "hash";


    /***
     * Set the location of device
     * @param zipCode
     * @param countryCode
     */
    public static void setCustomerLocation(String zipCode, String countryCode) {

    }


    /***
     * Set the location of device
     * @param location
     */
    public static void setCustomerLocation(Location location) {

    }


    /**
     * Set session attribute with the value.
     * by the 'value' provided
     *
     * @param type  As of now, the only type that is supported is CAMAA_STRING.
     * @param key   event key
     * @param value value to use for the type
     * @deprecated use {link {@link #setSessionAttribute(String, String)}}
     */
    public static void setSessionAttribute(String type, String key, String value) {
    }

    /**
     * Set session attribute with the value.
     *
     * @param key   event key
     * @param value value to use for the attribute
     */
    public static void setSessionAttribute(String key, String value) {
    }

    /**
     * Starts a new application transaction that bounds all the subsequent events.  Application name
     * is used as the service name
     *
     * @param transactionName name of the transaction
     * @param callback        The callback to the application, in case of an error/success. if null
     *                        is passed in, the app receives no callbacks.
     * @return returns <code>boolean</code>.  <code>true</code> for success and <code>false</code>
     * for failure
     */
    public static void startApplicationTransaction(String transactionName, CaMDOCallback callback) {
    }

    /**
     * Starts a new application transaction that bounds all the subsequent events
     *
     * @param transactionName name of the transaction
     * @param serviceName     name of the service
     * @param callback        The callback to the application, in case of an error/success. if null
     *                        is passed in, the app receives no callbacks.
     */
    public static void startApplicationTransaction(String transactionName, String serviceName, CaMDOCallback callback) {
    }

    /**
     * Stops the application transaction.  Subsequent events will be part of the previous transaction
     * if there is one
     *
     * @param transactionName name of the transaction
     * @param callback        The callback to the application, in case of an error/success. if null is passed
     *                        in, the app receives no callbacks.
     */
    public static void stopApplicationTransaction(String transactionName, CaMDOCallback callback) {
    }

    /**
     * Stops the application transaction.  Subsequent events will be part of the previous transaction
     * if there is one
     *
     * @param transactionName name of the transaction
     * @param failure         pass <code>null</code> for a successful transaction.  If it is a failed transaction
     *                        pass a brief description about the failure
     * @param callback        The callback to the application, in case of an error/success. if null is passed
     *                        in, the app receives no callbacks.
     */
    public static void stopApplicationTransaction(String transactionName, String failure, CaMDOCallback callback) {
    }

    /**
     * Receive crash notifications through the receiver
     *
     * @param messageReceiver <code>android.content.BroadcastReceiver</code> to receive the callbacks
     */
    public static void registerAppFeedBack(BroadcastReceiver messageReceiver) {
    }

    /**
     * Sets the feedback from the user about a crash
     * @param feedback
     *
     * @deprecated use {@link #setCrashFeedback(String)}
     */
    public static void setCustomerFeedback(String feedback) {
    }

    /**
     * Sets the feedback from the user about a crash
     * @param feedback
     *
     */
    public static void setCrashFeedback(String feedback) {
    }

    /**
     * Sets general feedback from the user. The feedback is tagged to the
     * current session
     *
     * @param feedback
     *
     */
    public static void setUserFeedback(String feedback) {
    }


    /***
     * Enable SDK if its not enabled.
     *
     * When SDK is enabled, sdk will collect data for analytics.
     */
    public static void enableSDK() {
    }

    /**
     * Disables SDK if its enabled.
     * When SDK is disabled, SDK will not intercept any calls and wont collect any data from App.
     */
    public static void disableSDK() {
    }

    /**
     * Checks if SDK is enabled or not
     */
    public static boolean isSDKEnabled() {
        return true;
    }

    /***
     * In Private Zone screenshots and other sensitive information will not be recorded
     */
    public static void enterPrivateZone() {

    }

    /***
     * Exiting private zone
     */
    public static void exitPrivateZone() {

    }

    /***
     * Checks if app is in private zone state.
     *
     * @return
     */
    public static boolean isInPrivateZone() {
        return false;
    }


    /***
     * Takes screenshot of current screen and adds an event to analytics.
     *
     * @param screenName name of screenshot
     * @param quality quality CaMDOIntegration.CAMAA_SCREENSHOT_QUALITY_HIGH,CaMDOIntegration.CAMAA_SCREENSHOT_QUALITY_MEDIUM , CaMDOIntegration.CAMAA_SCREENSHOT_QUALITY_LOW
     * @param callback The callback to the application, in case of an error/success. if null is passed
     *                      in, the app receives no callbacks.
     */
    public static void sendScreenShot(String screenName, int quality, CaMDOCallback callback) {
    }

    /**
     * Checks whether screenshot is enabled in Policy
     *
     * @return true or false.
     */
    public static boolean isScreenshotPolicyEnabled() {
        return false;
    }


    /**
     * Use this method to stop the current session.  No data will be logged until startSession
     * API is called again
     */
    public static void stopCurrentSession() {
    }

    /**
     * Use this method to start a new session.  If a session is already in progress, it will
     * be ended and new session is started
     */
    public static void startNewSession() {
    }

    /**
     * Stop and Start a new session.
     *
     * @deprecated Use {@link #stopCurrentSession()} and {@link #startNewSession()}
     */
    public static void stopCurrentAndStartNewSession() {
    }

    /**
     * When a page or view is fully loaded take screen shot.
     *
     * @param screenName
     * @param screenLoadTime
     * @param callback       The callback to the application, in case of an error/success. if null is passed
     *                       in, the app receives no callbacks.
     */
    public static void viewLoaded(String screenName, int screenLoadTime, CaMDOCallback callback) {

    }

    /**
     * API to log a network event to AXA SDK.
     *
     * @param url          URL request
     * @param statusCode   status code of the request  ex: 200,401 etc
     * @param responseTime time taken to execute the request
     * @param inBytes      bytes received as part of request.
     * @param outBytes     bytes sent as part of request.
     * @param callback     The callback to the application, in case of an error/success. if null is passed
     *                     in, the app receives no callbacks.
     */
    public static void logNetworkEvent(String url, int statusCode, int responseTime, int inBytes, int outBytes, CaMDOCallback callback) {

    }


    /**
     * API to log a network event to MAA SDK.
     *
     * @param url          URL request
     * @param statusCode   status code of the request  ex: 200,401 etc
     * @param responseTime time taken to execute the request
     * @param inBytes      bytes received as part of request.
     * @param outBytes     bytes sent as part of request.
     */
    public static void logNetworkEvent(String url, int statusCode, int responseTime, int inBytes, int outBytes) {

    }

    /**
     * Force an upload of the aggregated event(s).This is an expensive operation and should be
     * used with caution.
     * <p>
     * To get the number of events that were uploaded, use the key
     * {@link CaMDOCallback#UPLOAD_EVENT_COUNT} on the Bundle returned by
     * {@link CaMDOCallback#onSuccess(android.os.Bundle)}
     *
     * @param callback The callback to the application, in case of an error/success. if null is passed
     *                 in, the app receives no callbacks.
     */
    public static void uploadEvents(CaMDOCallback callback) {
    }

    /**
     * Logs Numeric Metric.
     *
     * @param name
     * @param value
     * @param attributes (optional)
     * @param callback   The callback to the application, in case of an error/success. if null is passed
     *                   in, the app receives no callbacks.
     */
    public static void logNumericMetric(String name, Double value, Map<String, String> attributes, CaMDOCallback callback) {

    }

    /**
     * Logs Text Metric.
     *
     * @param name
     * @param value
     * @param attributes (optional)
     * @param callback   The callback to the application, in case of an error/success. if null is passed
     *                   in, the app receives no callbacks.
     */
    public static void logTextMetric(String name, String value, Map<String, String> attributes, CaMDOCallback callback) {

    }

    /**
     * Returns Headers for tracking Network calls in APM.
     *
     * @return Map containing keys (header name) values ( value of header )
     */
    public static Map<String, String> getAPMHeaders() {
        return new HashMap<String, String>();
    }

    /**
     * Add values to APM header
     *
     * @param headerString
     */
    public static void addToApmHeader(String headerString) {

    }

    public static String getCustomerId() {
        return "";
    }

    /**
     * Set the CustomerID
     */
    public static void setCustomerId(String customerId) {
    }

    public static String getDeviceId() {
        return "";
    }

    public static void ignoreView(String viewName) {

    }

    public static void ignoreViews(HashSet<String> viewNames) {

    }

    /**
     * Sets the pinning mode for TLS/SSL connection.
     *
     * @deprecated use {@link #setSSLPinningMode(Application, String, ArrayList)}
     */
    public static void setSSLPinningMode(Application app, String sslPinningMode, byte[] value) {

    }

    /**
     * Sets the pinning mode for TLS/SSL connection.
     *
     * @param app            Application object
     * @param sslPinningMode can be any of {@link #CAMAA_SSL_PINNINGMODE_NONE}, {@link #CAMAA_SSL_PINNINGMODE_CERTIFICATE}
     *                       {@link #CAMAA_SSL_PINNINGMODE_FINGERPRINT_SHA1SIGNATURE}, {@link #CAMAA_SSL_PINNINGMODE_PUBLICKEY}
     *                       {@link #CAMAA_SSL_PINNINGMODE_PUBLICKEYHASH}
     * @param values         Depending on the sslPinningMode, this could be a collection of certificate bytes,
     *                       public-key/ public-key hashes, SHA1 fingerprints etc
     */
    public static void setSSLPinningMode(Application app, String sslPinningMode, ArrayList<byte[]> values) {

    }


}
