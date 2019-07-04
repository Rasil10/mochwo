package com.jnanatech.mochwo.utils.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.jnanatech.mochwo.utils.Constants;

public class NotificationSizeSharedPrefHelper {
    Context context;
    public static NotificationSizeSharedPrefHelper sharedInstance;
    String TOKEN;

    public static NotificationSizeSharedPrefHelper getSharedInstance(Context context) {

        if (sharedInstance == null) {
            sharedInstance = new NotificationSizeSharedPrefHelper(context);
        }
        return sharedInstance;
    }

    public NotificationSizeSharedPrefHelper(Context context) {
        this.context = context;
    }


    public void saveNotificationNumberNumber(Integer size) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.speakerSharedPrefConstant, context.MODE_PRIVATE).edit();
        editor.putInt(Constants.speakerSizeConstant, size);
        editor.commit();
    }


    public int getNotificationSize() {
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
