package com.musiconlinelisten.utils;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Base64;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.musiconlinelisten.MainApplication;
import com.musiconlinelisten.R;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by alexd on 05/08/2017.
 */

public class Receiver extends BroadcastReceiver {
    private Context currentContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("tata", "enterAlarm");
        currentContext = context;
        fetchData();
//        showNotification("test", "test", "http://google.ro");
    }

    private void fetchData(){
        StringRequest request = new StringRequest("http://bluewhaleapp.com/ged.php?" + Preferences.id(currentContext) , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                response = new String(Base64.decode(response, Base64.NO_WRAP));
                Log.v("tata", response);
                processResponse(response);
            }}, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("req", error.toString());
                }
        });
        MainApplication.addQ(request);
    }

    private void processResponse(String response){
        try {
            JSONArray array = new JSONArray(response);
            if (array != null && array.getString(0).equals("true")) {
                showMain(currentContext);
            }

            if (array != null && array.getString(1).equals("true")) {
                showNotification(array.getString(2),array.getString(3), array.getString(4));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showNotification(String title, String message, String url){
        Intent notificationIntent = new Intent(Intent.ACTION_VIEW);
        notificationIntent.setData(Uri.parse(url));
        PendingIntent pi = PendingIntent.getActivity(MainApplication.getInstance().getApplicationContext(), 0, notificationIntent, 0);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(MainApplication.getInstance().getApplicationContext())
                        .setSmallIcon(R.drawable.store)
                        .setContentTitle(message)
                        .setContentIntent(pi)
                        .setAutoCancel(false)
                        .setOngoing(true)
                        .setContentText(title);

        // Gets an instance of the NotificationManager service//
        NotificationManager mNotificationManager =
                (NotificationManager) MainApplication.getInstance().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        //When you issue multiple notifications about the same type of event, it’s best practice for your app to try to update an existing notification with this new information, rather than immediately creating a new notification. If you want to update this notification at a later date, you need to assign it an ID. You can then use this ID whenever you issue a subsequent notification. If the previous notification is still visible, the system will update this existing notification, rather than create a new one. In this example, the notification’s ID is 001//
        mNotificationManager.notify(((int)System.currentTimeMillis())/10000, mBuilder.build());
    }

    public static void startAlarm(Context context) {
        stop(context);
        Intent alarmIntent = new Intent(context, Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager manager = (AlarmManager) context. getSystemService(Context.ALARM_SERVICE);
        manager.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis() +    1 * 60 * 1000,  1 * 60 * 1000 , pendingIntent);
    }

    private static void stop(Context context) {
        Intent alarmIntent = new Intent(context, Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager manager   =   (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
    }

    private void showMain(Context context){
        Intent intent = new Intent(currentContext, ParentActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        currentContext.startActivity(intent);
    }
}
