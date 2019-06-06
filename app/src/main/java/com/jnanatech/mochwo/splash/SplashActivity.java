package com.jnanatech.mochwo.splash;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.jnanatech.mochwo.R;
import com.jnanatech.mochwo.main.view.MainActivity;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener {

    private ImageView icon;
    ImageView mochwoTextView;
    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        modifyStatusBar();


        icon = findViewById(R.id.iv_icons);
        mochwoTextView = findViewById(R.id.tv_intro);

        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_rotation);
        icon.startAnimation(animation2);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_zoom_in);
        mochwoTextView.startAnimation(animation);
        animation.setAnimationListener(this);
    }

    private void modifyStatusBar() {

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
        decorView.setSystemUiVisibility(uiOptions);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark)); //status bar or the time bar at the top
        }

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        SplashActivity.this.finish();
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


}
