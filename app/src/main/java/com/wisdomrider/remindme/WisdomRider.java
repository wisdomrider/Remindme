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
  */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class WisdomRider  {
    Context context;
    public WisdomRider(Context c){
        context=c;

    }
    Activity activity;
    public WisdomRider(Activity c){
        context=c;
        activity=c;

    }
    public AlertDialog showDialog(String text, final Pass positive, final Pass negative, boolean cancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(Html.fromHtml(text));
        if (positive != null) {
            String positiveText = positive.value();
            builder.setPositiveButton(positiveText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            positive.function(dialog);
                        }
                    });
        }
        if (negative != null) {
            String negativeText = negative.value();
            builder.setNegativeButton(negativeText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            negative.function(dialog);
                        }
                    });
        }
        AlertDialog dialog = builder.create();
        dialog.setCancelable(cancelable);
        dialog.show();
        return dialog;
    }


    public ImageView imageView(int id){
        return activity.findViewById(id);
    }
    public TextView textView(int id){
        return activity.findViewById(id);
    }
    public EditText editText(int id){
        return activity.findViewById(id);
    }

    public void toast(String s) {
        Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
    }

    public ProgressBar progressBar(int progress) {
    return activity.findViewById(progress);
    }

    public LinearLayout lay1(int no) {
        return activity.findViewById(no);
    }
}
