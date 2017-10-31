package com.maa.ca.kitchensink.utils;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.maa.ca.kitchensink.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.lang.ref.WeakReference;
import  android.system.Os;

/**
 * Created by sugsh04 on 10/1/17.
 */

public class StartTimeGrabber extends AsyncTask<Object, Object, String> {


    private static final String TAG = "StartTimeGrabber";
    //WeakReference<Activity> activity;
    Context mCtx;

    public StartTimeGrabber(Activity ctx){
        //activity = new WeakReference<Activity>(ctx);
        mCtx = ctx.getApplicationContext();
    }

    @Override
    protected String doInBackground(Object[] params) {
        //return  getStartTime();
        String returnValue = null;

        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            returnValue = ""+(SystemClock.uptimeMillis() - Process.getStartUptimeMillis());
        }else{
            returnValue = initializeStartupTime();
        }
        */
        String ram = " RAM: "+getTotalRAM();
        String cpuUsage = " cpuUsage: "+getCPUUsingDeviceStats();
        String startUpTime = " startUpTime: "+getStartupTime();
        String startUpTime2 = " startUpTime(Self): "+getStartupTimeSelf();

        return ram+cpuUsage+startUpTime+startUpTime2 ;
    }


    /**
     * Get Total Memory
     *
     * @return
     */
    protected static Long getTotalRAM() {

        BufferedReader buffreader = null;
        String line = null;
        String totalMem = "0";

        try {
            buffreader = new BufferedReader(new FileReader("/proc/meminfo"));
            line = buffreader.readLine();
            // first line looks like this ... MemTotal:         516144 kB
            // trimming out 'MemTotal:', empty spaces & kB
            totalMem = line.substring(line.indexOf(':') + 1).trim();
            totalMem = (totalMem.substring(0, totalMem.indexOf("kB") - 1)).trim();
            Log.d(TAG, "Memory in kB= " + totalMem);

        } catch (Exception ex) {
            Log.e(TAG,ex.getMessage(), ex);
        } finally {
            try {
                if (buffreader != null)
                    buffreader.close();
            } catch (IOException e) {
                Log.e(TAG,e.getMessage(), e);
            }
        }
        return Long.parseLong(totalMem);
    }

    protected static float getCPUUsingDeviceStats() {
        float cpu = 0f;
        RandomAccessFile reader=null;
        try {
            reader = new RandomAccessFile("/proc/stat", "r");
            String load = reader.readLine();

            String[] toks = load.split(" ");

            long idle1 = Long.parseLong(toks[5]);
            long cpu1 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4])
                    + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

            try {
                Thread.sleep(360);
            } catch (Exception e) {
            }

            reader.seek(0);
            load = reader.readLine();
            reader.close();

            toks = load.split(" ");

            long idle2 = Long.parseLong(toks[5]);
            long cpu2 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4])
                    + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

            cpu = (float) (cpu2 - cpu1) / ((cpu2 + idle2) - (cpu1 + idle1));

        } catch (IOException ex) {
            Log.e(TAG, ex.getMessage(), ex);
            cpu = 0f;
        }finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.d(TAG,"Exception while closing /proc/stat file:"+e.getLocalizedMessage());
                }
            }
        }

        if (cpu < 0) {
            return 0f;
        }
        cpu = (float) Math.round(cpu * 100) / 100;
        if (cpu > 100f) {
            return 100f;
        }
        return cpu * 100f;
    }

    public long getStartupTime(){
        try {
            final int mPid = Process.myPid();
            File in = new File("/proc/" + mPid);
            return System.currentTimeMillis()- in.lastModified();
        } catch (Exception e) {
            return -1;
        }
    }

    public long getStartupTimeSelf(){
        try {
            File in = new File("/proc/self" );
            return System.currentTimeMillis()- in.lastModified();
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    protected void onPostExecute(String o) {
        super.onPostExecute(o);
        //activity.get().findViewById(R.id.)
        Toast.makeText(mCtx,o,Toast.LENGTH_LONG).show();
    }

    /**
     * A way to find out how much time it was taken for process to start.
     */
    private static String initializeStartupTime() {

        try {
            final int mPid = Process.myPid();
            File in = new File("/proc/" + mPid);
            long lastMod = in.lastModified();
            return ""+(System.currentTimeMillis() - lastMod);
        } catch (Throwable th) {
            return null;
        }
    }

    private String getStartTime() {

        try {
            java.lang.Process process = Runtime.getRuntime().exec("logcat -d -b all ActivityManager:I *:S");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            StringBuilder log=new StringBuilder();
            String line = "";
            int count =0;
            while ((line = bufferedReader.readLine()) != null) {
                log.append(line+"\n");
                /*if(line.contains(": Displayed")){
                    log.append(line+"\n");
                }*/
            }
                Log.e("Asdasd",log.toString());
            return log.toString();

        }
        catch (IOException e) {}
        return null;
    }
}
