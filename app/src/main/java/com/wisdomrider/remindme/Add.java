package com.wisdomrider.remindme;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Add extends BaseActivity {
    int from = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        wisdom.textView(R.id.add_title).setBackgroundResource(randomClass.getColor());


        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent);
            }
        } else if (getIntent().getExtras() != null)
            from = getIntent().getIntExtra("from", 0);
      
    }


    void handleSendText(Intent intent) {
        final String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        final String subject = intent.getStringExtra(Intent.EXTRA_SUBJECT);
        if (sharedText != null) {
            wisdom.editText(R.id.title).setText(subject);
            wisdom.editText(R.id.desc).setText(sharedText);
            Cursor cursor = sqliteClosedHelper
                    .setTable("reminder")
                    .getWhere("title", subject);
            if (cursor.getCount() == 0)
                time1();
            else
                wisdom.showDialog("<b>Reminder is already there</b><br><br>Do you want to edit it?", new Pass() {
                    @Override
                    public String value() {
                        return "Yes";
                    }

                    @Override
                    public void function(DialogInterface dialog) {
                        Intent r = new Intent(getApplicationContext(), Edit.class);
                        r.putExtra("title", subject);
                        r.putExtra("from", 2);
                        r.putExtra("content", sharedText);
                        finish();
                        startActivity(r);

                    }
                }, new Pass() {
                    @Override
                    public String value() {
                        return "No";
                    }

                    @Override
                    public void function(DialogInterface dialog) {
                        finish();
                    }
                }, false);
        }

    }


    public void save(View v) {
        String title = wisdom.textView(R.id.title).getText().toString().trim();
        String desc = wisdom.textView(R.id.desc).getText().toString().trim();
        if (date1 != null && date != null && !title.isEmpty() && !desc.isEmpty()) {
            v.setVisibility(View.GONE);
            wisdom.progressBar(R.id.progress).setVisibility(View.VISIBLE);
            Cursor cursor = sqliteClosedHelper.setTable("reminder")
                    .getWhere("desc", desc);
            if (cursor.getCount() == 0) {
                sqliteClosedHelper.clearAll()
                        .setTable("reminder")
                        .insertFields("title", title)
                        .insertFields("desc", desc)
                        .insertFields("time", time(date1))
                        .insertFields("addedtime", time(date))
                        .insert();
                Intent r = new Intent(this, Alarm.class);
                PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, r, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, date1.getTime(), pi);
                wisdom.toast(wisdom.textView(R.id.time).getText().toString());
                back();
            } else {
                back();
                wisdom.toast("Reminder Already Added.");

            }

        } else {
            wisdom.toast("Oops ! something important is missing !");
        }
    }


    public void back() {
        if (from == 1) {
            startActivity(new Intent(this, home.class));
        } else {
            finish();
        }
    }

    public String time(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return (dateFormat.format(date));
    }

    public void time1() {
        Date date = Calendar.getInstance().getTime();
        final Dialog dialog = new Dialog(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final TimePicker picker = new TimePicker(this);
        picker.setCurrentHour(date.getHours() + 8);
        picker.setCurrentMinute(date.getMinutes());
        layout.addView(picker);
        Button button = new Button(new ContextThemeWrapper(getApplicationContext(), R.style.MyButton6));
        layout.addView(button);
        dialog.setContentView(layout);
        dialog.setCancelable(true);
        button.setText("Done");
        dialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTime(picker.getCurrentHour(), picker.getCurrentMinute());
                wisdom.textView(R.id.time).setText("You will be notified in " + showTime(picker.getCurrentHour(), picker.getCurrentMinute()));
                save(new View(getApplicationContext()));
                dialog.dismiss();

            }
        });

    }

    Date date;
    Date date1;

    private void saveTime(Integer currentHour, Integer currentMinute) {
        date = Calendar.getInstance().getTime();
        date1 = new Date();
        date1.setHours(currentHour);
        date1.setMinutes(currentMinute);
        date1.setDate(date.getDate());
        if (date1.getTime() < date.getTime()) {
            date1.setDate(date1.getDate() + 1);
        }
    }

    public void time(View v) {
        final Dialog dialog = new Dialog(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final TimePicker picker = new TimePicker(this);
        layout.addView(picker);
        Button button = new Button(new ContextThemeWrapper(getApplicationContext(), R.style.MyButton6));
        layout.addView(button);
        dialog.setContentView(layout);
        dialog.setCancelable(true);
        button.setText("Done");
        dialog.show();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTime(picker.getCurrentHour(), picker.getCurrentMinute());
                wisdom.textView(R.id.time).setText("You will be notified in " + showTime(picker.getCurrentHour(), picker.getCurrentMinute()));
                dialog.dismiss();
            }
        });
    }

    public String showTime(int hour, int min) {
        String format;
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        return hour + " : " + min + " " + format;
    }


}
