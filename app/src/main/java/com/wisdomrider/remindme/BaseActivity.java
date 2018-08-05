package com.wisdomrider.remindme;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.wisdomrider.sqliteclosedhelper.SqliteClosedHelper;

import java.util.ArrayList;
import java.util.Random;

/* Created By
  Wisdomrider
  On
  8/3/2018
  */


public class BaseActivity extends AppCompatActivity {
    WisdomRider wisdom;
   RandomClass randomClass;
    SqliteClosedHelper sqliteClosedHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        randomClass = new RandomClass();
        sqliteClosedHelper = new SqliteClosedHelper(this, "REMIND_ME");
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        wisdom = new WisdomRider(this);
    }

}
