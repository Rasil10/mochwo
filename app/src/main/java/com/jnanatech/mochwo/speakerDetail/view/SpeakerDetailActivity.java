package com.jnanatech.mochwo.speakerDetail.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.speakers.model.SpeakerModel;
import com.jnanatech.mochwo.utils.Constants;
import com.jnanatech.mochwo.utils.NoInternetDialog;
import com.jnanatech.mochwo.utils.database.RealmController;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;


public class SpeakerDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private RequestQueue requestQueue;

    SpeakerModel speakerModel;

    CircleImageView speakerImageView;
    TextView speakerName;
    TextView speakerCurrentPosition;
    TextView speakerShortDescription;
    TextView speakerDescription;
    TextView aboutTextView;
    TextView speakerQuoteTextView;

    TextView education1;
    TextView education2;
    TextView education3;
    TextView education4;

    TextView experience1;
    TextView experience2;
    TextView experience3;
    TextView experience4;

    TextView speakerPhoneNumber;
    TextView speakerEmailAddress;
    TextView speakerAddress;
    TextView followSpeakerTV;

    ImageView facebook;
    ImageView twitter;
    ImageView linkedin;
    ImageView instagram;
    ImageView youtube;

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
        }
        catch (Exception e){

        }
        finally {
            speakerModel = realmController.getSpeakerByName(getIntent().getStringExtra(Constants.speakerConstant));
        }


        bindActivity();
        updateSpeaker();


    }

    private void updateSpeaker() {
        getSupportActionBar().setTitle("About " + speakerModel.getSpeakerName());

        speakerName.setText(speakerModel.getSpeakerName());
        speakerCurrentPosition.setText(speakerModel.getCurrentPosition());
        speakerShortDescription.setText(speakerModel.getShortInfo());
        setImage(speakerModel.getFeatureMediaLink(), speakerImageView);
//        aboutTextView.setText("About " + speakerModel.getSpeakerName());
        speakerQuoteTextView.setText(speakerModel.getQuote());
//        speakerDescription.setText(speakerModel.getDescription());


        if (speakerModel.getEducation1() == null || speakerModel.getEducation1().isEmpty()) {
            education1.setVisibility(View.GONE);
        } else {
            education1.setText("•  " + speakerModel.getEducation1());
        }
        if (speakerModel.getEducation2() == null || speakerModel.getEducation2().isEmpty()) {
            education2.setVisibility(View.GONE);
        } else {
            education2.setText("•  " + speakerModel.getEducation2());
        }
        if (speakerModel.getEducation3() == null || speakerModel.getEducation3().isEmpty()) {
            education3.setVisibility(View.GONE);
        } else {
            education3.setText("•  " + speakerModel.getEducation3());
        }
        if (speakerModel.getEducation4() == null || speakerModel.getEducation4().isEmpty()) {
            education4.setVisibility(View.GONE);
        } else {
            education4.setText("•  " + speakerModel.getEducation4());
        }

        if (speakerModel.getExperience1() == null || speakerModel.getExperience1().isEmpty()) {
            experience1.setVisibility(View.GONE);
        } else {
            experience1.setText("•  " + speakerModel.getExperience1());
        }
        if (speakerModel.getExperience2() == null || speakerModel.getExperience2().isEmpty()) {
            experience2.setVisibility(View.GONE);
        } else {
            experience2.setText("•  " + speakerModel.getExperience2());
        }
        if (speakerModel.getExperience3() == null || speakerModel.getExperience3().isEmpty()) {
            experience3.setVisibility(View.GONE);
        } else {
            experience3.setText("•  " + speakerModel.getExperience3());
        }
        if (speakerModel.getExperience4() == null || speakerModel.getExperience4().isEmpty()) {
            experience4.setVisibility(View.GONE);
        } else {
            experience4.setText("•  " + speakerModel.getExperience4());
        }

        speakerPhoneNumber.setText(speakerModel.getPhone());
        speakerEmailAddress.setText(speakerModel.getEmail());
        speakerAddress.setText(speakerModel.getAddress());

        followSpeakerTV.setText("Follow " + speakerModel.getSpeakerName());


    }

    private void setImage(String featureMediaLink, final ImageView imageView) {
        JsonArrayRequest
                jsonArrayRequest
                = new JsonArrayRequest(
                Request.Method.GET,
                featureMediaLink,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(0);
                                JSONObject mediaObject = jsonObject.getJSONObject("media_details");
                                JSONObject imageSizeObject = mediaObject.getJSONObject("sizes");
                                JSONObject finalImageObject = imageSizeObject.getJSONObject("et-pb-gallery-module-image-portrait");
                                String url = finalImageObject.getString("source_url");

                                Picasso.get().load(url)
                                        .placeholder(R.drawable.ic_terrain_black_24dp)
                                        .error(R.drawable.ic_terrain_black_24dp)
                                        .into(imageView);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NoInternetDialog noInternetDialog = new NoInternetDialog(SpeakerDetailActivity.this);
                        noInternetDialog.showDialog();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }


    private void bindActivity() {


        speakerImageView = (CircleImageView) findViewById(R.id.speakerImageView);
        speakerName = (TextView) findViewById(R.id.speakerName);
        speakerCurrentPosition = (TextView) findViewById(R.id.speakerCurrentPosition);
        speakerShortDescription = (TextView) findViewById(R.id.speakerShortInfo);
//        speakerDescription = (TextView) findViewById(R.id.speakerDescriptionTextView);
        speakerQuoteTextView = (TextView) findViewById(R.id.speakerQuoteTextView);
//        aboutTextView = (TextView) findViewById(R.id.aboutTextView);

        education1 = (TextView) findViewById(R.id.education1);
        education2 = (TextView) findViewById(R.id.education2);
        education3 = (TextView) findViewById(R.id.education3);
        education4 = (TextView) findViewById(R.id.education4);

        experience1 = (TextView) findViewById(R.id.experience1);
        experience2 = (TextView) findViewById(R.id.experience2);
        experience3 = (TextView) findViewById(R.id.experience3);
        experience4 = (TextView) findViewById(R.id.experience4);

        speakerPhoneNumber = (TextView) findViewById(R.id.speakerPhoneNumber);
        speakerEmailAddress = (TextView) findViewById(R.id.speakerEmailAddress);
        speakerAddress = (TextView) findViewById(R.id.speakerAddress);

        followSpeakerTV = (TextView) findViewById(R.id.followSpeakerTV);

        facebook = (ImageView) findViewById(R.id.facebook);
        facebook.setOnClickListener(this);
        twitter = (ImageView) findViewById(R.id.twitter);
        twitter.setOnClickListener(this);
        linkedin = (ImageView) findViewById(R.id.linkedin);
        linkedin.setOnClickListener(this);
        instagram = (ImageView) findViewById(R.id.instagram);
        instagram.setOnClickListener(this);
        youtube = (ImageView) findViewById(R.id.youtube);
        youtube.setOnClickListener(this);


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
            case R.id.facebook:
                if (speakerModel.getFacebookUrl() == null || speakerModel.getFacebookUrl().isEmpty()) {
                    Toast.makeText(this, "No Facebook Availble.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent facebookIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(speakerModel.getFacebookUrl()));
                    startActivity(facebookIntent);
                }
                break;
            case R.id.twitter:
                if (speakerModel.getTwitterUrl() == null || speakerModel.getTwitterUrl().isEmpty()) {
                    Toast.makeText(this, "No Twitter Availble.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent twitterIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(speakerModel.getTwitterUrl()));
                    startActivity(twitterIntent);
                }

                break;
            case R.id.linkedin:

                if (speakerModel.getLinkedInUrl() == null || speakerModel.getLinkedInUrl().isEmpty()) {
                    Toast.makeText(this, "No Linkedin Availble.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent linkedIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(speakerModel.getLinkedInUrl()));
                    startActivity(linkedIntent);
                }



                break;
            case R.id.instagram:

                if (speakerModel.getInstagramUrl() == null || speakerModel.getInstagramUrl().isEmpty()) {
                    Toast.makeText(this, "No Instagram Availble.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent instagramIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(speakerModel.getInstagramUrl()));
                    startActivity(instagramIntent);
                }


                break;
            case R.id.youtube:

                if (speakerModel.getYoutubeUrl() == null || speakerModel.getYoutubeUrl().isEmpty()) {
                    Toast.makeText(this, "No Youtube Availble.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(speakerModel.getYoutubeUrl()));
                    startActivity(youtubeIntent);
                }
                break;
        }

    }
}