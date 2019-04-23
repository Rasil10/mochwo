package com.jnanatech.mochwo.contactUs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.main.view.MainActivity;
import com.jnanatech.mochwo.utils.Constants;

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout emailRL;
    private RelativeLayout phoneRL;

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
                        "mailto",getResources().getString(R.string.kiasEmail), null));
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
                }
                else{
                    startActivity(callIntent);
                }
                break;
        }

    }
}
