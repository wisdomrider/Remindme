/* Created By
  Wisdomrider
  On
  8/3/2018
  */
package com.wisdomrider.remindme;
/* Created By
  Wisdomrider
  On
  8/3/2018
  RemindMe
  */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reminders {
    String time, content,title,from;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Reminders(String time, String title, String content, String from) {
        this.time = time;
        this.from=from;
        this.title=title;
        this.content = content;
    }
    public String getTime1(){
        try {
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh : mm a");
            return  dateFormat.format(dateFormat1.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
