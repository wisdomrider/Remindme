package com.wisdomrider.remindme;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.wisdomrider.sqliteclosedhelper.SqliteClosedHelper;
import com.wisdomrider.sqliteclosedhelper.Wisdom;

import java.util.ArrayList;

public class home extends BaseActivity {
    ArrayList<Reminders> reminders = new ArrayList<>();
    RecyclerView recyclerView;
    RemindersAdapter remindersAdapter;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sqliteClosedHelper.clearAll()
                .setTable("reminder")
                .setTableFields("id", Wisdom.INTEGER(), Wisdom.PRIMARY_AUTOINCREMENT())
                .setTableFields("title", Wisdom.STRING())
                .setTableFields("desc", Wisdom.STRING())
                .setTableFields("time", Wisdom.DATETIME())
                .setTableFields("addedtime", Wisdom.DATETIME())
                .create();
        wisdom.textView(R.id.home_title).setBackgroundResource(randomClass.getColor());
        recyclerView = findViewById(R.id.recycle);
        remindersAdapter = new RemindersAdapter(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(remindersAdapter);

    }

    public void fetch() {
        reminders.clear();
        Cursor a = sqliteClosedHelper.setTable("reminder")
                .get("order by addedtime desc");
        if (a.getCount() != 0) {
            while (a.moveToNext()) {
                String from;
                title =
                        a.getString(1);
                if (title.toLowerCase().contains("youtube"))
                    from = "Youtube";
                else from = "Browser";
                reminders.add(new Reminders(a.getString(3), a.getString(1), a.getString(2), from));
            }
        } else {
            wisdom.lay1(R.id.no).setVisibility(View.VISIBLE);
            Glide.with(this)
                    .asGif()
                    .load(R.drawable.oops)
                    .into(wisdom.imageView(R.id.oops1));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetch();
    }

    public void add(View v) {
        Intent r = new Intent(this, Add.class);
        r.putExtra("from", 1);
        startActivity(r);
    }
}
