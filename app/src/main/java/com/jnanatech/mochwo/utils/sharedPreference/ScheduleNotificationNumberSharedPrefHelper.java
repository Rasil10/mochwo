package com.jnanatech.mochwo.utils.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.jnanatech.mochwo.utils.Constants;

public class ScheduleNotificationNumberSharedPrefHelper {
    Context context;
    public static ScheduleNotificationNumberSharedPrefHelper sharedInstance;
    String TOKEN;

    public static ScheduleNotificationNumberSharedPrefHelper getSharedInstance(Context context) {

        if (sharedInstance == null) {
            sharedInstance = new ScheduleNotificationNumberSharedPrefHelper(context);
        }
        return sharedInstance;
    }

    public ScheduleNotificationNumberSharedPrefHelper(Context context) {
        this.context = context;
    }


    public void saveCount() {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.speakerSharedPrefConstant, context.MODE_PRIVATE).edit();
        editor.putInt(Constants.speakerSizeConstant, getCount()+1);
        getCount();
        editor.commit();
    }


    public int getCount() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.speakerSharedPrefConstant, context.MODE_PRIVATE);
        Integer size = sharedPreferences.getInt(Constants.speakerSizeConstant, 0);
        return size;
    }

    public void clearSize() {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.speakerSharedPrefConstant, context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();

    }
}
