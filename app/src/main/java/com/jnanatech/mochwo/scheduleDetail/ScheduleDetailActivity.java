package com.jnanatech.mochwo.scheduleDetail;

import android.app.AlarmManager;
import android.app.PendingIntent;
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

import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.bookmark.model.BookmarkModel;
import com.jnanatech.mochwo.main.model.Conference;
import com.jnanatech.mochwo.schedule.model.EventModel;
import com.jnanatech.mochwo.speakerDetail.model.ChipTag;
import com.jnanatech.mochwo.utils.AlarmReceiver;
import com.jnanatech.mochwo.utils.Constants;
import com.jnanatech.mochwo.utils.database.RealmController;
import com.jnanatech.mochwo.utils.sharedPreference.ScheduleNotificationNumberSharedPrefHelper;
import com.plumillonforge.android.chipview.Chip;
import com.plumillonforge.android.chipview.ChipView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ScheduleDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private EventModel event = new EventModel();
    private TextView scheduleTitle;
    private TextView scheduleTime;
    private TextView scheduleDate;

    private TextView speakerName;
    private TextView speakerDetail;

    private ImageView bookMarkImageView;
    private TextView bookMarkTextView;

    private TextView eventDetailTextView;

    private ImageView shareImageView;

    private LinearLayout speakerLL;
    private TextView noSpeakerTextView;

    ChipView keywordChipGroup;


    RealmController realmController;
    Conference conference;

    boolean bookMarked = false;

    ArrayList<BookmarkModel> bookmarkModels = new ArrayList<>();

    AlarmManager alarm;
    PendingIntent notificationPendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_detail);

        realmController = RealmController.with(this);
        conference = realmController.getConference();
        bookmarkModels = realmController.getBookmarks();
        getEvent();

        bindActivity();


    }

    private void getEvent() {

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        if (bundle != null) {
            event.setId(bundle.getString(Constants.scheduleIDConstant));
            event.setStartTime(bundle.getString(Constants.scheduleStartTimeConstant));
            event.setEndTime(bundle.getString(Constants.scheduleEndTimeConstant));
            event.setChairPersonName(bundle.getString(Constants.scheduleChairPersonConstant));
            event.setChairPersonDetail(bundle.getString(Constants.scheduleChairPersonDetailConstant));

            event.setEventTitle(bundle.getString(Constants.scheduleTopicConstant));
            event.setEventSpeaker(bundle.getString(Constants.scheduleSpeakersConstant));
            event.setSpeakerDetail(bundle.getString(Constants.scheduleSpeakersDetailConstant));
            event.setAbstractDetail(bundle.getString(Constants.scheduleDescriptionConstant));
            event.setKeywords(bundle.getString(Constants.scheduleKeywordsConstant));

            event.setScheduleName(bundle.getString(Constants.scheduleScheduleConstant));
            event.setSessionTitle(bundle.getString(Constants.scheduleSessionConstant));
            event.setBookmarked(bundle.getBoolean(Constants.scheduleBookmarkConstant));


        } else {
            Toast.makeText(this, "No Data Availble.", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }

    private void bindActivity() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);

        noSpeakerTextView = (TextView) findViewById(R.id.noSpeakerTextView);


        scheduleTitle = (TextView) findViewById(R.id.scheduleTitle);
        scheduleTitle.setText(event.getEventTitle());

        scheduleTime = (TextView) findViewById(R.id.scheduleTimeTextView);
        scheduleTime.setText(event.getStartTime() + " - " + event.getEndTime());

        scheduleDate = (TextView) findViewById(R.id.scheduleDate);

        if (event.getScheduleName().equalsIgnoreCase("d1-schedule")) {
            scheduleDate.setText("October 18, 2019");
        } else {
            scheduleDate.setText("October 19, 2019");
        }


        speakerName = (TextView) findViewById(R.id.speakersTextView);
        speakerName.setText(event.getEventSpeaker());
        speakerDetail = (TextView) findViewById(R.id.speakerDetail);
        speakerDetail.setText(event.getSpeakerDetail());

//        if(speaker!=null){
//            speakerLL = (LinearLayout) findViewById(R.id.speakerLL);
//            speakerLL.setOnClickListener(this);
//
//
//            speakerName = (TextView) findViewById(R.id.speakerName);
//            speakerName.setText(event.getEventSpeaker());
//
//            speakerDetail = (TextView) findViewById(R.id.speakerDetail);
//            speakerDetail.setText(event.getSpeakerDetail());
//        }
//

        bookMarkImageView = (ImageView) findViewById(R.id.bookmarkImageView);
        bookMarkImageView.setOnClickListener(this);
        bookMarkTextView = (TextView) findViewById(R.id.bookMarkTextView);

        eventDetailTextView = (TextView) findViewById(R.id.eventDetailTextView);
        eventDetailTextView.setText(event.getAbstractDetail());

        keywordChipGroup = (ChipView) findViewById(R.id.keywordChipGroup);
        String[] keyWordsArray = event.getKeywords().split(",");
        List<Chip> chipList = new ArrayList<>();
        for (int i = 0; i < keyWordsArray.length; i++) {
            chipList.add(new ChipTag(keyWordsArray[i]));
        }
        keywordChipGroup.setChipList(chipList);

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

//                Intent intent = new Intent(this, SpeakerDetailActivity.class);
//                intent.putExtra(Constants.speakerConstant, speaker.getSpeakerName());
//                startActivity(intent);
//                break;

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

            setUpNotification();
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




    public void setUpNotification()  {

        ScheduleNotificationNumberSharedPrefHelper sharedPrefHelper = new ScheduleNotificationNumberSharedPrefHelper(this);

        Intent intent = new Intent(ScheduleDetailActivity.this, AlarmReceiver.class);
        intent.putExtra("notificationid", sharedPrefHelper.getCount());
        intent.putExtra("notificationDetail", event.getEventTitle() + " is starting in 1 hour.");

        notificationPendingIntent = PendingIntent.getBroadcast(ScheduleDetailActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        alarm = (AlarmManager) getSystemService(ALARM_SERVICE);


        SimpleDateFormat df = new SimpleDateFormat("HH:mm");

// replace with your start date string
        String[] splitByColon = event.getStartTime().split(":");
        int hoursValue = Integer.parseInt(splitByColon[0]);

        if(event.getStartTime().toLowerCase().contains("am")){
            hoursValue = hoursValue + 12;
        }
        String[] splitForMins = splitByColon[1].split(" ");
        int minutesValue = Integer.parseInt(splitForMins[0]);

        //create time
        Calendar startTime = Calendar.getInstance();
        startTime.set(Calendar.HOUR_OF_DAY, hoursValue-1);
        startTime.set(Calendar.MINUTE, minutesValue);
        startTime.set(Calendar.SECOND, 0);

        long alarmStartTime = startTime.getTimeInMillis();

        //set alarm
        alarm.set(AlarmManager.RTC_WAKEUP, alarmStartTime,notificationPendingIntent);


    }

    private void removeFromBookmark(String id) {
        for (int i = 0; i < bookmarkModels.size(); i++) {
            if (bookmarkModels.get(i).getId().equals(id)) {
                bookmarkModels.remove(i);
            }
        }

        realmController.clearBookmarks();
        for (int i = 0; i < bookmarkModels.size(); i++) {
            realmController.addBookmark(bookmarkModels.get(i));
        }

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
