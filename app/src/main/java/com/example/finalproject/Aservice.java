package com.example.finalproject;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Timer;
import java.util.TimerTask;

public class Aservice extends Service {


    private Timer timer = new Timer();
    // to check the app contiuosly

    SharedPreferences sharedPreferences;
    long newdata = 0;
    RequestQueue queue;
    String url;

    @Override
    public void onCreate() {

        queue = Volley.newRequestQueue(this);
        url = "https://corona.lmao.ninja/v2/countries/Nepal";


        super.onCreate();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // when the data changes the notification is send to user

                if (getdata() > 0) {
                    notifyuser(getdata());
                }
            }
        }, 0, 1 * 10 * 1000);   // checks every 10 seconds
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        newdata = getdata();
        notifyuser(newdata);
        // calling the method to notify user

        return START_STICKY;
    }


    private void notifyuser(long data) {

        // Notification builder
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(Aservice.this, "a");
        // channel id is required as passing only the service is deprecated

        mBuilder.setContentTitle("Corona nepal update");
        newdata = getdata();

        // if the data is changed then show above if case statement else else condition to be shown

        if (newdata != 0) {
            mBuilder.setContentText("the new data is" + newdata);
        } else {
            mBuilder.setContentText("No new case added");
        }
        mBuilder.setSmallIcon(R.drawable.ic_notifications_active_black_24dp);


        // onclick notification the homepage activity is started
        Intent resultIntent = new Intent(this, homePage.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(homePage.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, mBuilder.build());

    }

    long nu = 0;

    // method to return the difference between saved data and new updated cases

    private long getdata() {
        // using the combination of volley and gson to retrieve the data

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                ApiModel apiModel = gson.fromJson(response, ApiModel.class);
                String cases = apiModel.getCases().toString();

                String Death = apiModel.getDeaths().toString();


                sharedPreferences = getSharedPreferences("NepPrefs", Context.MODE_PRIVATE);

                // retrieving the data from cache to notify user if data is added

                long difference = Long.parseLong(sharedPreferences.getString("Confirmed", "")) - apiModel.getCases();

                if (difference > 0) {
                    nu = difference;
                    newdata = apiModel.getCases();
                    // Saving the data in cache
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("Confirmed", cases);
                    editor.commit();     //commit saves the data in the cache
                }


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                nu = 0;
            }
        });
        queue.add(stringRequest);

        return nu;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
