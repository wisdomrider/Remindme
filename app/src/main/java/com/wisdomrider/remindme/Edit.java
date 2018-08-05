package com.wisdomrider.remindme;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Edit extends BaseActivity {
    String title;
    String content;
    int from;
    String before;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        title = getIntent().getStringExtra("title");
        before = title;
        content = getIntent().getStringExtra("content");
        from = getIntent().getIntExtra("from", 0);
        wisdom.textView(R.id.title).setText(title);
        wisdom.textView(R.id.desc).setText(content);

    }

    public void time(View view) {
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


    public void save(View v) {
        String title = wisdom.textView(R.id.title).getText().toString().trim();
        String desc = wisdom.textView(R.id.desc).getText().toString().trim();
        if (date1 != null && date != null && !title.isEmpty() && !desc.isEmpty()) {
            v.setVisibility(View.GONE);
            wisdom.progressBar(R.id.progress).setVisibility(View.VISIBLE);
            sqliteClosedHelper.clearAll()
                    .setTable("reminder")
                    .updateFields("title", title)
                    .updateFields("desc", desc)
                    .updateFields("time", time(date1))
                    .updateFields("addedtime", time(date))
                    .update("title", before);
            Intent r = new Intent(this, Alarm.class);
            PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, r, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, date1.getTime(), pi);
            wisdom.toast(wisdom.textView(R.id.time).getText().toString());
            back();
        } else {
            wisdom.toast("Oops ! something important is missing !");
        }
    }

    public String time(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return (dateFormat.format(date));
    }

    public void back() {
        if (from == 1) {
            startActivity(new Intent(this, home.class));
        } else {
            finish();
        }
    }

    Date date, date1;

    private void saveTime(Integer currentHour, Integer currentMinute) {
        date = Calendar.getInstance().getTime();
        date1 = new Date();
        date1.setHours(currentHour);
        date1.setMinutes(currentMinute);
        date1.setDate(date.getDate());
        if(date1.getTime()<date.getTime()){
            date1.setDate(date1.getDate()+1);
        }
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
