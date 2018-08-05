/* Created By
  Wisdomrider
  On
  8/3/2018
  */
package com.wisdomrider.remindme;/* Created By
  Wisdomrider
  On
  8/3/2018
  RemindMe
  */

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class RemindersAdapter extends RecyclerView.Adapter<RemindersAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView time, title, desc, from;
        RelativeLayout lay;

        public ViewHolder(View v) {
            super(v);
            lay = v.findViewById(R.id.lay);
            time = v.findViewById(R.id.time);
            title = v.findViewById(R.id.title);
            desc = v.findViewById(R.id.desc);
            from = v.findViewById(R.id.from);
        }
    }

    home activity;

    public RemindersAdapter(home activity) {
        this.activity = activity;
    }

    @Override
    public RemindersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reminder, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RemindersAdapter.ViewHolder holder, final int position) {
        Reminders reminder = activity.reminders.get(position);
        holder.title.setText(Html.fromHtml(reminder.getTitle()));
        holder.from.setText(Html.fromHtml("<font color='red'>From : " + reminder.getFrom()));
        holder.desc.setText(Html.fromHtml(reminder.getContent()));
        holder.time.setText(Html.fromHtml(reminder.getTime1()));
        holder.lay.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                options(position);
                return false;
            }
        });
        holder.lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                options(position);
            }
        });
    }

    private void options(final int position) {
        final Dialog dialogg = new Dialog(activity);
        dialogg.setContentView(R.layout.post_menu);
        TextView edit = dialogg.findViewById(R.id.edit);
        TextView delete = dialogg.findViewById(R.id.delete);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent r = new Intent(activity, Edit.class);
                r.putExtra("title", activity.reminders.get(position).getTitle());
                r.putExtra("content", activity.reminders.get(position).getContent());
                r.putExtra("from", 1);
                activity.startActivity(r);


            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogg.dismiss();
                activity.wisdom.showDialog("<b>Are you sure? </b><br><br> It cannot be reverted.", new Pass() {
                    @Override
                    public String value() {
                        return "Yes";
                    }

                    @Override
                    public void function(DialogInterface dialog) {
                        activity.sqliteClosedHelper.setTable("reminder")
                                .delete("title", activity.reminders.get(position).getTitle());
                        activity.reminders.remove(position);
                        notifyDataSetChanged();
                        if (activity.reminders.isEmpty()) {
                            activity.wisdom.lay1(R.id.no).setVisibility(View.VISIBLE);
                            Glide.with(activity)
                                    .asGif()
                                    .load(R.drawable.oops)
                                    .into(activity.wisdom.imageView(R.id.oops1));
                        }
                        dialog.dismiss();
                    }
                }, new Pass() {
                    @Override
                    public String value() {
                        return "No";
                    }

                    @Override
                    public void function(DialogInterface dialog) {

                    }
                }, true);

            }
        });
        dialogg.show();

    }


    @Override
    public int getItemCount() {
        return activity.reminders.size();
    }
}
