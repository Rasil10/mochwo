package com.jnanatech.mochwo.speakers.presenter;

import android.app.Activity;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.jnanatech.mochwo.speakers.model.SpeakerModel;
import com.jnanatech.mochwo.speakers.view.SpeakersView;
import com.jnanatech.mochwo.utils.Constants;
import com.jnanatech.mochwo.utils.NoInternetDialog;
import com.jnanatech.mochwo.utils.database.RealmController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SpeakerImplementor implements SpeakerPresenter {

    Context context;
    SpeakersView speakersView;
    RealmController realmController;

    ArrayList<SpeakerModel> speakerModels = new ArrayList<>();
    private RequestQueue requestQueue;


    public SpeakerImplementor(Context context, SpeakersView speakersView) {
        this.context = context;
        this.speakersView = speakersView;
        requestQueue = Volley.newRequestQueue(context);
        realmController = RealmController.with((Activity) context);

    }

    @Override
    public void setSpeakers() {

       speakersView.getSpeaker(realmController.getAllSpeakersList());
    }




}
