package com.jnanatech.mochwo.main.view;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.jnanatech.mochwo.News.view.NewsActivity;
import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.aboutUs.view.AboutUsActivity;
import com.jnanatech.mochwo.bookmark.view.BookmarkActivity;
import com.jnanatech.mochwo.contactUs.ContactUsActivity;
import com.jnanatech.mochwo.main.model.Conference;
import com.jnanatech.mochwo.main.presenter.MainImplementor;
import com.jnanatech.mochwo.main.presenter.MainPresenter;
import com.jnanatech.mochwo.notification.model.NotificationModel;
import com.jnanatech.mochwo.notification.view.NotificationActivity;
import com.jnanatech.mochwo.schedule.view.ScheduleActivity;
import com.jnanatech.mochwo.search.view.SearchActivity;
import com.jnanatech.mochwo.speakers.view.SpeakersActivity;
import com.jnanatech.mochwo.sponsers.view.SponserActivity;
import com.jnanatech.mochwo.utils.AbstractSubmissionDialog;
import com.jnanatech.mochwo.utils.Constants;
import com.jnanatech.mochwo.utils.PastEventDialog;
import com.jnanatech.mochwo.utils.sharedPreference.NotificationSizeSharedPrefHelper;
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

    private TextView conferenceDate;

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
        mainPresenter.getNews();
        mainPresenter.checkNotificationSize();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 20 seconds
                mainPresenter.getUpdates();
                mainPresenter.checkNotificationSize();
                handler.postDelayed(this, 8000);
            }
        }, 8000);


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

        conferenceDate = (TextView) findViewById(R.id.conferenceDate);


        LinearLayout scheduleCardView = (LinearLayout) findViewById(R.id.scheduleCardView);
        scheduleCardView.setOnClickListener(this);
        scheduleCardView.setOnLongClickListener(this);

        LinearLayout speakersCardView = (LinearLayout) findViewById(R.id.speakersCardView);
        speakersCardView.setOnClickListener(this);
        speakersCardView.setOnLongClickListener(this);

        LinearLayout sponsersCardView = (LinearLayout) findViewById(R.id.sponsersCardView);
        sponsersCardView.setOnClickListener(this);
        sponsersCardView.setOnLongClickListener(this);

        LinearLayout bookmarksCardView = (LinearLayout) findViewById(R.id.bookmarksCardView);
        bookmarksCardView.setOnClickListener(this);
        bookmarksCardView.setOnLongClickListener(this);

        LinearLayout aboutCardView = (LinearLayout) findViewById(R.id.aboutCardView);
        aboutCardView.setOnClickListener(this);
        aboutCardView.setOnLongClickListener(this);

        LinearLayout mapCardView = (LinearLayout) findViewById(R.id.mapCardView);
        mapCardView.setOnClickListener(this);
        mapCardView.setOnLongClickListener(this);

        LinearLayout abstractCardView = (LinearLayout) findViewById(R.id.abstractCardView);
        abstractCardView.setOnClickListener(this);
        abstractCardView.setOnLongClickListener(this);

        LinearLayout newsCardView = (LinearLayout) findViewById(R.id.newsCardView);
        newsCardView.setOnClickListener(this);
        newsCardView.setOnLongClickListener(this);

        LinearLayout notificationCardView = (LinearLayout) findViewById(R.id.notificationCardView);
        notificationCardView.setOnClickListener(this);
        notificationCardView.setOnLongClickListener(this);

    }

    private void setUpEvent() throws ParseException {



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
            case R.id.bookmarksCardView:
                startActivity(new Intent(MainActivity.this, BookmarkActivity.class));
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
            case R.id.newsCardView:
                startActivity(new Intent(MainActivity.this, NewsActivity.class));
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

//        try {
//            setUpTimer();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void getNotificationChangeSize(int changeInSize, int newSize, boolean changed, NotificationModel notificationModel) {
        if (changeInSize == 0) {
            invalidateOptionsMenu();
            changeNotificationIcon = false;

        } else if(changeInSize>0){
            invalidateOptionsMenu();
            changeNotificationIcon = true;
            try {
                NotificationSizeSharedPrefHelper notificationSizeSharedPrefHelper = new NotificationSizeSharedPrefHelper(this);
                notificationSizeSharedPrefHelper.saveNotificationNumberNumber(newSize);
//
//
//                Intent intent = new Intent(this, NotificationActivity.class);
//                PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//                NotificationCompat.Builder b = new NotificationCompat.Builder(this);
//
//                b.setAutoCancel(true)
//                        .setDefaults(Notification.DEFAULT_ALL)
//                        .setWhen(System.currentTimeMillis())
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setContentTitle(notificationModel.getTitle())
//                        .setContentText(Html.fromHtml(notificationModel.getDetail()))
//                        .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
//                        .setContentIntent(contentIntent);
//
//                NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//                notificationManager.notify(newSize, b.build());

                Toast.makeText(this, "New Notification", Toast.LENGTH_SHORT).show();

                CharSequence channelName = notificationModel.getTitle();
                String channelDesc = "channelDesc";
                // Create the NotificationChannel, but only on API 26+ because
                // the NotificationChannel class is new and not in the support library
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel channel = new NotificationChannel(notificationModel.getTitle(), channelName, importance);
                    channel.setDescription(channelDesc);
                    // Register the channel with the system; you can't change the importance
                    // or other notification behaviors after this
                    NotificationManager notificationManager = getSystemService(NotificationManager.class);
                    assert notificationManager != null;
                    NotificationChannel currChannel = notificationManager.getNotificationChannel(notificationModel.getTitle());
                    if (currChannel == null)
                        notificationManager.createNotificationChannel(channel);
                }

                Intent intent = new Intent(this, NotificationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, notificationModel.getTitle())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(notificationModel.getTitle())
                        .setContentText(Html.fromHtml(notificationModel.getDetail()))
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);
                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                mBuilder.setSound(uri);


                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                int notificationId = (int) (System.currentTimeMillis()/4);
                notificationManager.notify(notificationId, mBuilder.build());



            } catch (Exception e) {
                e.printStackTrace();
            }
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
            startActivity(notificationIntent);
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

        }
//        else if (id == R.id.nav_bookmark) {
//            startActivity(new Intent(MainActivity.this, NewsActivity.class));
//        }
        else if (id == R.id.nav_about_us) {
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
                Toast.makeText(this, "View Our Collaborators" +
                        "", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.bookmarksCardView:
                Toast.makeText(this, "View Bookmarks", Toast.LENGTH_SHORT).show();
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
            case R.id.newsCardView:
                Toast.makeText(this, "View News", Toast.LENGTH_SHORT).show();
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
