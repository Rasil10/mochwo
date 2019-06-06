package com.jnanatech.mochwo.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.jnanatech.mochwo.R;

public class PastEventDialog {


    public void showDialog(final Context context, String msg) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_past_events);
        dialog.setTitle(msg);


        Button mochwo16 = (Button) dialog.findViewById(R.id.mochwo16);
        Button mochwo17 = (Button) dialog.findViewById(R.id.mochwo17);


        mochwo16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW);
                webIntent.setData(Uri.parse("http://mochwo16.kias.org.np/"));
                context.startActivity(webIntent);

                dialog.dismiss();
            }
        });
        mochwo17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW);
                webIntent.setData(Uri.parse("http://mochwo17.kias.org.np/"));
                context.startActivity(webIntent);
                dialog.dismiss();
            }
        });

        dialog.show();

    }

}
