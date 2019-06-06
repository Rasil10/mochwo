package com.jnanatech.mochwo.main.presenter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jnanatech.mochwo.main.model.Conference;
import com.jnanatech.mochwo.main.view.MainView;
import com.jnanatech.mochwo.notification.model.NotificationModel;
import com.jnanatech.mochwo.schedule.model.EventModel;
import com.jnanatech.mochwo.speakers.model.SpeakerModel;
import com.jnanatech.mochwo.sponsers.model.SponserModel;
import com.jnanatech.mochwo.utils.Constants;
import com.jnanatech.mochwo.utils.NoInternetDialog;
import com.jnanatech.mochwo.utils.database.RealmController;
import com.jnanatech.mochwo.utils.sharedPreference.SpeakerSizeSharedPrefHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;

public class MainImplementor implements MainPresenter {


    private Context context;
    private MainView mainView;
    private long remainingTime;

    private Conference conference = new Conference();

    private ArrayList<SpeakerModel> speakerModels = new ArrayList<>();
    private ArrayList<SponserModel> sponserModels = new ArrayList<>();

    private ArrayList<EventModel> dayOneEvents = new ArrayList<>();
    private ArrayList<EventModel> dayTwoEvents = new ArrayList<>();
    private ArrayList<EventModel> temporaryEvents = new ArrayList<>();

    private RequestQueue requestQueue;
    private RealmController realmController;
    private SpeakerSizeSharedPrefHelper speakerSizeSharedPrefHelper;


    ProgressDialog progressDialog;

    public MainImplementor(Context context, MainView mainView) {
        this.context = context;
        this.mainView = mainView;
        requestQueue = Volley.newRequestQueue(context);
        Realm.init(context);
        realmController = RealmController.with((Activity) context);
        speakerSizeSharedPrefHelper = new SpeakerSizeSharedPrefHelper(context);
        progressDialog = new ProgressDialog(context);
    }

    @Override
    public void setConference() {
        realmController.clearConference();
        conference.setImageUrl("https://www.adlibbing.org/wp-content/uploads/2018/04/confrence-room.jpg");
        conference.setTitle("MOUNTAINS IN THE CHANGING WORLD");
        conference.setStartDate("2019-10-18 02:20:00");
        conference.setEndDate("2019-10-19 10:00:00");
        conference.setLocation("KIAS Building");
        conference.setQuote("This is the quote for the mochwo 2019. conference.");

        realmController.addConference(conference);
        mainView.getEvent(conference);
    }

    @Override
    public void setSchedule1() {

        realmController.clearAllEvents();
        JsonObjectRequest
                jsonObjectRequest
                = new JsonObjectRequest(
                Request.Method.GET,
                Constants.URLSponserAPI,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONObject acfObject = response.getJSONObject("acf");
                            JSONArray scheduleD1Array = acfObject.getJSONArray("schedule_d1");


                            for (int i = 0; i < scheduleD1Array.length(); i++) {

                                JSONObject sessionObject = scheduleD1Array.getJSONObject(i);

                                JSONArray sessionArray = sessionObject.getJSONArray("other_content");
                                String sessionName = sessionObject.getString("session");

                                for (int s = 0; s < sessionArray.length(); s++) {
                                    EventModel event = new EventModel();

                                    JSONObject object = sessionArray.getJSONObject(s);

                                    event.setStartTime(object.getString("start_time"));
                                    event.setEndTime(object.getString("end_time"));
                                    event.setTopic(object.getString("topic"));

                                    event.setId("schedule_d1" + sessionName + event.getTopic());

                                    event.setDescription(object.getString("description"));
                                    event.setKeywords(object.getString("keywords"));
                                    event.setSpeakers(object.getString("speaker"));

                                    event.setSessionName(sessionName);
                                    event.setScheduleName("schedule_d1");

                                    if (getBookmarkStatus(event.getId()))
                                        event.setBookmarked(true);
                                    else
                                        event.setBookmarked(false);

                                    dayOneEvents.add(event);
                                }
                            }
                            for (int i = 0; i < dayOneEvents.size(); i++) {
                                realmController.addEvent(dayOneEvents.get(i));
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NoInternetDialog noInternetDialog = new NoInternetDialog(context);
                        noInternetDialog.showDialog();
                    }
                });
        requestQueue.add(jsonObjectRequest);

    }

    public Boolean getBookmarkStatus(String id) {
        boolean flag = false;
        for (int i = 0; i < temporaryEvents.size(); i++) {
            if (temporaryEvents.get(i).getId().equals(id)) {
                if (temporaryEvents.get(i).isBookmarked()) {
                    Log.d("bookmarkStatus", temporaryEvents.get(i).isBookmarked() + " ----");
                    flag = true;
                    break;
                } else {
                    flag = false;
                }
            }
        }
        return flag;
    }

        @Override
        public void setSchedule2 () {
            JsonObjectRequest
                    jsonObjectRequest
                    = new JsonObjectRequest(
                    Request.Method.GET,
                    Constants.URLSponserAPI,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONObject acfObject = response.getJSONObject("acf");
                                JSONArray scheduleD1Array = acfObject.getJSONArray("schedule_d2");

                                for (int i = 0; i < scheduleD1Array.length(); i++) {

                                    JSONObject sessionObject = scheduleD1Array.getJSONObject(i);

                                    JSONArray sessionArray = sessionObject.getJSONArray("other_content");
                                    String sessionName = sessionObject.getString("session");

                                    for (int s = 0; s < sessionArray.length(); s++) {
                                        EventModel event = new EventModel();

                                        JSONObject object = sessionArray.getJSONObject(s);

                                        event.setStartTime(object.getString("start_time"));
                                        event.setEndTime(object.getString("end_time"));
                                        event.setTopic(object.getString("topic"));

                                        event.setId("schedule_d2" + sessionName + event.getTopic());

                                        event.setDescription(object.getString("description"));
                                        event.setKeywords(object.getString("keywords"));
                                        event.setSpeakers(object.getString("speaker"));

                                        event.setSessionName(sessionName);
                                        event.setScheduleName("schedule_d2");

                                        if (getBookmarkStatus(event.getId()))
                                            event.setBookmarked(true);
                                        else
                                            event.setBookmarked(false);

                                        dayTwoEvents.add(event);

                                    }
                                }

                                for (int i = 0; i < dayTwoEvents.size(); i++) {
                                    realmController.addEvent(dayTwoEvents.get(i));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            NoInternetDialog noInternetDialog = new NoInternetDialog(context);
                            noInternetDialog.showDialog();
                        }
                    });
            requestQueue.add(jsonObjectRequest);
        }

        @Override
        public void setRemainingTime () {

            SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.dateFormatDefault);
            try {
                //conference date
                Date conferenceDateTime = dateFormat.parse(conference.getStartDate());

                //current date
                Calendar c = Calendar.getInstance();
                String formattedDate = dateFormat.format(c.getTime());
                Date currentDateTime = dateFormat.parse(formattedDate);

                remainingTime = conferenceDateTime.getTime() - currentDateTime.getTime();


            } catch (ParseException e) {
                e.printStackTrace();
            }

            mainView.getRemainingTime(remainingTime);
        }

        @Override
        public void getSpeakers () {
            JsonArrayRequest
                    jsonArrayRequest
                    = new JsonArrayRequest(
                    Request.Method.GET,
                    Constants.URLSpeakersAPI,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject speakerObject = response.getJSONObject(i);

                                    speakerModels.add(parseSpeakerJson(speakerObject));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            realmController.clearAllSpeakerList();
                            realmController.addAllSpeakers(speakerModels);


                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            NoInternetDialog noInternetDialog = new NoInternetDialog(context);
                            noInternetDialog.showDialog();
                        }
                    });
            requestQueue.add(jsonArrayRequest);
        }

        @Override
        public void checkSpeakerSize () {

            if (realmController.getSpeakerListSize() == speakerSizeSharedPrefHelper.getSpeakerSize()) {
                mainView.getSpeakerChangeSize(0, realmController.getSpeakerListSize());

            } else {
                for (int i = 0; i < (realmController.getSpeakerListSize() - speakerSizeSharedPrefHelper.getSpeakerSize()); i++) {
                    NotificationModel notificationModel = new NotificationModel();
                    notificationModel.setCategory("Speaker");
                    notificationModel.setDescription(realmController.getAllSpeakersList().get(i).getSpeakerName());

                    notificationModel.setDate(realmController.getAllSpeakersList().get(i).getDate());

                    realmController.addNotification(notificationModel);
                }
                mainView.getSpeakerChangeSize(realmController.getSpeakerListSize() - speakerSizeSharedPrefHelper.getSpeakerSize(), realmController.getSpeakerListSize());


            }

        }

        @Override
        public void getSponsers () {
            JsonObjectRequest
                    jsonObjectRequest
                    = new JsonObjectRequest(
                    Request.Method.GET,
                    Constants.URLSponserAPI,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONObject acfObject = response.getJSONObject("acf");
                                JSONArray sponserArray = acfObject.getJSONArray("sponsors");

                                for (int i = 0; i < sponserArray.length(); i++) {
                                    SponserModel sponserModel = new SponserModel();

                                    JSONObject object = sponserArray.getJSONObject(i);
                                    JSONObject sponserObject = object.getJSONObject("sponsor_logo");

                                    sponserModel.setId(sponserObject.getInt("id"));
                                    sponserModel.setTitle(sponserObject.getString("title"));
                                    sponserModel.setUrl(sponserObject.getString("url"));

                                   sponserModels.add(sponserModel);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            if (realmController.getAllSponsors().size() != sponserModels.size()) {
                                realmController.clearAllSponsorList();
                                for (int i = 0; i < sponserModels.size(); i++) {
                                    realmController.addSponsors(sponserModels.get(i));

                                }
                            }
                            mainView.getSponser(sponserModels);

                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            NoInternetDialog noInternetDialog = new NoInternetDialog(context);
                            noInternetDialog.showDialog();
                        }
                    });
            requestQueue.add(jsonObjectRequest);
        }

        @Override
        public void showLoadingDialog () {
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Loading...");
            progressDialog.show();
        }

        @Override
        public void dismissLoadingDialog () {
            progressDialog.dismiss();

        }


        private SpeakerModel parseSpeakerJson (JSONObject jsonObject) throws JSONException {
            SpeakerModel speakerModel = new SpeakerModel();

            speakerModel.setId(jsonObject.getInt("id"));
            speakerModel.setDate(jsonObject.getString("date"));
            speakerModel.setFeaturedMediaId(jsonObject.getInt("featured_media"));


            JSONObject acfObject = jsonObject.getJSONObject("acf");
            if (acfObject.has("facebook"))
                speakerModel.setFacebookUrl(acfObject.getString("facebook"));

            if (acfObject.has("twitter"))
                speakerModel.setTwitterUrl(acfObject.getString("twitter"));

            if (acfObject.has("linkedin"))
                speakerModel.setLinkedInUrl(acfObject.getString("linkedin"));

            if (acfObject.has("instagram"))
                speakerModel.setInstagramUrl(acfObject.getString("instagram"));

            if (acfObject.has("youtube"))
                speakerModel.setYoutubeUrl(acfObject.getString("youtube"));

            speakerModel.setSpeakerName(acfObject.getString("speaker_name"));
            speakerModel.setCurrentPosition(acfObject.getString("current_position"));
            speakerModel.setShortInfo(acfObject.getString("short_info"));

            if (acfObject.has("personal_site"))
                speakerModel.setPersonalSite(acfObject.getString("personal_site"));
            speakerModel.setDescription(acfObject.getString("description"));
            speakerModel.setQuote(acfObject.getString("quote"));

            if (acfObject.has("education1"))
                speakerModel.setEducation1(acfObject.getString("education1"));

            if (acfObject.has("education2"))
                speakerModel.setEducation2(acfObject.getString("education2"));

            if (acfObject.has("education3"))
                speakerModel.setEducation3(acfObject.getString("education3"));

            if (acfObject.has("education4"))
                speakerModel.setEducation4(acfObject.getString("education4"));

            if (acfObject.has("experience1"))
                speakerModel.setExperience1(acfObject.getString("experience1"));

            if (acfObject.has("experience2"))
                speakerModel.setExperience2(acfObject.getString("experience2"));

            if (acfObject.has("experience3"))
                speakerModel.setExperience3(acfObject.getString("experience3"));

            if (acfObject.has("experience4"))
                speakerModel.setExperience4(acfObject.getString("experience4"));

            speakerModel.setPhone(acfObject.getString("phone"));
            speakerModel.setEmail(acfObject.getString("email"));
            speakerModel.setAddress(acfObject.getString("address"));

            JSONObject linkObject = jsonObject.getJSONObject("_links");
            JSONArray attachmentLink = linkObject.getJSONArray("wp:attachment");

            speakerModel.setFeatureMediaLink(attachmentLink.getJSONObject(0).getString("href"));

            return speakerModel;

        }


    }
