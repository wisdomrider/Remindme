package com.wisdomrider.remindme;/* Created By
  Wisdomrider
  On
  8/4/2018
  RemindMe
  */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.widget.Toast;

import com.wisdomrider.sqliteclosedhelper.SqliteClosedHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SqliteClosedHelper sqliteClosedHelper = new SqliteClosedHelper(context, "REMIND_ME");
        Cursor cursor = sqliteClosedHelper.setTable("reminder")
                .getAll();
        while (cursor.moveToNext()) {
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = cursor.getString(3);
            Date date = null;
            try {
                date = dateFormat1.parse(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (date.getTime() - 50000 < System.currentTimeMillis()) {
                addALarm(context,cursor.getString(1),cursor.getString(2));
                sqliteClosedHelper.delete("desc", cursor.getString(2));
            }
        }
    }


    public void addALarm(Context c,String title, String content) {
        NotificationManager notificationManager;
        Intent notificationIntent = new Intent(Intent.ACTION_VIEW);
        notificationIntent.setData(Uri.parse(content));
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(c, 1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationManager = (android.app.NotificationManager) c.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(c);
        Uri uri = Uri.parse("android.resource://" + c.getPackageName() + "/" + R.raw.alarm);
        mBuilder.setContentIntent(contentIntent)
                .setContentTitle("Reminder")
                .setSmallIcon(R.drawable.logo)
                .setContentText(Html.fromHtml(title))
                .setAutoCancel(true)
                .setOngoing(false)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE);
        mBuilder.setSound(uri);
        mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        Notification notification = mBuilder.build();
        notificationManager.notify(new Random().nextInt(999), notification);

    }
}
