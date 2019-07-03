package com.jnanatech.mochwo.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.abstracts.view.AbstractActivity;

public class AbstractSubmissionDialog {


    public void showDialog(final Context context, String msg) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.dialog_abstract);
        dialog.setTitle(msg);


        Button abstractSubmissionButton = (Button) dialog.findViewById(R.id.abstractSubmission);
        Button presentationGuidelinesButton = (Button) dialog.findViewById(R.id.presentationGuidelines);
        Button conferenceThemeButton = (Button) dialog.findViewById(R.id.conferenceTheme);


        abstractSubmissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abstractIntent = new Intent(context, AbstractActivity.class);
                abstractIntent.putExtra(Constants.webUrlConstant, "https://conference2019.kias.org.np/abstract-submission/");
                abstractIntent.putExtra(Constants.toolbarConstant, "Abstract Submission");
                context.startActivity(abstractIntent);
                dialog.dismiss();
            }
        });
        presentationGuidelinesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent presentationIntent = new Intent(context, AbstractActivity.class);
                presentationIntent.putExtra(Constants.webUrlConstant,"https://conference2019.kias.org.np/presentation-guidelines/");
                presentationIntent.putExtra(Constants.toolbarConstant,"Presentation Guidelines");
                context.startActivity(presentationIntent);
                dialog.dismiss();
            }
        });

        conferenceThemeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent conferenceIntent = new Intent(context, AbstractActivity.class);
                conferenceIntent.putExtra(Constants.webUrlConstant,"https://conference2019.kias.org.np/conference-themes/");
                conferenceIntent.putExtra(Constants.toolbarConstant,"Conference Themes");
                context.startActivity(conferenceIntent);
                dialog.dismiss();
            }
        });

        dialog.show();

    }

}
