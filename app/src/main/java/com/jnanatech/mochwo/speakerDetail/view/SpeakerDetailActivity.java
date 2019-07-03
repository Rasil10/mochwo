package com.jnanatech.mochwo.speakerDetail.view;

import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.speakerDetail.model.ChipTag;
import com.jnanatech.mochwo.speakers.model.SpeakerModel;
import com.jnanatech.mochwo.utils.Constants;
import com.jnanatech.mochwo.utils.database.RealmController;
import com.plumillonforge.android.chipview.ChipView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class SpeakerDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private RequestQueue requestQueue;

    SpeakerModel speakerModel;

    CircleImageView speakerImageView;
    TextView speakerName;
    TextView speakerCurrentPosition;
    TextView abstractTextView;
    TextView aboutSpeakerTextView;
    TextView aboutTextView;
    TextView speakerTopic;
    TextView speakerEmailAddress;

    RelativeLayout emailRL;
    ChipView keywordChipGroup;
    RealmController realmController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_speaker_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        realmController = RealmController.with(this);

        requestQueue = Volley.newRequestQueue(SpeakerDetailActivity.this);
        try {
            speakerModel = realmController.getSpeakerByName(getIntent().getStringExtra(Constants.speakerConstant));
        } catch (Exception e) {

        } finally {
            speakerModel = realmController.getSpeakerByName(getIntent().getStringExtra(Constants.speakerConstant));
        }


        bindActivity();
        updateSpeaker();


    }

    private void updateSpeaker() {
        getSupportActionBar().setTitle("About " + speakerModel.getSpeakerName());

        speakerName.setText(speakerModel.getSpeakerName());
        speakerCurrentPosition.setText(speakerModel.getCurrentPosition());

        Picasso.get().load(speakerModel.getImageUrl())
                .placeholder(R.drawable.ic_terrain_black_24dp)
                .error(R.drawable.ic_terrain_black_24dp)
                .into(speakerImageView);

        aboutSpeakerTextView.setText(speakerModel.getShortInfo());
        aboutTextView.setText("About " + speakerModel.getSpeakerName());
        speakerTopic.setText(speakerModel.getTopic());
        abstractTextView.setText(speakerModel.getAbstractt());
        speakerEmailAddress.setText(speakerModel.getEmail());




        String[] keyWordsArray = speakerModel.getKeywords().split(",");
        List<com.plumillonforge.android.chipview.Chip> chipList = new ArrayList<>();
        for (int i = 0; i < keyWordsArray.length; i++) {
            chipList.add(new ChipTag(keyWordsArray[i]));
        }
        keywordChipGroup.setChipList(chipList);

    }



    private void bindActivity() {

        speakerImageView = (CircleImageView) findViewById(R.id.speakerImageView);
        speakerName = (TextView) findViewById(R.id.speakerName);
        speakerCurrentPosition = (TextView) findViewById(R.id.speakerCurrentPosition);
        abstractTextView = (TextView) findViewById(R.id.abstractTextView);
        speakerTopic = (TextView) findViewById(R.id.speakerTopicTextView);
        aboutTextView = (TextView) findViewById(R.id.aboutTextView);
        aboutSpeakerTextView = (TextView) findViewById(R.id.aboutSpeakerTextView);
        speakerEmailAddress = (TextView) findViewById(R.id.speakerEmailAddress);

        keywordChipGroup = (ChipView) findViewById(R.id.keywordChipGroup);

        emailRL = (RelativeLayout) findViewById(R.id.emailRL);
        emailRL.setOnClickListener(this);


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
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + speakerModel.getEmail()));
                startActivity(Intent.createChooser(emailIntent, "Contact " + speakerModel.getSpeakerName()));
                break;
        }

    }
}