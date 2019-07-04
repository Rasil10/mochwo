package com.jnanatech.mochwo.notification.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.speakers.model.SpeakerModel;
import com.jnanatech.mochwo.utils.Constants;
import com.jnanatech.mochwo.utils.database.RealmController;
import com.jnanatech.mochwo.utils.sharedPreference.NotificationSizeSharedPrefHelper;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {

    RealmController realmController;
    NotificationSizeSharedPrefHelper notificationSizeSharedPrefHelper;

    ArrayList<SpeakerModel> speakerList = new ArrayList<>();
    RecyclerView notificationRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        realmController = RealmController.with(this);

        notificationSizeSharedPrefHelper = new NotificationSizeSharedPrefHelper(this);
        notificationSizeSharedPrefHelper.saveNotificationNumberNumber(realmController.getAllNotification().size());

        notificationRecyclerView = (RecyclerView) findViewById(R.id.notificationRecyclerView);
        notificationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        NotificationRecyclerViewAdapter adapter = new NotificationRecyclerViewAdapter(this, realmController.getAllNotification());
        notificationRecyclerView.setAdapter(adapter);

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
    public void onBackPressed() {
        super.onBackPressed();
    }
}
