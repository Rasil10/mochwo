package com.jnanatech.mochwo.main.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import android.view.Gravity;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.aboutUs.view.AboutUsActivity;
import com.jnanatech.mochwo.contactUs.ContactUsActivity;
import com.jnanatech.mochwo.speakers.view.SpeakersActivity;
import com.jnanatech.mochwo.sponsers.view.SponserActivity;
import com.jnanatech.mochwo.utils.Constants;
import com.onesignal.OneSignal;

import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainView, View.OnClickListener {

    private DrawerLayout drawer;
    private FloatingActionMenu fabMenu;
    private FloatingActionButton fabCall;
    private FloatingActionButton fabWebsite;
    private FloatingActionButton fabLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        bindActivity();
    }

    private void bindActivity() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fabMenu = (FloatingActionMenu) findViewById(R.id.fabMenu);

        fabCall = (FloatingActionButton) findViewById(R.id.fabCall);
        fabCall.setOnClickListener(this);

        fabWebsite = (FloatingActionButton) findViewById(R.id.fabWebsite);
        fabWebsite.setOnClickListener(this);

        fabLocation = (FloatingActionButton) findViewById(R.id.fabLocation);
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
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_speaker) {
            startActivity(new Intent(MainActivity.this, SpeakersActivity.class));
        } else if (id == R.id.nav_sponser) {
            startActivity(new Intent(MainActivity.this, SponserActivity.class));

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
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fabCall:
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("977-1-6924204"));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, Constants.phonePermissionConstant);
                    } else {
                        startActivity(callIntent);

                    }
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
        }

    }

    @Override
    public void getRecentEvent() {

    }
}
