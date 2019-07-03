package com.jnanatech.mochwo.main.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.aboutUs.view.AboutUsActivity;
import com.jnanatech.mochwo.bookmark.view.BookmarkActivity;
import com.jnanatech.mochwo.contactUs.ContactUsActivity;
import com.jnanatech.mochwo.main.model.Conference;
import com.jnanatech.mochwo.main.presenter.MainImplementor;
import com.jnanatech.mochwo.main.presenter.MainPresenter;
import com.jnanatech.mochwo.material.view.MaterialsActivity;
import com.jnanatech.mochwo.notification.view.NotificationActivity;
import com.jnanatech.mochwo.schedule.view.ScheduleActivity;
import com.jnanatech.mochwo.search.view.SearchActivity;
import com.jnanatech.mochwo.speakers.view.SpeakersActivity;
import com.jnanatech.mochwo.sponsers.view.SponserActivity;
import com.jnanatech.mochwo.utils.AbstractSubmissionDialog;
import com.jnanatech.mochwo.utils.Constants;
import com.jnanatech.mochwo.utils.PastEventDialog;
import com.onesignal.OneSignal;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView, View.OnClickListener, View.OnLongClickListener {

    private DrawerLayout drawer;
    private NestedScrollView mainView;

    private ImageView conferenceEventImage;
    private TextView conferenceTitle;
    private TextView conferenceDate;
    private TextView conferenceQuote;

    private TextView timerTextView;
    private CountDownTimer mCountDownTimer;

    private long remainingTime;
    MainPresenter mainPresenter;
    Conference conference;
    private Menu menu;
    Boolean changeNotificationIcon = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        bindActivity();


        mainPresenter = new MainImplementor(MainActivity.this, this);
        mainPresenter.setConference();
        mainPresenter.setRemainingTime();
        mainPresenter.setSchedule1();
        mainPresenter.setSchedule2();
        mainPresenter.getSpeakers();
        mainPresenter.getUpdates();
        mainPresenter.getSponsers();


    }

    private void bindActivity() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainView = (NestedScrollView) findViewById(R.id.mainView);

        FloatingActionButton fabCall = (FloatingActionButton) findViewById(R.id.fabCall);
        fabCall.setOnClickListener(this);

        FloatingActionButton fabWebsite = (FloatingActionButton) findViewById(R.id.fabWebsite);
        fabWebsite.setOnClickListener(this);

        FloatingActionButton fabLocation = (FloatingActionButton) findViewById(R.id.fabLocation);
        fabLocation.setOnClickListener(this);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.ic_sort_black_24dp);
        toggle.setDrawerSlideAnimationEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }

        });

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        conferenceEventImage = (ImageView) findViewById(R.id.conferenceEventImageView);
        conferenceTitle = (TextView) findViewById(R.id.conferenceTitle);
        conferenceQuote = (TextView) findViewById(R.id.conferenceQuote);
        conferenceDate = (TextView) findViewById(R.id.conferenceDate);

        timerTextView = (TextView) findViewById(R.id.timerTextView);


        CardView scheduleCardView = (CardView) findViewById(R.id.scheduleCardView);
        scheduleCardView.setOnClickListener(this);
        scheduleCardView.setOnLongClickListener(this);

        CardView speakersCardView = (CardView) findViewById(R.id.speakersCardView);
        speakersCardView.setOnClickListener(this);
        speakersCardView.setOnLongClickListener(this);

        CardView sponsersCardView = (CardView) findViewById(R.id.sponsersCardView);
        sponsersCardView.setOnClickListener(this);
        sponsersCardView.setOnLongClickListener(this);

        CardView materialsCardView = (CardView) findViewById(R.id.materialsCardView);
        materialsCardView.setOnClickListener(this);
        materialsCardView.setOnLongClickListener(this);

        CardView aboutCardView = (CardView) findViewById(R.id.aboutCardView);
        aboutCardView.setOnClickListener(this);
        aboutCardView.setOnLongClickListener(this);

        CardView mapCardView = (CardView) findViewById(R.id.mapCardView);
        mapCardView.setOnClickListener(this);
        mapCardView.setOnLongClickListener(this);

        CardView abstractCardView = (CardView) findViewById(R.id.abstractCardView);
        abstractCardView.setOnClickListener(this);
        abstractCardView.setOnLongClickListener(this);

        CardView searchCardView = (CardView) findViewById(R.id.searchCardView);
        searchCardView.setOnClickListener(this);
        searchCardView.setOnLongClickListener(this);

        CardView notificationCardView = (CardView) findViewById(R.id.notificationCardView);
        notificationCardView.setOnClickListener(this);
        notificationCardView.setOnLongClickListener(this);

    }

    private void setUpEvent() throws ParseException {

        Picasso.get()
                .load(conference.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.ic_terrain_black_24dp)
                .into(conferenceEventImage);

        conferenceTitle.setText(conference.getTitle());
        conferenceQuote.setText(conference.getQuote());

        setUpDate();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fabCall:
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(getResources().getString(R.string.kiasPhoneNumber)));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, Constants.phonePermissionConstant);
                    } else {
                        startActivity(callIntent);

                    }
                } else {
                    startActivity(callIntent);
                }
                break;

            case R.id.fabWebsite:
                Intent webIntent = new Intent(Intent.ACTION_VIEW);
                webIntent.setData(Uri.parse("http://conference.kias.org.np/"));
                startActivity(webIntent);
                break;

            case R.id.fabLocation:

                Uri gmmIntentUri = Uri.parse("geo:27.6714696,85.2996738");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                } else {
                    String geoUri = "http://maps.google.com/maps?q=loc:" + 27.6714696 + "," + 85.2996738 + " (" + "KIAS" + ")";
                    mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                    startActivity(mapIntent);
                }
                break;

            case R.id.scheduleCardView:
//                ScheduleDialog scheduleDialog = new ScheduleDialog();
//                scheduleDialog.showDialog(this, "s");
                startActivity(new Intent(MainActivity.this, ScheduleActivity.class));
                break;
            case R.id.speakersCardView:
                startActivity(new Intent(MainActivity.this, SpeakersActivity.class));
                break;
            case R.id.sponsersCardView:
                Intent sponserIntent = new Intent(MainActivity.this, SponserActivity.class);
                startActivity(sponserIntent);
                break;
            case R.id.materialsCardView:
                startActivity(new Intent(MainActivity.this, MaterialsActivity.class));
                break;
            case R.id.aboutCardView:
                startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                break;
            case R.id.mapCardView:
                Uri gmmIntentUri2 = Uri.parse("geo:27.6714696,85.2996738");
                Intent mapIntent2 = new Intent(Intent.ACTION_VIEW, gmmIntentUri2);
                mapIntent2.setPackage("com.google.android.apps.maps");

                if (mapIntent2.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent2);
                } else {
                    String geoUri = "http://maps.google.com/maps?q=loc:" + 27.6714696 + "," + 85.2996738 + " (" + "KIAS" + ")";
                    mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                    startActivity(mapIntent);
                }
                break;
            case R.id.abstractCardView:
                AbstractSubmissionDialog abstractSubmissionDialog = new AbstractSubmissionDialog();
                abstractSubmissionDialog.showDialog(this, " Abstract ");
                break;
            case R.id.searchCardView:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                break;
            case R.id.notificationCardView:
                Intent notificationIntent = new Intent(MainActivity.this, NotificationActivity.class);
                startActivityForResult(notificationIntent, Constants.NotificationActivityConstant);
                break;


        }


    }


    @Override
    public void getEvent(Conference conference) {
        this.conference = conference;

        try {
            setUpEvent();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void getRemainingTime(long remainingTime) {
        this.remainingTime = remainingTime;

        try {
            setUpTimer();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getSpeakerChangeSize(int changeInSize, int originalSize) {
        if (changeInSize == 0) {
            invalidateOptionsMenu();
            changeNotificationIcon = false;
        } else {
            invalidateOptionsMenu();
            changeNotificationIcon = true;
        }
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Toggle location icon
        if (changeNotificationIcon) {
            menu.findItem(R.id.action_notification).setIcon(R.mipmap.ic_notification_true);
        } else {
            menu.findItem(R.id.action_notification).setIcon(R.drawable.ic_os_notification_fallback_white_24dp);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void setUpTimer() throws ParseException {

        mCountDownTimer = new CountDownTimer(remainingTime, 1000) {
            public void onTick(long millisUntilFinished) {


                String day = TimeUnit.HOURS.toDays(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)) + "";

                if (day.equalsIgnoreCase("0")) {
                    day = "";
                } else if (day.equalsIgnoreCase("1")) {
                    day = day + " day ";
                } else {
                    day = day + " days ";
                }

                String hr = (TimeUnit.MILLISECONDS.toHours(millisUntilFinished) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millisUntilFinished))) + "";
                String min = (TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished))) + "";
                String ss = (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))) + "";
                timerTextView.setText(day + hr + " Hrs " + min + " Min " + ss + " Sec ");

            }

            public void onFinish() {
                timerTextView.setVisibility(View.GONE);
            }
        }.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        this.menu = menu;

        return true;
    }

    private void setUpDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.dateFormatDefault);
        Date startDateDate = null;
        Date endDateDate = null;
        try {
            startDateDate = dateFormat.parse(conference.getStartDate());
            endDateDate = dateFormat.parse(conference.getEndDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat targetFormat = new SimpleDateFormat("MMMM dd, yyyy");
        String startDate = targetFormat.format(startDateDate);

        int endMonth = endDateDate.getDate();

        String[] parts = startDate.split(",");
        String firstPart = parts[0];
        String secondPart = parts[1];

        String totalDate = firstPart + " - " + endMonth + ", " + secondPart;

        conferenceDate.setText(totalDate);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            startActivity(new Intent(MainActivity.this, SearchActivity.class));
            return true;
        } else if (id == R.id.action_notification) {
            Intent notificationIntent = new Intent(MainActivity.this, NotificationActivity.class);
            startActivityForResult(notificationIntent, Constants.NotificationActivityConstant);
            return true;
        } else if (id == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_past_events) {
            PastEventDialog pastEventDialog = new PastEventDialog();
            pastEventDialog.showDialog(MainActivity.this, " Past Events ");

        } else if (id == R.id.nav_bookmark) {
            startActivity(new Intent(MainActivity.this, BookmarkActivity.class));
        } else if (id == R.id.nav_about_us) {
            startActivity(new Intent(MainActivity.this, AboutUsActivity.class));

        } else if (id == R.id.nav_contact_us) {
            startActivity(new Intent(MainActivity.this, ContactUsActivity.class));

        } else if (id == R.id.nav_share) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.scheduleCardView:
                Toast.makeText(this, "View Schedule", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.speakersCardView:
                Toast.makeText(this, "View Speakers", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.sponsersCardView:
                Toast.makeText(this, "View Sponsers", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.materialsCardView:
                Toast.makeText(this, "View Materials", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.aboutCardView:
                Toast.makeText(this, "View About", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.mapCardView:
                Toast.makeText(this, "View Map", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.abstractCardView:
                Toast.makeText(this, "View Abstract", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.searchCardView:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.notificationCardView:
                Toast.makeText(this, "View Notification", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.NotificationActivityConstant && resultCode == RESULT_OK) {
            menu.findItem(R.id.action_notification).setIcon(R.drawable.ic_os_notification_fallback_white_24dp);
            changeNotificationIcon = false;
        } else {
            menu.findItem(R.id.action_notification).setIcon(R.mipmap.ic_notification_true);
            changeNotificationIcon = true;
        }
    }
}
