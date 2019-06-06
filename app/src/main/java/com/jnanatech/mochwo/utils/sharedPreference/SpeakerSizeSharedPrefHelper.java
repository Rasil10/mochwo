package com.jnanatech.mochwo.utils.sharedPreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.jnanatech.mochwo.utils.Constants;

public class SpeakerSizeSharedPrefHelper {
    Context context;
    public static SpeakerSizeSharedPrefHelper sharedInstance;
    String TOKEN;

    public static SpeakerSizeSharedPrefHelper getSharedInstance(Context context) {

        if (sharedInstance == null) {
            sharedInstance = new SpeakerSizeSharedPrefHelper(context);
        }
        return sharedInstance;
    }

    public SpeakerSizeSharedPrefHelper(Context context) {
        this.context = context;
    }


    public void saveSpeakerNumber(Integer size) {
        SharedPreferences.Editor editor = context.getSharedPreferences(Constants.speakerSharedPrefConstant, context.MODE_PRIVATE).edit();
        editor.putInt(Constants.speakerSizeConstant, size);
        editor.commit();
    }


    public int getSpeakerSize() {
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
