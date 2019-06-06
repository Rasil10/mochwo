package com.jnanatech.mochwo.scheduleDetail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.jnanatech.mochwo.bookmark.model.BookmarkModel;
import com.jnanatech.mochwo.main.model.Conference;
import com.jnanatech.mochwo.schedule.model.EventModel;
import com.jnanatech.mochwo.speakerDetail.view.SpeakerDetailActivity;
import com.jnanatech.mochwo.speakers.model.SpeakerModel;
import com.jnanatech.mochwo.utils.Constants;
import com.jnanatech.mochwo.utils.database.RealmController;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ScheduleDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private EventModel event = new EventModel();
    private SpeakerModel speaker;
    private TextView scheduleTitle;
    private TextView scheduleTime;
    private TextView scheduleDate;

    private CircleImageView speakerImageView;
    private TextView speakerName;
    private TextView speakerPosition;
    private TextView scheduleLocation;

    private ImageView bookMarkImageView;
    private TextView bookMarkTextView;

    private ImageView shareImageView;

    private LinearLayout speakerLL;

    RealmController realmController;
    Conference conference;

    boolean bookMarked = false;

    ArrayList<BookmarkModel> bookmarkModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_detail);

        realmController = RealmController.with(this);
        conference = realmController.getConference();
        bookmarkModels = realmController.getBookmarks();

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        if (bundle != null) {
            event.setStartTime(bundle.getString(Constants.scheduleStartTimeConstant));
            event.setEndTime(bundle.getString(Constants.scheduleEndTimeConstant));
            event.setTopic(bundle.getString(Constants.scheduleTopicConstant));
            event.setDescription(bundle.getString(Constants.scheduleDescriptionConstant));
            event.setKeywords(bundle.getString(Constants.scheduleKeywordsConstant));
            event.setSpeakers(bundle.getString(Constants.scheduleSpeakersConstant));
            event.setId(bundle.getString(Constants.scheduleIDConstant));
            event.setScheduleName(bundle.getString(Constants.scheduleScheduleConstant));
            event.setSessionName(bundle.getString(Constants.scheduleSessionConstant));
            event.setBookmarked(bundle.getBoolean(Constants.scheduleBookmarkConstant));


            if (realmController.getSpeakerByName(event.getSpeakers()) == null) {
                onBackPressed();
                Toast.makeText(this, "No Data Availble.", Toast.LENGTH_SHORT).show();

            } else {
                speaker = realmController.getSpeakerByName(event.getSpeakers());
            }


        } else {
            Toast.makeText(this, "No Data Availble.", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }

        bindActivity();
    }

    private void bindActivity() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        scheduleTitle = (TextView) findViewById(R.id.scheduleTitle);
        scheduleTitle.setText(event.getTopic());

        scheduleTime = (TextView) findViewById(R.id.scheduleTimeTextView);
        scheduleTime.setText(event.getStartTime() + " - " + event.getEndTime());

        scheduleDate = (TextView) findViewById(R.id.scheduleDate);

        if (event.getScheduleName().equalsIgnoreCase("schedule_d1")) {
            scheduleDate.setText("October 18, 2019");
        } else {
            scheduleDate.setText("October 19, 2019");
        }

        speakerLL = (LinearLayout) findViewById(R.id.speakerLL);
        speakerLL.setOnClickListener(this);

        speakerImageView = (CircleImageView) findViewById(R.id.speakerImageView);
        setImage(speaker.getFeatureMediaLink(), speakerImageView);

        speakerName = (TextView) findViewById(R.id.speakerName);
        speakerName.setText(speaker.getSpeakerName());

        speakerPosition = (TextView) findViewById(R.id.speakerPosition);
        speakerPosition.setText(speaker.getCurrentPosition());

        scheduleLocation = (TextView) findViewById(R.id.scheduleLocation);
        scheduleLocation.setText(conference.getLocation());

        bookMarkImageView = (ImageView) findViewById(R.id.bookmarkImageView);
        bookMarkImageView.setOnClickListener(this);
        bookMarkTextView = (TextView) findViewById(R.id.bookMarkTextView);

        Log.d("bookmarkStatus", event.isBookmarked() + "---activity check first");

        Boolean flag = false;
        for (int i = 0; i < bookmarkModels.size(); i++) {
            if (bookmarkModels.get(i).getId().equals(event.getId())) {
                flag = true;
                break;
            } else {
                flag = false;
            }
        }
        if (flag) {
            bookMarkImageView.setImageResource(R.drawable.ic_bookmark_true);
            bookMarkTextView.setText("Bookmarked");

            bookMarked = true;
        } else {
            bookMarkImageView.setImageResource(R.drawable.ic_bookmark_false);
            bookMarkTextView.setText("Bookmark");

            bookMarked = false;
        }

        shareImageView = (ImageView) findViewById(R.id.shareImageView);
        shareImageView.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.speakerLL:

                Intent intent = new Intent(this, SpeakerDetailActivity.class);
                intent.putExtra(Constants.speakerConstant, speaker.getSpeakerName());
                startActivity(intent);
                break;

            case R.id.bookmarkImageView:

                if (bookMarked) {
                    bookMarked = false;
                } else {
                    bookMarked = true;
                }
                updateBookmarkIcon(bookMarked);
                break;

            case R.id.shareImageView:
                String message = "Share this Event...";
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_TEXT, message);

                startActivity(Intent.createChooser(share, "Share this Event."));
        }
    }

    private void updateBookmarkIcon(boolean bookMarked) {
        if (bookMarked) {
            bookMarkImageView.setImageResource(R.drawable.ic_bookmark_true);
            bookMarkTextView.setText("Bookmarked");
            event.setBookmarked(true);
            realmController.addBookmark(new BookmarkModel(event.getId()));
            realmController.updateEvent(event);

        } else {
            bookMarkImageView.setImageResource(R.drawable.ic_bookmark_false);
            bookMarkTextView.setText("Bookmark");
            event.setBookmarked(false);
            realmController.updateEvent(event);

            removeFromBookmark(event.getId());

        }
    }

    private void removeFromBookmark(String id) {
        Log.d("bookmarkSize", bookmarkModels.size() + "-- before");
        for (int i = 0; i < bookmarkModels.size(); i++) {
            if (bookmarkModels.get(i).getId().equals(id)) {
                bookmarkModels.remove(i);
            }
        }

        Log.d("bookmarkSize", bookmarkModels.size() + "-- after");
        realmController.clearBookmarks();
        for (int i = 0; i < bookmarkModels.size(); i++) {
            realmController.addBookmark(bookmarkModels.get(i));
        }

    }

    private void setImage(String featureMediaLink, final CircleImageView speakerImageView) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
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
                                        .into(speakerImageView);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ScheduleDetailActivity.this, "Error Loading Image.", Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
