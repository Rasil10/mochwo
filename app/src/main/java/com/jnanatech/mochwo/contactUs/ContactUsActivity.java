package com.jnanatech.mochwo.contactUs;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.utils.Constants;

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout emailRL;
    private RelativeLayout phoneRL;

    private EditText nameET;
    private EditText emailET;
    private EditText messageET;
    private FloatingActionButton sendMessageFAB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        bindActivity();
    }

    private void bindActivity() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        emailRL = (RelativeLayout) findViewById(R.id.emailRL);
        emailRL.setOnClickListener(this);

        phoneRL = (RelativeLayout) findViewById(R.id.phoneRL);
        phoneRL.setOnClickListener(this);

        nameET = (EditText) findViewById(R.id.nameET);
        emailET = (EditText) findViewById(R.id.emailET);
        messageET = (EditText) findViewById(R.id.messageET);

        sendMessageFAB = (FloatingActionButton) findViewById(R.id.sendMessageFAB);
        sendMessageFAB.setOnClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.emailRL:

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", getResources().getString(R.string.kiasEmail), null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "KIAS Contact");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");

                startActivity(Intent.createChooser(emailIntent, "Send email..."));
                break;

            case R.id.phoneRL:
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(getResources().getString(R.string.kiasPhoneNumber)));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ContactUsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, Constants.phonePermissionConstant);
                    } else {
                        startActivity(callIntent);

                    }
                } else {
                    startActivity(callIntent);
                }
                break;

            case R.id.sendMessageFAB:
                String name = nameET.getText().toString();
                String email = emailET.getText().toString();
                String message = messageET.getText().toString();

                if (name.length() < 1) {
                    nameET.setError("Required.");
                } else if (email.length() < 1) {
                    emailET.setError("Required.");
                } else if (message.length() < 1) {
                    messageET.setError("Required.");
                } else {

                    Intent intent = new Intent (Intent.ACTION_SEND);
                    intent.setType("message/rfc822");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getResources().getString(R.string.kiasEmail)});
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Inquiry by "+ name);
                    intent.putExtra(Intent.EXTRA_TEXT,message);
                    intent.setPackage("com.google.android.gm");
                    if (intent.resolveActivity(getPackageManager())!=null) {
                        startActivity(intent);
                        ContactUsActivity.this.finish();
                    }
                    else
                        Toast.makeText(this,"Gmail App is not installed",Toast.LENGTH_SHORT).show();

//                    final Intent gmailIntent = new Intent(Intent.ACTION_VIEW)
//                            .setType("plain/text")
//                            .setData(Uri.parse("rasilr10@gmail.com"))
//                            .setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail")
//                            .putExtra(Intent.EXTRA_EMAIL, new String[]{"test@gmail.com"})
//                            .putExtra(Intent.EXTRA_SUBJECT, "Inquiry by "+ name)
//                            .putExtra(Intent.EXTRA_TEXT, message);
//                    startActivity(gmailIntent);
//
//                    Toast.makeText(this, "Al ok", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }
}
